package vigenere;

import caesar.Caesar;
import util.AlphabetMode;
import util.Arrays;
import util.Cipher;
import util.Sanitizer;
import util.Sanitizer.SanitizeResult;

public class Vigenere extends Cipher {
	private String key;
	private String keyInverted;

	public Vigenere(AlphabetMode mode) {
		super(mode);
		// Prevent future exceptions
		setKey("HELLOWORLD");
	}

	@Override
	public String encrypt(String plaintext) {
		return crypt(sanitizePlaintext(plaintext), this.key);
	}

	@Override
	public String decrypt(String ciphertext) {
		return crypt(ciphertext, this.keyInverted);
	}

	private String crypt(String text, String key) {
		StringBuilder result = new StringBuilder(text);
		for (int i = 0; i < result.length(); i++) {
			result.setCharAt(i, Caesar.shift(result.charAt(i),
					Arrays.indexInCharArray(allowedChars, key.charAt(i % key.length())), allowedChars));
		}
		return result.toString();
	}

	private String invertKey(String key) {
		StringBuilder result = new StringBuilder(key);
		for (int i = 0; i < result.length(); i++) {
			result.setCharAt(i,
					allowedChars[(allowedChars.length - Arrays.indexInCharArray(allowedChars, result.charAt(i)))%allowedChars.length]);
		}
		return result.toString();
	}

	public void setKey(String key) {
		SanitizeResult sanitized = Sanitizer.sanitize(key, mode);
		if (sanitized.changed)
			System.err.println("WARNING: The key \"" + key
					+ "\" contained characters not allowed by the selected AlphabetMode and was changed to \""
					+ sanitized.result + "\" in order to only use supported characters.");
		if (sanitized.result.length() < 1)
			throw new IllegalArgumentException("The key must not be empty!");
		this.key = sanitized.result;
		this.keyInverted = invertKey(this.key);
	}

	public String getKey() {
		return key;
	}

	public String getInvertedKey() {
		return keyInverted;
	}
}
