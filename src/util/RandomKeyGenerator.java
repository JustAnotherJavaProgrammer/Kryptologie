package util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomKeyGenerator {

	private static SecureRandom csprng;

	private static void initCspring() {
		if (csprng != null)
			return;
		try {
			csprng = SecureRandom.getInstance("NativePRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			csprng = new SecureRandom();
		}
	}
	
	public static int generateNumKey(int maxVal) {
		initCspring();
		return csprng.nextInt(maxVal);
	}
	
	public static long generateLongKey() {
		initCspring();
		return csprng.nextLong();
	}

	public static String generateKey(int length, char[] allowedChars) {
		initCspring();
		StringBuilder keyBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			keyBuilder.append(allowedChars[csprng.nextInt(allowedChars.length)]);
		}
		return keyBuilder.toString();
	}

	public static String generateKey(int length, AlphabetMode mode) {
		return generateKey(length, AlphabetMode.getAllowedChars(mode));
	}
}
