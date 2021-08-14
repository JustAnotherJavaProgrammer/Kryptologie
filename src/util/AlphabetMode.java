package util;

import java.util.ArrayList;

public enum AlphabetMode {
	MODE_UPPER_ONLY, MODE_UPPER_SPACE, MODE_CASE_SENSITIVE, MODE_CASE_SENSITIVE_SPACE, MODE_ONE_WIDTH_CHARS_ASCII,
	MODE_UNIVERSAL;

	public static char[] getAllowedChars(AlphabetMode mode) {
		ArrayList<Character> vals = new ArrayList<>();
		switch (mode) {
		case MODE_UNIVERSAL:
			return charsFromTo(0, 256);
//			break;
		case MODE_ONE_WIDTH_CHARS_ASCII:
			return charsFromTo(32, 127);
//			break;
		case MODE_UPPER_ONLY:
		case MODE_UPPER_SPACE:
		case MODE_CASE_SENSITIVE_SPACE:
		case MODE_CASE_SENSITIVE:
//			for (int i = 65; i < 91; i++) {
			for (char c : charsFromTo(65, 91)) {
				vals.add(c);
			}
			if (mode == AlphabetMode.MODE_UPPER_ONLY || mode == AlphabetMode.MODE_UPPER_SPACE)
				break;
//			for (int i = 97; i < 123; i++) {
			for (char c : charsFromTo(97, 123)) {
				vals.add(c);
			}
			break;
		default:
			// Should not happen!
		}
		if (mode == AlphabetMode.MODE_UPPER_SPACE || mode == AlphabetMode.MODE_CASE_SENSITIVE_SPACE) {
			vals.add((char) 32); // SPACE character
		}
		char[] allowedChars = new char[vals.size()];
		for (int i = 0; i < allowedChars.length; i++) {
			allowedChars[i] = vals.get(i);
		}
		return allowedChars;
	}

	private static char[] charsFromTo(int from, int to) {
		char[] result = new char[to - from];
		for (int i = 0; i < result.length; i++) {
			result[i] = (char) (i + from);
		}
		return result;
	}
}
