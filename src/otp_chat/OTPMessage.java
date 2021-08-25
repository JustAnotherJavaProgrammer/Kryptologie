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

	public OTPMessage(AlphabetMode alphabet, KeyCiphertextPair msg, ConcurrentHashMap<String, String> personalizedKeys,
			String nickname) {
		super(msg, alphabet, nickname);
		this.personalizedKeys = personalizedKeys;
	}

	public OTPMessage(AlphabetMode alphabet, KeyCiphertextPair msg, String nickname) {
		this(alphabet, msg, new ConcurrentHashMap<>(), nickname);
	}

	public OTPMessage(AlphabetMode alphabet, String key, String ciphertext,
			ConcurrentHashMap<String, String> personalizedKeys, String nickname) {
		this(alphabet, new KeyCiphertextPair(key, ciphertext), personalizedKeys, nickname);
	}

	public OTPMessage(AlphabetMode alphabet, String key, String ciphertext, String nickname) {
		this(alphabet, new KeyCiphertextPair(key, ciphertext), nickname);
	}

}
