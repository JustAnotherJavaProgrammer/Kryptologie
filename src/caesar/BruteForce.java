package caesar;

import java.util.ArrayList;

public class BruteForce {
	public static void main(String[] args) {
		String[] options = generateOptions("Jcnnq\"Ygnv#", CaesarMode.MODE_UNIVERSAL);
		for (int i = 0; i < options.length; i++) {
			System.out.print("Key: ");
			System.out.print(i);
			System.out.println(" | Possible decryption:");
			System.out.println(options[i]);
		}
	}

	public static String[] generateOptions(String cyphertext, CaesarMode mode) {
		String[] options = new String[CaesarMode.getAllowedChars(mode).length];
		Caesar caesar = new Caesar(mode);
		for (int i = 0; i < options.length; i++) {
			caesar.setKey(i);
			options[i] = caesar.decrypt(cyphertext);
		}
		return options;
	}
}
