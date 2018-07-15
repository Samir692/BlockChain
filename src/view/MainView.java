package view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import javax.persistence.EntityManager;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;

public class MainView {

	
	//show menu and get user choice
	public String getUserChoice() {
		final String EnteredChoice;
		Scanner console = new Scanner(System.in);
		
		System.out.println("Please choose one of the options from followings");
		System.out.println(
						   "Write 'a'      : To buy product"         								+   "\n"  +  
						   "Write 'e'      : To exit from application"										+   "\n"  +
						   "Enter:"
							);
		EnteredChoice = console.nextLine();
		System.out.println("Chosen opearion is " + EnteredChoice + "\n");
		return EnteredChoice;
	}

	//back operation to back to menu
	public boolean getBackMenu(){
		boolean ext = false;
		String MenuChoice;
		
		System.out.println("Write 'b'    : To go back to MENU");	
		Scanner console = new Scanner(System.in);

		do{
			MenuChoice = console.nextLine();
			System.out.println("Chosen opearion is " + MenuChoice);
			if(MenuChoice.compareTo("b") == 0){
				ext = true;
				return ext;
			}
			else{
				System.out.println("Please write operation correctly!!");
			}
		}while(!ext);
		
		return ext;
	}
	
	public String getBackMenuALLForString(String input){
		int  num;
		try{
	       num = Integer.parseInt(input);
	    } catch (NumberFormatException e)
	    {
	    	if("b".equals(input)){
				return "";
			}
	    	else{
	    		if(input.isEmpty()) System.out.println("You pressed the Enter Key, Welcome to the Main Menu");
				return input;
	    	}
	     }
		return Integer.toString(num);
		
		
	}

public int getBackMenuALLForInteger(String input) throws Exception{
		
		int num = -1;
		try{
	        num = Integer.parseInt(input);
	        if(num == -1)
	        	throw new Exception();
	    } catch (NumberFormatException e) 
	    {
	    	if("b".equals(input)){
				return -1;
			}
	    	else{
	    		if(input.isEmpty()){
	    			System.out.println("You pressed the Enter Key, Welcome to the Main Menu");
	    			return -1;
	    		}
	    		throw new Exception();
	    	}
	     }
		return num;
		
		
	}

	public double getBackMenuALLForDouble(String input) throws Exception{
		
		double num = -1;
		try{
	        num = Double.parseDouble(input);
	        if(num == -1)
	        	throw new Exception();
	    } catch (NumberFormatException e) 
	    {
	    	if("b".equals(input)){
				return -1;
			}
	    	else{
	    		if(input.isEmpty()){
	    			System.out.println("You pressed the Enter Key, Welcome to the Main Menu");
	    			return -1;
	    		}
	    		throw new Exception();
	    	}
	     }
		return num;
		
		
}

	public String getSureExit() {
		System.out.println("Are you sure to exit application???");
		System.out.println("Choose from followings:" + "\n" + "y" + "\n" + "n" + "\n" + "Enter:");
		final String answer;
		Scanner console = new Scanner(System.in);
		answer = console.nextLine();

		return answer;
		
}

}
