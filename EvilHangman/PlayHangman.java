
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Set;
import java.io.File;
import hangman.EvilHangmanGame;
import hangman.EvilHangmanGame.GuessAlreadyMadeException;

/**
 * A simple main class for running the Evil Hangman Game.
 */
public class PlayHangman {
    private EvilHangmanGame game = null;
    Set<Character> guesses = new TreeSet<Character>();
    Set<String> possibleWords = new TreeSet<String>();

    public void guess(int numGuesses) {
        if(numGuesses < 1) {
            System.out.println("You lose!");
            System.out.println("The word was " + game.getRandomWord());
        }
        System.out.println("\nYou have " + numGuesses + " left");
        System.out.print("Used letters:");
        for (char guess : guesses) {
            System.out.print(" " + guess);
        }
        System.out.print("\nWord: ");
        System.out.println(game.getCurrentWord());

        boolean goodGuess = false;
        while(!goodGuess) {
            System.out.print("\nEnter guess: ");
            Scanner scan = new Scanner(System.in);
            String guess = scan.next();
            if(guess.length() > 1) {
                System.out.println("Invalid input");
                continue;
            }
            scan.close();

            char[] chars = guess.toCharArray();
            boolean notAlphabetic = false;
            for (char c : chars) {
                if(!Character.isLetter(c)) {
                    notAlphabetic = true;
                }
            }
            if(notAlphabetic) {
                System.out.println("Invalid input");
                continue;
            }

            guess = guess.toLowerCase();
            try {
                possibleWords = game.makeGuess(guess.charAt(0));
                goodGuess = true;
            } catch (EvilHangmanGame.GuessAlreadyMadeException e) {
                System.out.println(e);
            }
            String currentWord = game.getCurrentWord();
            if(!currentWord.contains("_")) {
                System.out.println("You win!");
                return;
            }
        }
        if(goodGuess) {
            guess(numGuesses);
        }
        else {
            guess(numGuesses - 1);
        }

    }

    public void playGame(String dictionaryFileName, int wordLength, int numGuesses) {
        game = new EvilHangmanGame();
        File file = new File(dictionaryFileName);
		game.startGame(file, wordLength);
        guess(numGuesses);
    }

	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) {
		String dictionaryFileName = args[0];
		int wordLength = Integer.parseInt(args[1]);
        int numGuesses = Integer.parseInt(args[2]);

        PlayHangman hangman = new PlayHangman();
        hangman.playGame(dictionaryFileName, wordLength, numGuesses);
	}

}
