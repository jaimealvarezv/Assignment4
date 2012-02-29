/*
 * File: HangmanCanvas.java
 * ------------------------
 * Displays a Hangman using Java Graphic Objects
 * Author: Jaime Alvarez
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;

	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int DASHES_Y = 440;
	private static final int BAD_LETTERS_Y = 480;

	private int x;
	private int y;
	private int x1;
	private int bodyY;
	
	private String badLetters = "";

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		
		drawScaffold(); 
		drawBeam();
		drawRope();
	}

	/** Draws the scaffold */
	private void drawScaffold() {
		x = (getWidth() / 2) - BEAM_LENGTH;
		y = (getHeight() / 2) - BODY_LENGTH - (ROPE_LENGTH + (HEAD_RADIUS * 2));

		GLine scaffold = new GLine(x, y, x, y + SCAFFOLD_HEIGHT);
		add(scaffold);
	}

	/** Draws the scaffold Beam  */
	private void drawBeam() {
		GLine beam = new GLine(x, y, x + BEAM_LENGTH, y);
		add(beam);
	}

	/** Draws the scaffold Rope */
	private void drawRope() {

		GLine rope = new GLine(x + BEAM_LENGTH, y, x + BEAM_LENGTH, y
				+ ROPE_LENGTH);
		add(rope);
	}

	/**
	 * Updates the word on the screen to correspond to the current
	 * state of the game.  The argument string shows what letters have
	 * been guessed so far; unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String guessedChars) {

		x = (getWidth() / 2) - BEAM_LENGTH;
		y = DASHES_Y;
		
		// y = (getHeight() / 2) - BODY_LENGTH + ROPE_LENGTH + (HEAD_RADIUS * 2)+ (LEG_LENGTH * 2);

		// Removes the label to put a new one where the last was
		GObject label = getElementAt(x, y);
		if (label != null) remove(label);

		// Puts the new label with the guessed chars
		GLabel typedCharsLabel = new GLabel(guessedChars, x, y);
		typedCharsLabel.setFont("SansSerif-30");
		add(typedCharsLabel);

	}
	
	
	/**
	 * Updates the display to correspond to an incorrect guess by the
	 * user.  Calling this method causes the next body part to appear
	 * on the scaffold and adds the letter to the list of incorrect
	 * guesses that appears at the bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {
				
		badLetters = badLetters + letter;
		displayBadLetters(badLetters);
		int n = badLetters.length();
		
		switch (n) {
			
			case 1:
				drawHead();
				break;
				
			case 2:
				drawBody();
				break;
				
			case 3:
				drawLeftArm();
				break;

			case 4:
				drawRightArm();
				break;

			case 5:
				drawLeftLeg();
				break;
				
			case 6:
				drawRightLeg();
				break;
				
			case 7:
				drawLeftFoot();
				break;
				
			case 8:
				drawRightFoot();
				break;			
		}
	}

	/** Draws The head */
	private void drawHead() {

		x = (getWidth() / 2) - HEAD_RADIUS;
		y = (getHeight() / 2) - BODY_LENGTH
				- (ROPE_LENGTH + (HEAD_RADIUS * 2) + ROPE_LENGTH) + HEAD_RADIUS;
		bodyY = y; // Used Later to draw other body parts

		GOval head = new GOval(x, y, HEAD_RADIUS * 2, HEAD_RADIUS * 2);
		add(head);

	}

	/** Draws the Body */
	private void drawBody() {

		x = (getWidth() / 2);
		y = getHeight() / 2 - BODY_LENGTH;

		GLine body = new GLine(x, y, x, y + BODY_LENGTH);
		add(body);
	}

	/** Draws a Left Arm */
	private void drawLeftArm() {
		x = getWidth() / 2;
		y = bodyY + (2 * HEAD_RADIUS) + ARM_OFFSET_FROM_HEAD;
		x1 = x - UPPER_ARM_LENGTH;

		GLine leftForearm = new GLine(x, y, x1, y);
		add(leftForearm);

		x = getWidth() / 2;
		y = bodyY + (2 * HEAD_RADIUS) + ARM_OFFSET_FROM_HEAD;
		x1 = x - UPPER_ARM_LENGTH;

		GLine leftArm = new GLine(x1, y, x1, y + LOWER_ARM_LENGTH);
		add(leftArm);
	}

	/** Draws a Right Arm */
	private void drawRightArm() {
		x = getWidth() / 2;
		y = bodyY + (2 * HEAD_RADIUS) + ARM_OFFSET_FROM_HEAD;
		x1 = x + UPPER_ARM_LENGTH;

		GLine rightForearm = new GLine(x, y, x1, y);
		add(rightForearm);

		x = getWidth() / 2;
		y = bodyY + (2 * HEAD_RADIUS) + ARM_OFFSET_FROM_HEAD;
		x1 = x + UPPER_ARM_LENGTH;

		GLine rigthArm = new GLine(x1, y, x1, y + LOWER_ARM_LENGTH);
		add(rigthArm);
	}

	/**  Draws a Left Leg */
	private void drawLeftLeg() {

		x = getWidth() / 2;
		y = bodyY + (2 * HEAD_RADIUS) + BODY_LENGTH;
		x1 = x - HIP_WIDTH;

		GLine leftLegHip = new GLine(x, y, x1, y);
		add(leftLegHip);

		GLine leftLeg = new GLine(x1, y, x1, y + LEG_LENGTH);
		add(leftLeg);

	}

	/** Draws a Left Foot */
	private void drawLeftFoot() {
		x = (getWidth() / 2)  - HIP_WIDTH; 
		y = bodyY + (2 * HEAD_RADIUS) + BODY_LENGTH;

		GLine leftFoot = new GLine(x, y + LEG_LENGTH, x - FOOT_LENGTH, y
				+ LEG_LENGTH);
		add(leftFoot);
		
	}

	/** Draws a Right leg */
	private void drawRightLeg() {

		x = getWidth() / 2;
		y = bodyY + (2 * HEAD_RADIUS) + BODY_LENGTH;
		x1 = x + HIP_WIDTH;

		GLine leftLegHip = new GLine(x, y, x1, y);
		add(leftLegHip);

		GLine leftLeg = new GLine(x1, y, x1, y + LEG_LENGTH);
		add(leftLeg);

	}

	/** Draws a Right Foot */
	private void drawRightFoot() {

		x = (getWidth() / 2) + HIP_WIDTH;
		y = bodyY + (2 * HEAD_RADIUS) + BODY_LENGTH;
		GLine leftFoot = new GLine(x, y + LEG_LENGTH, x + FOOT_LENGTH, y + LEG_LENGTH);
		add(leftFoot);

	}
		
	/** Draws a label that contains the bad letters */
	private void displayBadLetters(String badLetters) {
		x = (getWidth() / 2) - BEAM_LENGTH ;
		y = BAD_LETTERS_Y;
		
		// Removes the label to put a new one where the last was
		GObject label = getElementAt(x, y);
		if (label != null) remove(label);

		// Puts the new label with the guessed chars
		GLabel badTypedLetters = new GLabel(badLetters, x, y);
		badTypedLetters.setFont("SansSerif-30");
		add(badTypedLetters);
	}


}
