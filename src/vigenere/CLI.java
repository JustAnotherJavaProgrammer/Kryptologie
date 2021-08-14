package vigenere;

import util.AlphabetMode;

public class CLI {
	
	public static void main(String[] args) {
		Vigenere vigenere = new Vigenere(AlphabetMode.MODE_CASE_SENSITIVE_SPACE);
		vigenere.setKey("Secret");
		String cyphertext = vigenere.encrypt("This is some secret text");
		System.out.println(cyphertext);
		System.out.println(vigenere.decrypt(cyphertext));
	}

}
