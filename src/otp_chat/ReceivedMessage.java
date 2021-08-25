package otp_chat;

import java.io.Serializable;

import otp.OTP.KeyCiphertextPair;
import util.AlphabetMode;

public class ReceivedMessage extends KeyCiphertextPair implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 56928229991928458L;
	public final AlphabetMode alphabet;
	public final String nickname;

	public ReceivedMessage(String key, String ciphertext, AlphabetMode alphabet, String nickname) {
		super(key, ciphertext);
		this.alphabet = alphabet;
		this.nickname = nickname;
	}

	public ReceivedMessage(KeyCiphertextPair keyCiphertextPair, AlphabetMode alphabet, String nickname) {
		this(keyCiphertextPair.key, keyCiphertextPair.ciphertext, alphabet, nickname);
	}

}
