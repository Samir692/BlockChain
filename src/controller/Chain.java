package controller;

import model.Block;
import model.BlockPersisntent;
import model.Product;
import view.MainView;

import org.bouncycastle.*;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class Chain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
	

	final static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("BlockChainJava");
	final static EntityManager entityManager = emfactory.createEntityManager();
	static MainView view = new MainView();
	
	public static void main(String[] args) throws UnknownHostException {
		
		Product product = null;
		BlockPersisntent persistentBlock = null;
		
		entityManager.getTransaction().begin();
		//enter details of user
				try{
					product = new Product();
					persistentBlock = new BlockPersisntent("", "", "0");
					entityManager.persist(product);
					entityManager.persist(persistentBlock);
					
				}
				catch(Exception e){
					System.out.println("Product creation faliled");
		}
		
		
		Product genesisProduct = new Product();
		Block genesisBlock = new Block(genesisProduct, "0");
		blockchain.add(genesisBlock);
		blockchain.get(0).mineBlock(difficulty);
		System.out.println("\nBlockchain is Valid: " + isChainValid());
		
//		addProduct(entityManager, genesisProduct,  genesisBlock);
		//SocketTest.startServer();
		//SocketTest.startSender();
				
		System.out.println("Welcome to Renting Product Services platform!!" + "\n");
		

		
		boolean ext = false;
		String choice = view.getUserChoice();
		do{
			switch(choice){
			case "a":
				if(addProduct() == false || view.getBackMenu() ){
					choice = view.getUserChoice();
				}
				
				break;
			case "e":
				boolean temp = false;
				do{
					String answer = view.getSureExit();
					if("y".equals(answer)){
						temp = true;
						ext = true;
					}
					else if("n".equals(answer)){
						System.out.println("Exit process is denied!!");
						temp = true;
						choice = view.getUserChoice();
					}
					else
						System.out.println("Please enter right operation!!");
					
				}while(!temp);
				break;
		    default:
		    	System.out.println("Please enter right operation");
		    	choice = view.getUserChoice();
			}
		}while(!ext);
		System.out.println("Application terminated!!");
		
		
		entityManager.close();
		emfactory.close();
		
		
	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		return true;

	}
	
	private static boolean addProduct() throws UnknownHostException {
		
		Product product = new Product();
		String name = "computer";
		int price = 200;
		product.setName(name);
		product.setPrice(price);
		
		//check every time validity of block
		//if new block is created mine it
		
		Block block = new Block(product, blockchain.get(blockchain.size()-1).hash);
		BlockPersisntent persistBlock = new BlockPersisntent(Integer.toString(product.getId()), block.getPreviousHash(),
															block.getHash());
				
		blockchain.add(block);
		blockchain.get(blockchain.size()-1).mineBlock(difficulty);
		if(isChainValid()) {
			System.out.println("\nBlockchain is Valid");
			
			String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
			Thread t1 = new Thread(new Runnable() {
			    public void run()
			    {
			    	SocketTest.startServer();
			    }});  
			    t1.start();
			    
			    Thread t2 = new Thread(new Runnable() {
				    public void run()
				    {
				    	try {
							SocketTest.startSender(blockchainJson);
							addProductDB(entityManager, product, persistBlock);
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	
				    }});  
				    t2.start();
			
			
			
			return true;
		}
		return false;
		
	}
	
	private static void addProductDB(EntityManager em, Product product, BlockPersisntent block) {
    		em.persist(product);
    		em.persist(block);
    		em.getTransaction().commit();		
		
	}

}
