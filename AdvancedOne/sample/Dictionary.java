package sample;
/**
	Project: Dictionary (assignment 1)
	Class: Dictionary
	Group: ULIS with love
*/
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
// Save the list of Word

class Dictionary {

	/*
		Attribute:
			- dictionary: list of Word
		Constructor:
		Method:
			- addWord(w)
			- removeWord(w)
			- getSize()
			- getWord()
	*/
    public Dictionary(){
    }
	public List <Word> dictionary = new ArrayList<Word>();

	// Method: addWord(w): add a new word (w)

	public boolean addWord (Word w) {
		try {
			int low = 0, high = this.dictionary.size();
			String t = w.getTarget();

			while (low < high) {
				int middle = (low + high) >> 1;
				String wmt = this.dictionary.get(middle).getTarget();
				if (wmt.compareToIgnoreCase(t) == 0) return false;
				if (wmt.compareToIgnoreCase(t) < 0) low = middle + 1;
				else high = middle;
			}

			if (this.dictionary.size() == 0 || low == this.dictionary.size())
				this.dictionary.add(w);
			else
				this.dictionary.add(low, w);

			return true;
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
			return false;
		}
	}

	// Method: removeWord(w): remove the word (w)

	public boolean removeWord (Word w) {
		try {
			int old_size = this.dictionary.size();
			this.dictionary.remove(w);
			return (this.dictionary.size() != old_size);
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
			return false;
		}
	}

	// Method: getSize(): get the number of words

	public int getSize() {
		return this.dictionary.size();
	}

	// Method: getWord(i): get the ith word

	public Word getWord (int i) {
		try {
			Word w = this.dictionary.get(i);
			return w;
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Error: out of bound!");
			e.printStackTrace();
			return null;
		}
	}

	public Word getWord (String t) {
		try {
			for (int i = 0; i < this.dictionary.size(); ++i) {
				Word w = this.dictionary.get(i);
				if (w.getTarget().equalsIgnoreCase(t))
					return w;
			}
			return (new Word());
		}
		catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
			return (new Word());
		}
	}

}