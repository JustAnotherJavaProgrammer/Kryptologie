package caesar;

import util.Arrays;

public class Caesar {
	public final CaesarMode mode;
	private char[] allowedChars = {};
	private int key = 0;

	public Caesar(CaesarMode mode) {
		this.mode = mode;
		this.allowedChars = CaesarMode.getAllowedChars(mode);
	}

	public String encrypt(String plaintext) {
		return crypt(plaintext, key);
	}

	public String decrypt(String cyphertext) {
		return crypt(cyphertext, allowedChars.length - (key % allowedChars.length));
	}

	private String crypt(String text, int key) {
		StringBuilder result = new StringBuilder(text);
		for (int i = 0; i < result.length(); i++) {
			result.setCharAt(i, allowedChars[(Arrays.indexInCharArray(allowedChars, result.charAt(i)) + key)
					% allowedChars.length]);
		}
		return result.toString();
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	protected SanitizeResult sanitize(String text) {
		if (mode == CaesarMode.MODE_UNIVERSAL)
			return new SanitizeResult(false, text);
		String result = text;
		if (mode == CaesarMode.MODE_UPPER_SPACE || mode == CaesarMode.MODE_CASE_SENSITIVE_SPACE)
			result = result.replaceAll("[^a-zA-Z ]", "");
		if (mode == CaesarMode.MODE_CASE_SENSITIVE || mode == CaesarMode.MODE_UPPER_ONLY)
			result = result.replaceAll("[^a-zA-Z]", "");
		if (mode == CaesarMode.MODE_UPPER_ONLY || mode == CaesarMode.MODE_UPPER_SPACE)
			result = result.toUpperCase();
		return new SanitizeResult(result.equals(text), result);
	}
}
