package util;

public class GGT {
	public static long ggt(long a, long b) throws Exception {
		{
			final long tmp = Math.min(a, b);
			b = Math.max(a, b);
			a = tmp;
		}
		for (long i = a; i > 0; i--) {
			if (a % i == 0 && b % i == 0) {
				System.out.println(a-i+1 + " iterations (bad)");
				return i;
			}
		}
		throw new Exception("No common divisors! Big disgrace!");
	}

	public static long euclid(long a, long b) {
		long its = 0;
		while (b != 0) {
			its++;
			long tmp = b;
			b = a % b;
			a = tmp;
		}
		System.out.println(its + " iterations (good)");
		return a;
	}
}
