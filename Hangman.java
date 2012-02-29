/*
 * File: Hangman.java
 * ------------------
 * Author: Jaime Alvarez
 * Assignment 4: Implementation of Hangman game.
 */


import acm.program.*;
import acm.util.*;


public class Hangman extends ConsoleProgram {

	/** Number of turns */
	private static final int NTURNS = 8;
	public static final int APPLICATION_WIDTH = 800;
	public static final int APPLICATION_HEIGHT = 600;

	/* Remainder turns */
	private int remainderTurns = NTURNS;

	/** Random generator */
	private RandomGenerator rgen = RandomGenerator.getInstance();

	// The secret word to be guessed
	static String secretWord;

	// The typed chars plus with dashes
	static String typedChars;

	// Word Guessed indicator (true: guessed the secret word)
	private Boolean wordGuessed = false;

	// The latest character entered by the user
	private char ch;

	private HangmanCanvas canvas;
	
	// First method to be executed 
	public void init() {
		// create the canvas frame
		canvas = new HangmanCanvas();
		add(canvas);
		
	}
	
	// Setup, Play and finish the game
	public void run() {
		setup();
		playGame();
		endGame();
	}

	/** Displays the final game messages */
	private void endGame() {
		
		if (wordGuessed) {
			println("You guessed the word: " + secretWord);
			println("You win!!");
		} else {
			println("You're completely hung.");
			println("The word was: " + secretWord);
			println("You lose!.");
		}
		
	}
	
	/** Setup the random word and messages for the user. */
	private void setup() {
		
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		canvas.reset();
		
		// Get a new Word randomly
		secretWord = randomWord();
		//println("The word to be guessed (test only): " + secretWord);
		
		// Show the initial dashes with the same length of the secret word 
		typedChars = secretWord.replaceAll("[A-Z]", "-");
		canvas.displayWord(typedChars);
		
		// Display initial message and options to the player.
		println("Welcome to Hangman!");
		println("The word now looks like this: " + typedChars);
		println("You have " + remainderTurns + " guesses left.");
				
	}

	/**
	 * Plays the game, reading the user letter, checking if it exists in the
	 * secret word and showing the guessed Chars and the remainder turns.
	 */
	private void playGame() {

		// Play the game while the user have remainder turns and don't 
		// win the game.
		
		while ((remainderTurns > 0)  & (!wordGuessed)){
			// Reads the new typed character
			char ch = readChar();

			// Check if the typed letter is in the secret word.
			if (inSecretWord(ch)) {

				// Replace dashes with guessed chars.
				typedChars = replaceDashes(ch);
				canvas.displayWord(typedChars);
				
				// If the secret word is guessed the user win the game.
				if (secretWord.equals(typedChars)) {
					wordGuessed = true;
				}
			}

			// Print the messages to show the game status.
			println("The Word now looks like this: " + typedChars);
			println("You have " + remainderTurns + " guesses left.");

		} 
		
		if (remainderTurns == 0) wordGuessed = false;
	}

	/** Reads the letter typed by user
	 * @return The typed letter	 */
	
	private char readChar() {
		String input = readLine("Your guess: ");
		do {
			if (input.length() > 1) {
				input = readLine("You can only type one character. Try again: ");
			}
		} while (input.length() > 1);

		ch = input.charAt(0);
		return Character.toUpperCase(ch);
	}

	/** Checks if the typed letter is in the secret word
	 * @return True if is in the word, else false
	 */
	private boolean inSecretWord(char ch) {

		if (secretWord.indexOf(ch) < 0) {
			remainderTurns--;
			canvas.noteIncorrectGuess(ch);
			println("There are no " + ch + "'s in the word");
			return false;
		} else {
			println("The guess is correct.");
			return true;
		}
	}

	/** Replace dashes with the guessed chars.
	 * @param ch The typed character
	 * @return The new String with the replaced chars
	 */
	private String replaceDashes(char ch) {

		int index = 0;
		do {

			if (secretWord.charAt(index) == ch) {
				if (index > 0) {
					typedChars = typedChars.substring(0, index) + ch
							+ typedChars.substring(index + 1);
				} else {
					typedChars = ch + typedChars.substring(1);
				}
			}

			index++;
		} while (index < secretWord.length());

		return typedChars;
	}

	/** Selects a word randomly.
	 * @return A Random Word  
	 */
	private String randomWord() {
		
		// Read the file that contains the secret words
		HangmanLexicon secretWordsList = new HangmanLexicon();
		
		// Get the word count from the ListArray
		int wordCount = secretWordsList.getWordCount();
		
		// Obtains a random index between 0 and wordCount from file.
		int randomWord = rgen.nextInt(0, wordCount);
		
		// Return the random selected word 
		return secretWordsList.getWord(randomWord);

	}

}
