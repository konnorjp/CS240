package hangman;

/**
 * A simple main class for running the Evil Hangman Game.
 */
public class PlayHangman {
    private EvilHangmanGame game = null;
    Set<char> guesses = new Set<char>();
    Set<String> possibleWords = new Set<String>();

    public void guess(int numGuesses) {
        if(numGuesses < 1) {
            System.out.println("You lose!")
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
            import java.util.Scanner;
            Scanner scan = new Scanner(System.in);
            String guess = scan.next();
            if(guess.size() > 1) {
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
            }
            catch (GuessAlreadyMadeException e) {
                System.out.println(e)
            }
            String currentWord = game.getCurrentWord();
            if(!currentWord.contains('_')) {
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
		game.startGame(dictionaryFileName, wordLength);
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
