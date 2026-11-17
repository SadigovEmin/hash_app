package hash_app;

import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;

public class MurmurHash {
	public static int getBucketIndex(String text, int numberOfBuckets) {
		int hashValue = Hashing.murmur3_32_fixed().hashString(text, StandardCharsets.UTF_8).asInt();
		return Math.floorMod(hashValue , numberOfBuckets);
	}
}
