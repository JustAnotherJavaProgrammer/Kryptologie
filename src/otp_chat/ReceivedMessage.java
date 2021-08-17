package otp_chat;

import java.io.Serializable;

import otp.OTP.KeyCiphertextPair;
import util.AlphabetMode;

public class ReceivedMessage extends KeyCiphertextPair implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 56928229991928458L;
	public final AlphabetMode alphabet;
	
	
	public ReceivedMessage(String key, String ciphertext, AlphabetMode alphabet) {
		super(key, ciphertext);
		this.alphabet = alphabet;
	}
	
	public ReceivedMessage(KeyCiphertextPair keyCiphertextPair, AlphabetMode alphabet) {
		super(keyCiphertextPair.key, keyCiphertextPair.ciphertext);
		this.alphabet = alphabet;
	}

}
