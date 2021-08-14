package caesar;

import util.AlphabetMode;

public class CLI {

	public static void main(String[] args) {
		Caesar caesar = new Caesar(AlphabetMode.MODE_ONE_WIDTH_CHARS_ASCII);
		caesar.setKey(2);
		String cyphertext = caesar.encrypt("Hallo Welt!");
		System.out.println(cyphertext);
		System.out.println(caesar.decrypt(cyphertext));
	}

}
