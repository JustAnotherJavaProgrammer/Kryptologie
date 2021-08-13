package util;

import java.util.ArrayList;
import java.util.Comparator;

public class FrequencyAnalysis {

	public static void main(String[] args) {
		String str = "Hallo Welt!";
		System.out.println("Analyzing \"" + str + "\":");
		System.out.println("code\tchar\tabs. f.\trel. freq.");
		for (CharFreqPair pair : analyze(str)) {
			System.out.println(pair.toString());
		}
	}

	public static CharFreqPair[] analyze(String str) {
		ArrayList<CharFreqPair> chars = new ArrayList<>();
		outerLoop: for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			for (CharFreqPair pair : chars) {
				if (pair.character == c) {
					pair.absoluteFrequency++;
					continue outerLoop;
				}
			}
			chars.add(new CharFreqPair(c, 1));
		}
		for (CharFreqPair pair : chars) {
			pair.recalculateRelativeFrequency(str.length());
		}
		chars.sort(new Comparator<CharFreqPair>() {

			@Override
			public int compare(CharFreqPair o1, CharFreqPair o2) {
				// Reversed order or o1 and o2 from typical comparison, so that elements with a larger absoluteFrequency appear at the top
				return o2.absoluteFrequency - o1.absoluteFrequency;
			}
		});
		CharFreqPair[] result = new CharFreqPair[chars.size()];
		return chars.toArray(result);
	}
}

class CharFreqPair {
	public char character;
	public int absoluteFrequency;
	public double relativeFrequency;

	public CharFreqPair(char character) {
		this(character, 0);
	}

	public CharFreqPair(char character, int absoluteFrequency) {
		this(character, absoluteFrequency, 0);
	}

	public CharFreqPair(char character, int absoluteFrequency, double relativeFrequency) {
		this.character = character;
		this.absoluteFrequency = absoluteFrequency;
		this.relativeFrequency = relativeFrequency;
	}

	public void recalculateRelativeFrequency(int strLen) {
		relativeFrequency = absoluteFrequency / (double)strLen;
	}

	@Override
	public String toString() {
		return ((int) character) + "\t" + character + "\t" + absoluteFrequency + "\t" + ((double)Math.round(relativeFrequency*10000)/100.0) + "%";
	}
}
