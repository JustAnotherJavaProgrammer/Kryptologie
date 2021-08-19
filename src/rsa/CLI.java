package rsa;

import util.AlphabetMode;

public class CLI {

	public static void main(String[] args) {
		RSA rsa = new RSA(AlphabetMode.MODE_CASE_SENSITIVE_SPACE);
		System.out.println(rsa.encrypt(1001l, 125l, 2881l));
	}

}
