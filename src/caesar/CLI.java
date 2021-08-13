package caesar;

public class CLI {

	public static void main(String[] args) {
		Caesar caesar = new Caesar(CaesarMode.MODE_UNIVERSAL);
		caesar.setKey(2);
		String cyphertext = caesar.encrypt("Hallo Welt!");
		System.out.println(cyphertext);
		System.out.println(caesar.decrypt(cyphertext));
	}

}
