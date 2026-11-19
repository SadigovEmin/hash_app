#Hash Bucket Storage Application

## Overview

This project implements a file-based hash bucket storage application using Java. It uses MurmurHash3 to determine in which bucket a string should be stored. MurmurHash3 is not implemented from scratch, the jar file used for that from guava google which provides hash implementations that has already been built. When a bucket reaches its maximum capacity, the application creates overflow files to store additional data.

The program accepts the number of buckets and the bucket size as command-line arguments or interactively from CLI interface if the arguments are not provided.

## How It Works

### 1. Bucket Creation

The application creates `n` primary bucket files named:

- `1.txt`
- `2.txt`
- `3.txt`
- ...
- `n.txt`

Each bucket has a maximum capacity of `s` characters.

This process is done by the function (createBucketFiles) in the HashBucketManager class.
The function loop through from 1 to the numberOfBuckets to create files. It uses the index number to name files by creating object from the File class.

### 2. Hashing

The application uses MurmurHash3 (32-bit) from the Guava library to calculate a hash value for each input string.

The hash value is converted into a valid bucket index using `Math.floorMod()`. The selected index determines the primary bucket where the string should be stored.

### 3. String Storage

Strings are appended directly to the bucket file without separators. If multiple strings are assigned to the same bucket, they are concatenated.

The application checks the current number of characters stored in a file before inserting a new string to make sure the bucket capacity is not exceeded.

Strings longer than the bucket capacity are rejected.

### 4. Overflow Handling and References

When a primary bucket becomes full, the application creates overflow files.

For example:

- `1.txt`
- `1_reference.txt`
- `1_overflow_1.txt`
- `1_overflow_2.txt`

The reference file (`1_reference.txt`) contains the names of the overflow files associated with the original bucket, with each filename stored on a separate line.

Example contents of `1_reference.txt`:

    1_overflow_1.txt
    1_overflow_2.txt

The application reads this reference file to identify existing overflow files. If the last overflow file has enough remaining capacity, the new string is written there. Otherwise, a new overflow file is created and its name is appended to the reference file.

This linking mechanism allows the application to identify overflow files belonging to a particular primary bucket and continue storing data in the correct file.

### 5. Data Persistence

Bucket data and reference information are stored in files on disk. Existing files are not overwritten when the application is restarted. New strings are appended to existing files, and previously created overflow files and references can be reused.

## Running the Application

### Using Command-Line Arguments

The program accepts two arguments:

- `n` – number of hash buckets
- `s` – maximum capacity of each bucket in characters

Example:

	In order to compile java classes run:

	javac -cp "lib/guava-33.4.8-jre.jar" -d bin src\hash_app\*.java
	
	In order to run compiled java classes run:

    java -cp "bin;lib/guava-33.4.8-jre.jar" hash_app.Main 3 10

### Interactive Configuration (Run code with run button (easy way))

If the program is started without arguments, it asks the user to enter the number of buckets and the bucket size.

Example:

    Please enter the number of hash buckets and bucket size (e.g. 5 20): 3 10

After initialization, the program continuously accepts strings until the user terminates it with Ctrl+C or stop the program manually with button in the terminal.

## Overflow File Naming

Overflow files follow this naming convention:

    bucketNumber_overflow_overflowNumber.txt

For example:

    2_overflow_1.txt
    2_overflow_2.txt

The corresponding reference file is:

    2_reference.txt

## Dependencies

The project uses the Guava library for the MurmurHash3 implementation.

The Guava JAR is located in the `lib` directory.

## Author

Emin Sadigov
	