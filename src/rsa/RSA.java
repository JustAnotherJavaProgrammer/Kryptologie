package rsa;

import java.math.BigInteger;

import util.AlphabetMode;
import util.Cipher;
import util.Euler;
import util.RandomKeyGenerator;

public class RSA extends Cipher {
	private long key = 0;

	public RSA(AlphabetMode mode) {
		super(mode);
	}

	@Override
	public String encrypt(String plaintext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String decrypt(String ciphertext) {
		// TODO Auto-generated method stub
		return null;
	}

	private BigInteger crypt(BigInteger val, BigInteger ed, BigInteger n) {
		return val.modPow(ed, n);
	}

	private BigInteger crypt(long val, long ed, long n) {
		return crypt(new BigInteger(Long.toString(val)), new BigInteger(Long.toString(ed)),
				new BigInteger(Long.toString(n)));
	}

	private long cryptLong(long val, long ed, long n) {
		return crypt(val, ed, n).longValue();
	}

	public BigInteger decrypt(BigInteger c, BigInteger d, BigInteger n) {
		return crypt(c, d, n);
	}

	public BigInteger decrypt(long c, long d, long n) {
		return crypt(c, d, n);
	}

	public long decryptLong(long c, long d, long n) {
		return cryptLong(c, d, n);
	}

	public BigInteger encrypt(BigInteger w, BigInteger e, BigInteger n) {
		return crypt(w, e, n);
	}

	public BigInteger encrypt(long w, long e, long n) {
		return crypt(w, e, n);
	}

	public long encryptLong(long w, long e, long n) {
		return cryptLong(w, e, n);
	}

//	public long encrypt(long w, long e, long n) {
//		return longPow(w, e) % n;
//	}
//	
//	private static long longPow(long a, long b) {
//		long res = 1;
//		for(long i = 0; i < b; i++) {
//			res *= a;
//		}
//		return res;
//	}

	public void setKey(long key) {
		this.key = key;
	}
	
	public static long generatePrime(long min, long max) {
		long val;
		do {
			val = RandomKeyGenerator.generateLongKey();
		} while(val < min || val >= max || !Euler.isPrime(val));
		return val;
	}

}
