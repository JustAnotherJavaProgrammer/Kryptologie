package util;

public class Arrays {
	public static int indexInCharArray(char[] arr, char c) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == c)
				return i;
		}
		return -1;
	}

	public static boolean contains(Object[] arr, Object obj) {
		for (Object element : arr) {
			if (element.equals(obj)) {
				return true;
			}
		}
		return false;
	}
}
