package otp;

import otp.OTP.KeyCiphertextPair;
import util.AlphabetMode;

public class CLI {

	public static void main(String[] args) {
		OTP otp = new OTP(AlphabetMode.MODE_CASE_SENSITIVE_SPACE);
		String origPlaintext = "This is some secret text";
		KeyCiphertextPair encrypted = otp.encryptWithRandomKey(origPlaintext);
		System.out.println("Ciphertext: " + encrypted.ciphertext);
		System.out.println("Key:\t\t" + encrypted.key);
		System.out.println("Inferred key:\t" + otp.inferKey(origPlaintext, encrypted.ciphertext));
		System.out.println(otp.decrypt(encrypted));
	}

}
