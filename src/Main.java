import util.Euler;
import util.GCD;

public class Main {

	public static void main(String[] args) throws Exception {
		for(int i = 1; i <= 100; i++) {
			if(Euler.isPrime(i))
				System.out.println(i);
		}
	}

}
