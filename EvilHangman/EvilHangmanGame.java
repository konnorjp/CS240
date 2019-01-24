package hangman;

import java.io.File;
import java.util.Set;

public class EvilHangmanGame implements IEvilHangmanGame {
    Set<String> dictionary = new Set<String>();

    public EvilHangmanGame() {}

	@SuppressWarnings("serial")
	public static class GuessAlreadyMadeException extends Exception {

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
        if(guess is in guesses made) throw GuessAlreadyMadeException;


    }

}
