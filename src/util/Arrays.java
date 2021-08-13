package util;

public class Arrays {
	public static int indexInCharArray(char[] arr, char c) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == c)
				return i;
		}
		return -1;
	}
}
