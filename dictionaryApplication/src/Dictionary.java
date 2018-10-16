/** +---------------------------------------+
 *  | Project: Dictionary - Assignment 1	|
 *  | Class: Dictionary						|
 *  | Group: ULIS with love					|
 *  +---------------------------------------+
 */

package com.io;

import java.util.ArrayList;
import java.util.List;
import java.lang.NullPointerException;
import java.lang.IndexOutOfBoundsException;

// Save the list of Word

public class Dictionary {

	/*
		Attribute:
			- dictionary: list of Word
		Constructor:
		Method:
			- addWord(w)
			- removeWord(w)
			- getSize()
			- getWord(i)
			- getWord(t)
			- getPrefixLowerBound(t)
			- getPrefixUpperBound(t)
			- getAllWords()
	*/

	private List <Word> dictionary = new ArrayList<Word>();

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
		try {
			int sz = this.dictionary.size();
			return sz;
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
			return 0;
		}
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

	// Method: getWord(t): get the word w with target = t

	public Word getWord (String t) {
		try {
			int low = 0, high = this.dictionary.size();

			while (low < high) {
				int middle = (low + high) >> 1;
				String wt = this.dictionary.get(middle).getTarget();
				if (wt.compareToIgnoreCase(t) == 0) return this.dictionary.get(middle);
				if (wt.compareToIgnoreCase(t) < 0) low = middle + 1;
				else high = middle - 1;
			}

			return (low < this.dictionary.size() &&
					this.dictionary.get(low).getTarget().compareToIgnoreCase(t) == 0 ?
					this.dictionary.get(low) : null);
		}
		catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
			return null;
		}
	}

	// Method: getPrefixLowerBound(t): return the index of first element
	// w: w.target >= t (prefix only)

	public int getPrefixLowerBound (String t) {
		try {
			int low = 0, high = this.dictionary.size();

			while (low < high) {
				int middle = (low + high) >> 1;
				String wt = this.dictionary.get(middle).getTarget();
				wt = wt.substring(0, (wt.length() < t.length() ? wt.length() : t.length()));
				if (wt.compareToIgnoreCase(t) < 0) low = middle + 1;
				else high = middle;
			}

			return low;
		}
		catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
			return this.dictionary.size();
		}
	}

	// Method: getPrefixUpperBound(t): return the index of first element
	// w: w.target > t (prefix only)

	public int getPrefixUpperBound (String t) {
		try {
			int low = 0, high = this.dictionary.size();

			while (low < high) {
				int middle = (low + high) >> 1;
				String wt = this.dictionary.get(middle).getTarget();
				wt = wt.substring(0, (wt.length() < t.length() ? wt.length() : t.length()));
				if (wt.compareToIgnoreCase(t) <= 0) low = middle + 1;
				else high = middle;
			}

			return low;
		}
		catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
			return this.dictionary.size();
		}
	}

	// Method: getAllWords(): return all word.target

	public ArrayList<String> getAllWords() {
		try {
			ArrayList<String> res = new ArrayList<String>();

			for (int i = 0; i < this.dictionary.size(); ++i)
				res.add(this.dictionary.get(i).getTarget());

			return res;
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
			return null;
		}
	}

}