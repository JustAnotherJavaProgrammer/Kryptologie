package caesar;

public class Caesar {
	public final CaesarMode mode;
	private int rangeSize = 0;
	private int key = 0;
	
	public Caesar(CaesarMode mode) {
		this.mode = mode;
		switch(mode) {
		case MODE_UNIVERSAL:
			this.rangeSize = 255;
			break;
		case MODE_CASE_SENSITIVE_SPACE:
			this.rangeSize = 53;
			break;
		case MODE_CASE_SENSITIVE:
			this.rangeSize = 52;
			break;
		case MODE_UPPER_SPACE:
			this.rangeSize = 27;
			break;
		case MODE_UPPER_ONLY:
			this.rangeSize = 26;
			break;
			default:
				this.rangeSize = 0;
				// Should not happen!
		}
	}
	
	public String encrypt(String plaintext) {
		for(int i = 0; i < plaintext.length(); i++) {
			plaintext[i] = plaintext[i] + key;
		}
		return null;
	}
	
	public String decrypt(String cyphertext) {
		return null;
	}
	
	public byte getKey() {
		return key;
	}
	
	public void setKey(byte key) {
		this.key = key;
	}
	
	protected SanitizeResult sanitize(String text) {
		if(mode == CaesarMode.MODE_UNIVERSAL)
			return new SanitizeResult(false, text);
		String result = text;
		if(mode == CaesarMode.MODE_CASE_SENSITIVE || mode == CaesarMode.MODE_UPPER_ONLY)
			result = result.replaceAll("[^a-zA-Z]", "");
		if(mode == CaesarMode.MODE_UPPER_ONLY || mode == CaesarMode.MODE_UPPER_SPACE)
			result = result.toUpperCase();
		return new SanitizeResult(result.equals(text), result);
	}
}
