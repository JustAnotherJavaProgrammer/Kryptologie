package caesar;

import util.AlphabetMode;
import util.Arrays;
import util.Cipher;

public class Caesar extends Cipher {
	public Caesar(AlphabetMode mode) {
		super(mode);
	}

	private int key = 0;

	public String encrypt(String plaintext) {
		return crypt(sanitizePlaintext(plaintext), key);
	}

	public String decrypt(String ciphertext) {
		return crypt(ciphertext, allowedChars.length - (key % allowedChars.length));
	}

	private String crypt(String text, int key) {
		StringBuilder result = new StringBuilder(text);
		for (int i = 0; i < result.length(); i++) {
//			result.setCharAt(i, allowedChars[(Arrays.indexInCharArray(allowedChars, result.charAt(i)) + key)
//					% allowedChars.length]);
			result.setCharAt(i, Caesar.shift(result.charAt(i), key, allowedChars));
		}
		return result.toString();
	}

	public static char shift(char c, int key, char[] allowedChars) {
		return allowedChars[(Arrays.indexInCharArray(allowedChars, c) + key) % allowedChars.length];
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

}
