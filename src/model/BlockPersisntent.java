package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * Entity implementation class for Entity: BlockPersisntent
 *
 */

@Entity
public class BlockPersisntent implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	
		@Id
	    @GeneratedValue
	    private int id;
	
		private String hash;
		private String previousHash;
		private String data;
		private long timeStamp;
		private int nonce; //for mining

		
		public BlockPersisntent() {
			super();
		}

		//Block Constructor.
		public BlockPersisntent(String data,String previousHash, String hash ) {
			this.data = data;
			this.previousHash = previousHash;
			this.timeStamp = new Date().getTime();;
			this.hash = hash;
		}


   
}
