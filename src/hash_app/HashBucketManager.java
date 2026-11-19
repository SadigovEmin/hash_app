package hash_app;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
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
		
	    if (getFileSize(file) + text.length() > bucketSize) {

	        File referenceFile = new File(fileNumber + "_reference.txt");

	        try {
	            List<String> overflowNames;

	            if (referenceFile.exists()) {
	                overflowNames = Files.readAllLines(referenceFile.toPath());
	            } else {
	                overflowNames = List.of();
	            }

	            boolean spaceFound = false;

	            if(!overflowNames.isEmpty()) {
	            	
	            	String lastOverflowName = overflowNames.get(overflowNames.size() - 1);
	            	File lastOverflowFile = new File(lastOverflowName);
	            	
	            	if(lastOverflowFile.exists() && getFileSize(lastOverflowFile) + text.length() <= bucketSize ){
	            		file = lastOverflowFile;
	            		spaceFound = true;
	            	}
	            }

	            if (!spaceFound) {

	                int overflowNum = 1;

	                String overflowName = fileNumber
	                        + "_overflow_" + overflowNum + ".txt";

	                File overflowFile = new File(overflowName);

	                while (overflowFile.exists()
	                        || overflowNames.contains(overflowName)) {

	                    overflowNum++;

	                    overflowName = fileNumber
	                            + "_overflow_" + overflowNum + ".txt";

	                    overflowFile = new File(overflowName);
	                }

	                Files.createFile(overflowFile.toPath());

	                Files.writeString(
	                        referenceFile.toPath(),
	                        overflowName + System.lineSeparator(),
	                        StandardOpenOption.CREATE,
	                        StandardOpenOption.APPEND
	                );

	                file = overflowFile;
	            }

	        } catch (IOException e) {
	            System.out.println("Error handling overflow: " + e.getMessage());
	            return;
	        }
	    }
		
		try {
			java.nio.file.Files.writeString(file.toPath(), text, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
			System.out.println(text + " is successfully added to " + file.getName());
		}catch(IOException e) {
			System.out.println("Error while writing to " + fileNumber + ".txt " + e.getMessage());
			
		}
	}
	
	private int getFileSize(File file) {
		try {
			String content = Files.readString(file.toPath());
			return content.length();
			
		}catch(IOException e) {
			System.out.println("Error while reading file: " + e.getMessage());
			return 0;
		}
	}
}
