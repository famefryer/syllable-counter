package syllablecounter;

public class OOSyllableCounter {

	public int countSyllables(String word) {
		WordCounter counter = new WordCounter();
		Character c = ' ';
		word = word.toLowerCase();
		for (int k = 0; k < word.length(); k++) {
			c = word.charAt(k);
			if (c == '\'' || c == ' ') {
				continue;
			}
			counter.getState().handleChar(c);
		}
		if (word.length() > 1) {
			char beforeEnd = word.charAt(word.length() - 2);
			if (word.charAt(word.length() - 1) == 'e' && (beforeEnd != 'a' && beforeEnd != 'e' && beforeEnd != 'i'
					&& beforeEnd != 'o' && beforeEnd != 'u')) {
				if (counter.getSyllable() == 1) {
					return 1;
				} else {
					return counter.getSyllable() - 1;
				}
			}
		}

		return counter.getSyllable();
	}

}

class WordCounter {
	private final StateOO START = new StartState();
	private final StateOO SINGLEVOWEL = new SingleVowelState();
	private final StateOO MULTIVOWEL = new MultiVowelState();
	private final StateOO HYPHEN = new HyphenState();
	private final StateOO NONWORD = new NonwordState();
	private final StateOO CONSONANT = new ConsonantState();
	private StateOO state;
	private int syllableCount = 0;

	public WordCounter() {
		state = START;
		syllableCount = 0;
	}

	public void setState(StateOO newState) {
		if (newState != state)
			newState.enterState();
		state = newState;
	}

	public StateOO getState() {
		return this.state;
	}

	public int getSyllable() {
		return this.syllableCount;
	}

	abstract class StateOO {
		public abstract void handleChar(char c);

		private Character[] vowelList = { 'a', 'e', 'i', 'o', 'u' };

		public void enterState() {

		}

		protected boolean isVowelOrY(Character c) {
			if (c == vowelList[0] || c == vowelList[1] || c == vowelList[2] || c == vowelList[3] || c == vowelList[4]
					|| c == 'y') {
				return true;
			} else {
				return false;
			}
		}

		protected boolean isVowel(Character c) {
			if (c == vowelList[0] || c == vowelList[1] || c == vowelList[2] || c == vowelList[3] || c == vowelList[4]) {
				return true;
			} else {
				return false;
			}
		}

	}

	class SingleVowelState extends StateOO {

		public SingleVowelState() {
		}

		@Override
		public void handleChar(char c) {
			if (isVowel(c)) {
				setState(MULTIVOWEL);
			} else if (Character.isLetter(c)) {
				setState(CONSONANT);
			} else if (c == '-') {
				setState(HYPHEN);
			} else {
				setState(NONWORD);
			}
		}

		@Override
		public void enterState() {
			syllableCount++;
		}
	}

	class StartState extends StateOO {
		public StartState() {
		}

		@Override
		public void handleChar(char c) {
			if (isVowelOrY(c)) {
				setState(SINGLEVOWEL);
			} else if (Character.isLetter(c)) {
				setState(CONSONANT);
			} else if (c == '-') {
				setState(NONWORD);
			} else {
				setState(NONWORD);
			}
		}

	}

	class MultiVowelState extends StateOO {

		public MultiVowelState() {
		}

		@Override
		public void handleChar(char c) {
			if (isVowel(c)) {
			} else if (Character.isLetter(c)) {
				setState(CONSONANT);
			} else if (c == '-') {
				setState(HYPHEN);
			} else {
				setState(NONWORD);
			}

		}

	}

	class HyphenState extends StateOO {
		public HyphenState() {
		}

		@Override
		public void handleChar(char c) {
			if (isVowelOrY(c)) {
				setState(SINGLEVOWEL);
			} else if (Character.isLetter(c)) {
				setState(CONSONANT);
			} else {
				setState(NONWORD);
			}
		}

	}

	class NonwordState extends StateOO {
		public NonwordState() {
		}

		@Override
		public void handleChar(char c) {
		}

		public void enterState() {
			syllableCount = 0;
		}

	}

	class ConsonantState extends StateOO {
		public ConsonantState() {

		}

		@Override
		public void handleChar(char c) {
			if (isVowelOrY(c)) {
				setState(SINGLEVOWEL);
			} else if (Character.isLetter(c)) {
			} else if (c == '-') {
				setState(HYPHEN);
			} else {
				setState(NONWORD);
			}
		}

	}

	class LastState extends StateOO {

		@Override
		public void handleChar(char c) {

		}

	}

}
