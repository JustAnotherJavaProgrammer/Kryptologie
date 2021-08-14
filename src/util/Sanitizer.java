package util;

public class Sanitizer {

	public static SanitizeResult sanitize(String text, AlphabetMode mode) {
		char[] allowedChars = AlphabetMode.getAllowedChars(mode);
		StringBuilder result = new StringBuilder(
				(mode == AlphabetMode.MODE_UPPER_ONLY || mode == AlphabetMode.MODE_UPPER_SPACE) ? text.toUpperCase()
						: text);
		outerLoop: for (int i = 0; i < result.length(); i++) {
			char c = result.charAt(i);
			for (char allowedChar : allowedChars) {
				if (c == allowedChar)
					continue outerLoop;
			}
			result.deleteCharAt(i);
			i--;
		}
		String builtResult = result.toString();
		return new SanitizeResult(builtResult.equals(text), builtResult);
	}

	public static class SanitizeResult {
		public final boolean changed;
		public final String result;

		public SanitizeResult(boolean changed, String result) {
			this.changed = changed;
			this.result = result;
		}
	}
}