package hash_app;

import java.io.File;
import java.io.IOException;

public class HashBucketManager {
	private int numberOfBuckets;
	private int bucketSize;
	
	public HashBucketManager(int numberOfBuckets, int bucketSize) {
		this.numberOfBuckets = numberOfBuckets;
		this.bucketSize = bucketSize;
	}
	
	public void createBucketFiles() {
		
		for (int i = 1; i <= numberOfBuckets; i++) {
			File file = new File("bucket" + i + ".txt"); //java object. 
			
			try {
				if(file.createNewFile()) {
					System.out.println(file.getName() + " created.");
				}else {
					System.out.println(file.getName() + " already exists");
				}
			}catch(IOException e) {
				System.out.println(e.getMessage() + " File name: " + file.getName());
			}
			
		}
	}
}
