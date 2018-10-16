/** +---------------------------------------+
 *  | Project: Dictionary - Assignment 1	|
 *  | Class: DictionaryCommandLine			|
 *  | Group: ULIS with love					|
 *  +---------------------------------------+
 */

package com.cmd;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.lang.System;
import java.io.IOException;

import com.io.*;
import com.util.*;

/** Use dictionary from command-line */

class DictionaryCommandLine {

	/*
		Attribute:
			- dictionaryData: reference of Dictionary
		Constructor:
		Method:
			- dictionaryBasic()
			- dictionaryAdvanced()
			- main()
	*/

	private Dictionary dictionaryData;

	// Method: dictionaryBasic(): run dictionary using command-line

	public void dictionaryBasic() {
		this.dictionaryData = new Dictionary();
		(new DictionaryManagement(this.dictionaryData)).insertFromCommandLine();
		(new DictionaryCmd(this.dictionaryData)).showAllWords();
	}

	// Method: dictionaryAdvanced(): update (read data from file, look up words)

	public void dictionaryAdvanced() {
		this.dictionaryData = new Dictionary();
		(new DictionaryManagement(this.dictionaryData)).insertFromFile("res/dictionaries/dictionaries.txt");
		(new DictionaryCmd(this.dictionaryData)).showAllWords();

		Scanner sc = new Scanner(System.in);

		for (boolean loop = true; loop; ) {
			System.out.print("Option: 0 - add word | 1 - edit word | 2 - remove word | 3 - search word | 4 - search prefix | 5 - export to file | 6 - exit: ");
			
			int option = -1;

			try {
				option = sc.nextInt();
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println("Option must be a number!");
				sc.nextLine();
				continue;
			}

			switch (option) {
				case 0: {
					System.out.print("Input the new word: ");
					String t = sc.nextLine();
					System.out.print("Input the word's meaning: ");
					String e = sc.nextLine();

					// add new word
					(new DictionaryManagement(this.dictionaryData)).dictionaryAddWord(t, e);
					(new DictionaryCmd(this.dictionaryData)).showAllWords();

					break;
				}

				case 1: {
					System.out.print("Input the word: ");
					String t = sc.nextLine();
					System.out.print("Input the new word's meaning: ");
					String ne = sc.nextLine();

					// edit word
					(new DictionaryManagement(this.dictionaryData)).dictionaryEditWord(t, ne);
					(new DictionaryCmd(this.dictionaryData)).showAllWords();

					break;
				}

				case 2: {
					System.out.print("Input the word: ");
					String t = sc.nextLine();

					// remove word
					(new DictionaryManagement(this.dictionaryData)).dictionaryRemoveWord(t);
					(new DictionaryCmd(this.dictionaryData)).showAllWords();

					break;
				}

				case 3: {
					System.out.print("Input the word: ");
					String t = sc.nextLine();

					// Look up the word match (t) exactly
					Word d = (new DictionaryManagement(this.dictionaryData)).dictionaryLookup(t);
					if (d == null)
						System.out.println("Word not found!");
					else
						System.out.println(d.getTarget() + ": " + d.getExplain());

					break;
				}

				case 4: {
					System.out.print("Input the word: ");
					String t = sc.nextLine();

					// Look up all words have (t) as prefix
					Dictionary d = (new DictionaryManagement(this.dictionaryData)).dictionarySearcher(t);
					(new DictionaryCmd(d)).showAllWords();

					break;
				}

				case 5: {
					System.out.print("Input the output file name: ");
					String f = sc.nextLine();

					// Export data to file
					(new DictionaryManagement(this.dictionaryData)).dictionaryExportToFile(f);

					break;
				}

				case 6: {
					loop = false;
					break;
				}

				default:
					System.out.println("Invalid option!");
			}
		}
	}

	// Method: main(): start testing

	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in);

		for (boolean loop = true; loop; ) {
			System.out.print("Data source: 0 - Command line | 1 - File dictionaries.txt | 2 - exit: ");
			
			int option = -1;

			try {
				option = sc.nextInt();
			}
			catch (InputMismatchException e) {
				System.out.println("Option must be a number!");
				sc.nextLine();
				continue;
			}

			switch (option) {
				case 0:
					(new DictionaryCommandLine()).dictionaryBasic();
					loop = false;
					break;
				case 1:
					(new DictionaryCommandLine()).dictionaryAdvanced();
					loop = false;
					break;
				case 2: {
					loop = false;
					break;
				}
				default:
					System.out.println("Invalid option!");
			}
		}
	}

}