
import java.io.IOException;
import spell.ISpellCorrector;
import spell.SpellCorrector;
import spell.Trie;
import java.util.ArrayList;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {

	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {

		String dictionaryFileName = args[0];
		String inputWord = args[1];

		/**
		 * Create an instance of your corrector here
		 */
		SpellCorrector corrector = new spell.SpellCorrector();

		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		//System.out.println(corrector.trieToString());
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}

		System.out.println("Suggestion is: " + suggestion);

		ArrayList<String> words;
		words = corrector.getWordsInsertionDist(inputWord);
		for (String word: words) {
			System.out.println(word);
		}
	}

}
