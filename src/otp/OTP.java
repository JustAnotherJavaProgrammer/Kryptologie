package otp;

import util.AlphabetMode;
import util.Arrays;
import util.Cipher;
import util.RandomKeyGenerator;
import util.Sanitizer;
import util.Sanitizer.SanitizeResult;
import vigenere.Vigenere;

public class OTP extends Cipher {
	private final Vigenere vigenereInstance;

	public OTP(AlphabetMode mode) {
		super(mode);
		vigenereInstance = new Vigenere(mode);
	}

	public KeyCiphertextPair encryptWithRandomKey(String plaintext) {
		plaintext = sanitizePlaintext(plaintext);
		return encrypt(plaintext, RandomKeyGenerator.generateKey(plaintext.length(), allowedChars));
	}

	public KeyCiphertextPair encrypt(String plaintext, String key) {
		plaintext = sanitizePlaintext(plaintext);
		SanitizeResult sanitizedKey = Sanitizer.sanitize(key, mode);
		if (sanitizedKey.changed)
			System.err.println("WARNING: The key contained letters that are unsupported by your chosen AlphabetMode "
					+ "and was therefore changed to only use supported characters.");
		key = sanitizedKey.result;
		if (plaintext.length() != key.length())
			throw new IllegalArgumentException("Key and plaintext must be the same length!");
		vigenereInstance.setKey(key);
		return new KeyCiphertextPair(key, vigenereInstance.encrypt(plaintext));
	}

	/**
	 * Encrypts a string with a random key<br>
	 * To get the key, use {@link OTP#inferKey(String, String) inferKey}
	 * 
	 * @deprecated use {@link OTP#encryptWithRandomKey(String) encryptWithRandomKey}
	 *             instead and get a return value that includes the key
	 * @see OTP#encryptWithRandomKey(String)
	 */
	@Override
	@Deprecated
	public String encrypt(String plaintext) {
		return encryptWithRandomKey(plaintext).ciphertext;
	}

	public String decrypt(KeyCiphertextPair encrypted) {
		if (encrypted.key.length() != encrypted.ciphertext.length())
			throw new IllegalArgumentException("Key and ciphertext must be the same length!");
		vigenereInstance.setKey(encrypted.key);
		return vigenereInstance.decrypt(encrypted.ciphertext);
	}

	public String decrypt(String ciphertext, String key) {
		return decrypt(new KeyCiphertextPair(key, ciphertext));
	}

	@Override
	@Deprecated
	public String decrypt(String cyphertext) {
		return vigenereInstance.decrypt(cyphertext);
	}

	public String inferKey(String plaintext, String ciphertext) {
		if (plaintext.length() != ciphertext.length())
			throw new IllegalArgumentException("Ciphertext and plaintext must be the same length!");
		StringBuilder keyBuilder = new StringBuilder(ciphertext.length());
		for (int i = 0; i < ciphertext.length(); i++) {
			keyBuilder.append(
					allowedChars[(allowedChars.length + (Arrays.indexInCharArray(allowedChars, ciphertext.charAt(i))
							- Arrays.indexInCharArray(allowedChars, plaintext.charAt(i)))) % allowedChars.length]);
		}
		return keyBuilder.toString();
	}

	public static class KeyCiphertextPair {
		public final String key;
		public final String ciphertext;

		public KeyCiphertextPair(String key, String ciphertext) {
			this.key = key;
			this.ciphertext = ciphertext;
		}
	}

}
