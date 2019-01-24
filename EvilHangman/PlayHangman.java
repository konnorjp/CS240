package hangman;

/**
 * A simple main class for running the Evil Hangman Game.
 */
public class PlayHangman {
    private EvilHangmanGame game = null;
    Set<char> guesses = new Set<char>();

    public void guess(int numGuesses) {
        System.out.println("You have " + numGuesses + " left");
        System.out.print("Used letters:");
        for (char guess : guesses) {
            System.out.print(" " + guess);
        }
        System.out.print("\nWord: ");

        //Print word

        bool goodGuess = false;
        while(!goodGuess) {
            System.out.print("\nEnter guess: ");
            //Read in input
            guess = guess.toLowerCase();
            if(set.add(guess)) {
                goodGuess = true;
            }
            else {
                System.out.println("You already used that letter");
            }
        }
        guess(numGuesses - 1);
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
