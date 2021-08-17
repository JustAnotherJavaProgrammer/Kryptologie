package otp_chat;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import otp.OTP.KeyCiphertextPair;
import util.AlphabetMode;

public class OTPMessage extends ReceivedMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7468286953036574702L;
	public final ConcurrentHashMap<String, String> personalizedKeys;

	public OTPMessage(AlphabetMode alphabet, KeyCiphertextPair msg,
			ConcurrentHashMap<String, String> personalizedKeys) {
		super(msg, alphabet);
		this.personalizedKeys = personalizedKeys;
	}

	public OTPMessage(AlphabetMode alphabet, KeyCiphertextPair msg) {
		this(alphabet, msg, new ConcurrentHashMap<>());
	}

	public OTPMessage(AlphabetMode alphabet, String key, String ciphertext,
			ConcurrentHashMap<String, String> personalizedKeys) {
		this(alphabet, new KeyCiphertextPair(key, ciphertext), personalizedKeys);
	}

	public OTPMessage(AlphabetMode alphabet, String key, String ciphertext) {
		this(alphabet, new KeyCiphertextPair(key, ciphertext));
	}

}
