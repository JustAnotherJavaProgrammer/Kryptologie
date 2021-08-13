package caesar;

import java.util.ArrayList;

public enum CaesarMode {
	MODE_UPPER_ONLY,
	MODE_UPPER_SPACE,
	MODE_CASE_SENSITIVE,
	MODE_CASE_SENSITIVE_SPACE,
	MODE_UNIVERSAL;
	
	public static char[] getAllowedChars(CaesarMode mode) {
		ArrayList<Character> vals = new ArrayList<>();
		switch (mode) {
		case MODE_UNIVERSAL:
			for (int i = 0; i < 256; i++) {
				vals.add((char) i);
			}
			break;
		case MODE_UPPER_ONLY:
		case MODE_CASE_SENSITIVE_SPACE:
		case MODE_CASE_SENSITIVE:
		case MODE_UPPER_SPACE:
			for (int i = 65; i < 91; i++) {
				vals.add((char) i);
			}
			if (mode == CaesarMode.MODE_UPPER_ONLY || mode == CaesarMode.MODE_UPPER_SPACE)
				break;
			for (int i = 97; i < 123; i++) {
				vals.add((char) i);
			}
			break;
		default:
			// Should not happen!
		}
		if (mode == CaesarMode.MODE_UPPER_SPACE || mode == CaesarMode.MODE_CASE_SENSITIVE_SPACE) {
			vals.add((char) 32); // SPACE character
		}
		char[] allowedChars = new char[vals.size()];
		for (int i = 0; i < allowedChars.length; i++) {
			allowedChars[i] = vals.get(i);
		}
		return allowedChars;
	}
}
