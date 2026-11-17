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
		if (text.length() > bucketSize) {
		    System.out.println("String exceeds bucket capacity.");
		    return;
		}
		int bucketIndex = MurmurHash.getBucketIndex(text, numberOfBuckets);
		int fileNumber  = bucketIndex + 1;
		File file = new File(fileNumber + ".txt");
		while (getFileSize(file) + text.length() > bucketSize) {
			int overflowNum = 1;
			
			File overflowFile = new File(fileNumber + "_overflow_" + overflowNum + ".txt");
			while(overflowFile.exists() && getFileSize(overflowFile) + text.length() > bucketSize) {
				overflowNum++;
				overflowFile = new File(fileNumber + "_overflow_" + overflowNum + ".txt");
			}
			file = overflowFile;
		}
		try {
			java.nio.file.Files.writeString(file.toPath(), text, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
			System.out.println(text + " is successfully added to " + fileNumber + ".txt");
		}catch(IOException e) {
			System.out.println("Error while writing to " + fileNumber + ".txt " + e.getMessage());
			
		}
	}
	
	private int getFileSize(File file) {
		try {
			String content = java.nio.file.Files.readString(file.toPath());
			return content.length();
			
		}catch(IOException e) {
			System.out.println("Error while reading file: " + e.getMessage());
			return 0;
		}
	}
}
