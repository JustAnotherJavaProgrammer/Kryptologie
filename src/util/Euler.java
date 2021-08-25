package util;

public class Euler {
	public static long euler(long n) {
		long e = 0;
		for (long i = 1; i <= n; i++) {
			if (GCD.euclid(n, i) == 1)
				e++;
		}
		return e;
	}

	public static boolean isPrime(long n) {
		if (n <= 1l)
			return false;
		for (long i = 1; i < n; i++) {
			if (GCD.euclid(n, i) != 1)
				return false;
		}
		return true;
	}
}
