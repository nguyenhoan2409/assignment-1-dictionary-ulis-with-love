/** +---------------------------------------+
 *  | Project: Dictionary - Assignment 1	|
 *  | Class: DictionaryCmd					|
 *  | Group: ULIS with love					|
 *  +---------------------------------------+
 */

package com.util;

import java.lang.NullPointerException;

import com.io.*;

// Commands for dictionary

public class DictionaryCmd {

	/*
		Attribute:
			- dictionaryData: reference of Dictionary
		Constructor:
			- Default
			- Parameterized
		Method:
			- showAllWords()
	*/

	private Dictionary dictionaryData;

	// Default constructor: do nothing

	public DictionaryCmd(){}

	// Parameterized constructor: reference of dictionary

	public DictionaryCmd (Dictionary d) {
		this.dictionaryData = d;
	}

	// Method: showAllWords(): show all words in dictionary

	public void showAllWords() {
		try {
			System.out.printf("%-8s|%-30s|%s\n", "No", "English", "Tieng Viet");
			int sz = this.dictionaryData.getSize();
			for (int i = 0; i < sz; ++i) {
				String no = String.valueOf(i + 1);			// No. of word
				Word w = this.dictionaryData.getWord(i);	// Word object
				String t = w.getTarget();					// word_target
				String e = w.getExplain();					// word_explain
				System.out.printf("%-8s|%-30s|%s\n", no, t, e);
			}
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
		}
	}

}