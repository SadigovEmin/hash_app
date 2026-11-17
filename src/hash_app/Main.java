
package hash_app;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int n; // number of hash buckets
        int s; // hash size
        
        if (args.length == 2) {

            try {
                n = Integer.parseInt(args[0]);
                s = Integer.parseInt(args[1]);

            } catch (NumberFormatException e) {
                System.out.println("Please provide valid integer arguments.");
                in.close();
                return;
            }

        } else{
            System.out.print("Please enter the number of hash buckets and bucket size (e.g. 5 20): ");

            String hashInfo = in.nextLine();

            String[] nums = hashInfo.trim().split("\\s+");

            if (nums.length != 2) {
                System.out.println("Please enter exactly two numbers.");
                in.close();
                return;
            }

            try {
                n = Integer.parseInt(nums[0]);
                s = Integer.parseInt(nums[1]);

            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers.");
                in.close();
                return;
            }

        }

        if (n <= 0 || s <= 0) {
            System.out.println("Both values must be positive numbers.");
            in.close();
            return;
        }

        System.out.println("The number of hash buckets: " + n + ", Bucket size: " + s);
        
        HashBucketManager hashBucketManager = new HashBucketManager(n,s);
        hashBucketManager.createBucketFiles();
        
        while (true) {
        	System.out.println("Please enter the string: ");
        	
        	String text = in.nextLine();
        	
        	if(text.isEmpty()) {
        		continue;
        	}
        	
        	hashBucketManager.addString(text);
        	System.out.println();
        }
        
        
    }
}
