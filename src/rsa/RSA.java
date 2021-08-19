package rsa;

import java.math.BigInteger;

import util.AlphabetMode;
import util.Cipher;

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
	
	public BigInteger encrypt(BigInteger w, BigInteger e, BigInteger n) {
		return w.modPow(e, n);
	}
	public BigInteger encrypt(long w, long e, long n) {
		return encrypt(new BigInteger(Long.toString(w)), new BigInteger(Long.toString(e)), new BigInteger(Long.toString(n)));
	}
	
	public long encryptLong(long w, long e, long n) {
		return encrypt(w,e,n).longValue();
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

}
