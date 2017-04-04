package syllablecounter;

public class SimpleSyllableCounter {
	private Character[] vowelList = { 'a', 'e', 'i', 'o', 'u' };

	private boolean isVowelOrY(Character c) {
		if (c == vowelList[0] || c == vowelList[1] || c == vowelList[2] || c == vowelList[3] || c == vowelList[4]
				|| c == 'y') {
			return true;
		} else {
			return false;
		}
	}

	private boolean isVowel(Character c) {
		if (c == vowelList[0] || c == vowelList[1] || c == vowelList[2] || c == vowelList[3] || c == vowelList[4]) {
			return true;
		} else {
			return false;
		}
	}

	public int countSyllables(String word) {
		int syllables = 0;
		Character c = ' ';
		State state = State.START;
		word = word.toLowerCase();
		for (int k = 0; k < word.length(); k++) {
			c = word.charAt(k);
			if (c == '\'' || c == ' ') {
				continue;
			}
			if (state != State.MULTI_VOWEL && state != State.SINGLE_VOWEL && k == word.length() - 1) {
				state = State.LAST;
			}
			switch (state) {
			case CONSONANT:
				if (isVowelOrY(c)) {
					state = State.SINGLE_VOWEL;
					syllables++;
				} else if (Character.isLetter(c)) {

				} else if (c == '-') {
					state = State.HYPHEN;
				} else {
					state = State.NONWORD;
				}
				break;
			case START:
				if (isVowel(c)) {
					state = State.SINGLE_VOWEL;
					syllables++;
				} else if (Character.isLetter(c)) {
					state = State.CONSONANT;
				} else if (c == '-') {
					state = State.NONWORD;
				} else {
					state = State.NONWORD;
				}
				break;
			case SINGLE_VOWEL:
				if (isVowel(c)) {
					state = State.MULTI_VOWEL;
				} else if (Character.isLetter(c)) {
					state = State.CONSONANT;
				} else if (c == '-') {
					state = State.HYPHEN;
				} else {
					state = State.NONWORD;
				}
				break;
			case MULTI_VOWEL:
				if (isVowel(c)) {
					continue;
				} else if (Character.isLetter(c)) {
					state = State.CONSONANT;
				} else if (c == '-') {
					state = State.HYPHEN;
				} else {
					state = State.NONWORD;
				}
				break;
			case NONWORD:
				syllables = 0;
				break;
			case HYPHEN:
				if (c == '-') {
					state = State.NONWORD;
				} else if (isVowel(c)) {
					state = State.SINGLE_VOWEL;
					syllables++;
				} else if (Character.isLetter(c)) {
					state = State.CONSONANT;
				} else {
					state = State.NONWORD;
				}
				break;
			case LAST:
				if (isVowelOrY(c)) {
					syllables++;
				}
				if (c == 'e' && word.length() != 1 && syllables > 1) {
					syllables--;
				}
			}

		}

		return syllables;
	}

	public static void main(String[] args) {
		SimpleSyllableCounter c = new SimpleSyllableCounter();
		System.out.println(c.countSyllables("moohanz"));
	}

}
