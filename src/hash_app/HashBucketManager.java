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
			File file = new File(i + ".txt"); //java object. 
			
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
	
	public void addString(String text) {
		int bucketIndex = MurmurHash.getBucketIndex(text, numberOfBuckets);
		int fileNumber  = bucketIndex + 1;
		File file = new File(fileNumber + ".txt");
		try {
			java.nio.file.Files.writeString(file.toPath(), text, java.nio.file.StandardOpenOption.APPEND);
			System.out.println(text + " is successfully added to " + fileNumber + ".txt");
		}catch(IOException e) {
			System.out.println("Error while writing to " + fileNumber + ".txt " + e.getMessage());
			
		}
	}
}
