/*
 * File: HangmanLexicon.java
 * -------------------------
 * Author: Jaime Alvarez
 * Creates an array list of words read from a file.
 * 
 */

import java.io.*;
import acm.util.*;
import java.util.*;

public class HangmanLexicon {

	
	private ArrayList<String> secretWordList = new ArrayList<String>();

	/** Reads the secret words from a file and put them into the array list */
	public HangmanLexicon() {
		
		try {
			FileReader fr = new FileReader("HangmanLexicon.txt");
			BufferedReader secretWords = new BufferedReader(fr);
			
			String line = secretWords.readLine();
					
			while (line != null) { 
				secretWordList.add(line);
				line = secretWords.readLine();
			}
			secretWords.close();
			
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return secretWordList.get(index);

	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return secretWordList.size();
	}
}

