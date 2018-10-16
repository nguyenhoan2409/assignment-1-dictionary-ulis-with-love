package sample;
/**
	Project: Dictionary (assignment 1)
	Class: Word
	Group: ULIS with love
*/
// Save the list of word and their meanings

class Word {

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
		this.word_target = t;
	}

	// Method: setExplain(e): set word.explain = e

	public void setExplain (String e) {
		this.word_explain = e;
	}

	// Method: getTarget(): get the word_target

	public String getTarget() {
		return this.word_target;
	}

	// Method: getExplain(): get the word_explain

	public String getExplain() {
		return this.word_explain;
	}

}