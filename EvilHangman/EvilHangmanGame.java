package hangman;

import java.io.File;
import java.util.Set;

public class EvilHangmanGame implements IEvilHangmanGame {
    Set<String> dictionary = new Set<String>();
    Set<char> guessesMade = new Set<char>();
    String currentWord = null;
    int lengthOfWord = 0;

    public EvilHangmanGame() {}

    public String getCurrentWord() {
        return currentWord;
    }

    public String getRandomWord() {
        return dictionary.toArray()[0];
    }

	@SuppressWarnings("serial")
	public static class GuessAlreadyMadeException extends Exception {
        public GuessAlreadyMadeException(String errorMessage) {
            super(errorMessage);
        }
	}

	/**
	 * Starts a new game of evil hangman using words from <code>dictionary</code>
	 * with length <code>wordLength</code>.
	 *	<p>
	 *	This method should set up everything required to play the game,
	 *	but should not actually play the game. (ie. There should not be
	 *	a loop to prompt for input from the user.)
	 *
	 * @param dictionary Dictionary of words to use for the game
	 * @param wordLength Number of characters in the word to guess
	 */
	public void startGame(File dictionary, int wordLength) {
        // Reset entire game, reinitialize everything
        dictionary = new Set<String>();
        lengthOfWord = wordLength;
        char[] curWord = new char[];
        for(int i = 0; i < lengthOfWord; i++) {
            curWord.append('_');
        }
        currentWord = String.valueOf(curWord);
        Scanner scanner = null;

		try {
			File file = new File(dictionaryFileName);
			scanner = new Scanner(file);

			while(scanner.hasNext()) {
				String word = scanner.next();
                if(wordLength == word.length()) {
                    dictionary.add(word);
                }
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

    }

    private String findRightmostGuessedLetter(char guess, Set<String> fewLettersKeys, int index) {
        Set<String> rightmostKeys = new Set<String>();
        for(String key: fewLettersKeys) {
            char[] word = key.toCharArray();
            int rightIndexWord = 0;
            if(word[index] == guess) {
                rightmostKeys.add(key);
            }
        }
        if(rightmostKeys.size() == 1) {
            return rightmostKeys.toArray()[0];
        }
        else if(rightmostKeys.size() > 1 {
            return findRightmostGuessedLetter(guess, rightmostKeys, index - 1);
        }
        else {
            return findRightmostGuessedLetter(guess, fewLettersKeys, index - 1);
        }
    }

    private Set<String> findFewestGuessedLetter(char guess, Map<String,Set<String>> maxSets) {
        char[] noGuess = new char[];
        for(int i = 0; i < lengthOfWord; i++) {
            noGuess.append('_');
        }

        if(maxSets.containsKey(String.valueOf(noGuess)) {
            return maxSets.get(noGuess));
        }

        Set<String> keysMaxSets = maxSets.keySet();
        int maxNumChar = 0;
        Set<String> fewLettersKeys = new Set<String>();
        for(String oneKey : keysMaxSets) {
            int numChar = 0;
            char[] wordChars = oneKey.toCharArray();
            for (int i = 0; i < wordChars.length(); i++) {
                if(wordChars[i] == guess) numChar++;
            }
            if(numChar > maxNumChar) {
                maxNumChar = numChar;
                fewLettersKeys.clear();
                fewLettersKeys.add(oneKey);
            }
            else if(numChar == maxNumChar) {
                fewLettersKeys.add(oneKey);
            }
        }

        if(fewLettersKeys.size() == 1) {
            return maxSets.get(oneKey);
        }
        else if(fewLettersKeys.size() > 1) {
            String rightmostKey = findRightmostGuessedLetter(guess, fewLettersKeys, lengthOfWord - 1);
            return maxSets.get(rightmostKey);
        }
        else {
            System.out.println('Failure! Fewest guesses in word!');
            return null;
        }
    }

	/**
	 * Make a guess in the current game.
	 *
	 * @param guess The character being guessed
	 *
	 * @return The set of strings that satisfy all the guesses made so far
	 * in the game, including the guess made in this call. The game could claim
	 * that any of these words had been the secret word for the whole game.
	 *
	 * @throws GuessAlreadyMadeException If the character <code>guess</code>
	 * has already been guessed in this game.
	 */
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        if(!guessesMade.add(guess)) {
            throw new GuessAlreadyMadeException("Guess has already been made: " + String.valueOf(guess));
        }

        Map<String,Set<String>> partitions = new Map<String,Set<String>>;

        for (String word: dictionary) {
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if(chars[i] == guess) {
                    chars[i] = guess;
                }
                else {
                    chars[i] = '_';
                }
            }
            String key = String.valueOf(chars);
            if(partitions.containsKey(key)) {
                Set<String> partition = partitions.get(key);
                partition.add(word);
                // Do I need to re-put the partition into the map?
                // I'm assuming not
            }
            else {
                Set<String> newPartition = new Set<String>();
                newPartition.add(word);
                partitions.put(key, newPartition);
            }
        }

        int maxNumWords = 0;
        String curWord = null;
        Map<String,Set<String>> maxSets = new Map<String,Set<String>>();
        for(Map.Entry<String,Set<String>> entry : partitions.entrySet()) {
            int setSize = entry.getValue().size();
            if(setSize > maxNumWords) {
                maxNumWords = setSize;
                curWord = entry.getKey();
                maxSets.clear();
                maxSets.put(entry);
            }
            else if(setSize == maxNumWords) {
                maxSets.add(entry)
            }
        }

        Set<String> newDictionary = new Set<String>();
        if(maxSets.size() == 1) {
            newDictionary.addAll(maxSets.entrySet().toArray()[0].getValue());
        }
        else if(maxSets.size() > 1) {
            newDictionary.addAll(findFewestGuessedLetter(char guess, Map<String,Set<String>> maxSets));
        }
        else {
            System.out.println('Failure! No Partitions!');
        }
        dictionary.clear();
        dictionary.addAll(newDictionary);
        String oneWord = dictionary.toArray()[0];
        char[] newWordChars = oneWord.toCharArray();
        int numOfCharGuess = 0;
        for(int i = 0; i < lengthOfWord; i++) {
            if(newWordChars[i] == guess) {
                numOfCharGuess++;
            }
            if(!guessesMade.contains(newWordChars[i])) {
                newWordChars[i] = '_';
            }
        }
        String newWord = String.valueOf(newWordChars);
        if(currentWord.equals(newWord)) {
            System.out.printf("Sorry, there are no %c's%n", guess);
        }
        else {
            System.out.printf("There is %d %c",numOfCharGuess, guess);
        }
        return newDictionary;
    }

}
