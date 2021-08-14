package util;

public abstract class Cipher {
	public final AlphabetMode mode;
	protected final char[] allowedChars;
	
	public Cipher(AlphabetMode mode) {
		this.mode = mode;
		this.allowedChars = AlphabetMode.getAllowedChars(mode);
	}
	
	public abstract String encrypt(String plaintext);
	public abstract String decrypt(String cyphertext);
}
