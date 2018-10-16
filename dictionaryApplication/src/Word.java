/** +---------------------------------------+
 *  | Project: Dictionary - Assignment 1	|
 *  | Class: Word							|
 *  | Group: ULIS with love					|
 *  +---------------------------------------+
 */

package com.io;

// Save word and its meaning

public class Word {

	/*
		Attribute:
			- word_target: new word
			- word_explain: word's meanings
		Constructor:
			- Default
			- Parameterized
		Method:
			- setTarget(t)
			- setExplain(e)
			- getTarget()
			- getExplain()
			- compareTo(w)
	*/

	private String word_target, word_explain;

	// Default constructor: empty word

	public Word(){}

	// Parameterized constructor: new word & its meaning

	public Word (String wt, String we) {
		this.word_target = wt;
		this.word_explain = we;
	}

	// Method: setTarget(t): set word.target = t

	public void setTarget (String t) {
		try {
			this.word_target = t;
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
		}
	}

	// Method: setExplain(e): set word.explain = e

	public void setExplain (String ex) {
		try {
			this.word_explain = ex;
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
		}
	}

	// Method: getTarget(): get the word_target

	public String getTarget() {
		return (this == null ? null : this.word_target);
	}

	// Method: getExplain(): get the word_explain

	public String getExplain() {
		return (this == null ? null : this.word_explain);
	}

}