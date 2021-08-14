package util;

import util.Sanitizer.SanitizeResult;

public abstract class Cipher {
	public final AlphabetMode mode;
	protected final char[] allowedChars;

	public Cipher(AlphabetMode mode) {
		this.mode = mode;
		this.allowedChars = AlphabetMode.getAllowedChars(mode);
	}

	/**
	 * Encrypts a string
	 * @param plaintext The plaintext to be encrypted
	 * @return The encrypted ciphertext
	 */
	public abstract String encrypt(String plaintext);

	/**
	 * Decrypts a string
	 * @param ciphertext The ciphertext to be decrypted
	 * @return The decrypted ciphertext
	 */
	public abstract String decrypt(String ciphertext);

	/**
	 * Sanitizes plaintext to only use characters supported by the current
	 * {@link AlphabetMode}.<br>
	 * Prints a warning to {@code System.error}, if the plaintext was changed to
	 * remove unsupported characters
	 * 
	 * @param plaintext Some plaintext
	 * @return The sanitized plaintext
	 */
	protected String sanitizePlaintext(String plaintext) {
		SanitizeResult sanitized = Sanitizer.sanitize(plaintext, mode);
		if (sanitized.changed)
			System.err.println(
					"WARNING: The plaintext contained letters that are unsupported by your chosen AlphabetMode "
							+ "and was therefore changed to only use supported characters.");
		return sanitized.result;
	}
}
