package syllablecounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {
	static final String DICT_URL = "http://se.cpe.ku.ac.th/dictionary.txt";
	private OOSyllableCounter counter = new OOSyllableCounter();

	private void DictCount() {
		BufferedReader reader;
		int sum = 0;
		int words = 0;
		try {
			URL url = new URL(DICT_URL);
			InputStream in = url.openStream();
			reader = new BufferedReader(new InputStreamReader(in));
			while (true) {
				String word = reader.readLine();
				if (word == null)
					break;
				if(counter.countSyllables(word)!=0){
					words++;
					sum += counter.countSyllables(word);

				}
			}
		} catch (IOException e) {
			System.err.println("Exception in getDictionary: " + e.getMessage());
		}
		System.out.println("Reading words from http://se.cpe.ku.ac.th/dictionary.txt");
		System.out.println("Counted "+sum+" syllables in "+words+" words");

	}

	public static void main(String[] args) {
		Main m = new Main();
		m.DictCount();
	}
		

}
