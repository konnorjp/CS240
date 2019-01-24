package spell;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.lang.StringBuilder;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;

public class SpellCorrector implements ISpellCorrector {
	public ITrie trie = new Trie();

	public SpellCorrector () {}

	/**
	 * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
	 * for generating suggestions.
	 * @param dictionaryFileName File containing the words to be used
	 * @throws IOException If the file cannot be read
	 */
	public void useDictionary(String dictionaryFileName) {
		Scanner scanner = null;
		try {
			File file = new File(dictionaryFileName);
			scanner = new Scanner(file);

			while(scanner.hasNext()) {
				String word = scanner.next();
				trie.add(word);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	public ArrayList<String> getWordsDeletionDist(String word) {
		char[] chars = word.toCharArray();
		ArrayList<String> words = new ArrayList<String>(0);

		for(int i = 0; i < chars.length; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(Arrays.copyOfRange(chars, 0, i));
			sb.append(Arrays.copyOfRange(chars, i+1, chars.length));
			String newWord = sb.toString();
			words.add(newWord);
		}
		return words;
	}

	public ArrayList<String> getWordsTranspositionDist(String word) {
		char[] chars = word.toCharArray();
		ArrayList<String> words = new ArrayList<String>(0);

		for(int i = 0; i < chars.length; i++) {
			for(int j = 0; j < chars.length; j++) {
				char[] newChars = Arrays.copyOf(chars, chars.length);
				char temp = newChars[i];
				newChars[i] = newChars[j];
				newChars[j] = temp;
				String newWord = new String(newChars);
				words.add(newWord);
			}
		}
		return words;
	}

	public ArrayList<String> getWordsAlterationDist(String word) {
		char[] chars = word.toCharArray();
		ArrayList<String> words = new ArrayList<String>(0);
		char[] alphaChars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

		for(int i = 0; i < chars.length; i++) {
			for(int j = 0; j < alphaChars.length; j++) {
				char [] newChars = Arrays.copyOf(chars, chars.length);
				newChars[i] = alphaChars[j];
				String newWord = new String(newChars);
				words.add(newWord);
			}
		}
		return words;
	}

	public ArrayList<String> getWordsInsertionDist(String word) {
		char[] chars = word.toCharArray();
		ArrayList<String> words = new ArrayList<String>(0);
		char[] alphaChars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

		for(int i = 0; i <= chars.length; i++) {
			for(int j = 0; j < alphaChars.length; j++) {
				StringBuilder sb = new StringBuilder();
				sb.append(Arrays.copyOfRange(chars, 0, i));
				sb.append(alphaChars[j]);
				sb.append(Arrays.copyOfRange(chars, i, chars.length));
				String newWord = sb.toString();
				words.add(newWord);
			}
		}
		return words;
	}

	private String suggestSimilarWord2Dist(ArrayList<String> words) {
		ArrayList<String> allCombine = new ArrayList<String>(0);
		for(String word : words) {
			allCombine.addAll(getWordsDeletionDist(word));
			allCombine.addAll(getWordsTranspositionDist(word));
			allCombine.addAll(getWordsAlterationDist(word));
			allCombine.addAll(getWordsInsertionDist(word));
		}

		ArrayList<String> allWords = new ArrayList<String>(0);
		for(String combine : allCombine) {
			if(trie.find(combine) != null) allWords.add(combine);
		}

		if(allWords.size() > 0) {
			if(allWords.size() == 1) return allWords.get(0);

			ArrayList<String> freqWords = new ArrayList<String>(0);
			int greatFreq = 0;
			for(String oneWord : allWords) {
				int newFreq = trie.find(oneWord).getValue();
				if(newFreq > greatFreq) {
					greatFreq = newFreq;
					freqWords.clear();
					freqWords.add(oneWord);
				}
				else if(newFreq == greatFreq) {
					freqWords.add(oneWord);
				}
			}

			if(freqWords.size() == 1) return freqWords.get(0);
			return freqWords.stream().sorted().findFirst().orElse(null);
		}
		return null;
	}

	/**
	 * Suggest a word from the dictionary that most closely matches
	 * <code>inputWord</code>
	 * @param inputWord
	 * @return The suggestion or null if there is no similar word in the dictionary
	 */
	public String suggestSimilarWord(String inputWord) {
		String word = inputWord.toLowerCase();
		Trie.INode node = trie.find(word);
		if (node != null) return word;

		ArrayList<String> allCombine = getWordsDeletionDist(word);
		allCombine.addAll(getWordsTranspositionDist(word));
		allCombine.addAll(getWordsAlterationDist(word));
		allCombine.addAll(getWordsInsertionDist(word));

		ArrayList<String> allWords = new ArrayList<String>(0);
		for(String combine : allCombine) {
			if(trie.find(combine) != null) allWords.add(combine);
		}

		if(allWords.size() > 0) {
			if(allWords.size() == 1) return allWords.get(0);

			ArrayList<String> freqWords = new ArrayList<String>(0);
			int greatFreq = 0;
			for(String oneWord : allWords) {
				int newFreq = trie.find(oneWord).getValue();
				if(newFreq > greatFreq) {
					greatFreq = newFreq;
					freqWords.clear();
					freqWords.add(oneWord);
				}
				else if(newFreq == greatFreq) {
					freqWords.add(oneWord);
				}
			}

			if(freqWords.size() == 1) return freqWords.get(0);
			return freqWords.stream().sorted().findFirst().orElse(null);
		}
		return suggestSimilarWord2Dist(allCombine);
	}

	public String trieToString() {
		return trie.toString();
	}
}
