package caesar;

import util.AlphabetMode;
import util.Arrays;
import util.Sanitizer;
import util.Sanitizer.SanitizeResult;

public class Caesar {
	public final AlphabetMode mode;
	private char[] allowedChars = {};
	private int key = 0;

	public Caesar(AlphabetMode mode) {
		this.mode = mode;
		this.allowedChars = AlphabetMode.getAllowedChars(mode);
	}

	public String encrypt(String plaintext) {
		SanitizeResult sanitized = Sanitizer.sanitize(plaintext, mode);
		if (sanitized.changed)
			System.err.println(
					"WARNING: The plaintext contained letters that are unsupported by your chosen AlphabetMode "
							+ "and was therefore changed to only use supported characters.");
		return crypt(sanitized.result, key);
	}

	public String decrypt(String cyphertext) {
		return crypt(cyphertext, allowedChars.length - (key % allowedChars.length));
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
