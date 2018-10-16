/** +---------------------------------------+
 *  | Project: Dictionary - Assignment 1	|
 *  | Class: DictionaryManagement			|
 *  | Group: ULIS with love					|
 *  +---------------------------------------+
 */

package com.util;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

import com.io.*;

// Manage the dictionary

public class DictionaryManagement {

	/*
		Attribute:
			- dictionaryData: reference of Dictionary
		Constructor:
			- Default
			- Parameterized
		Method:
			- insertFromCommandLine()
			- insertFromFile(f)
			- insertFromDatabase(host, port, user, pass, dbname, tbl)
			- dictionaryLookup(t)
			- dictionaryAddWord(nt, ne)
			- dictionaryEditWord(t, ne)
			- dictionaryRemoveWord(t)
			- dictionarySearcher(t)
			- dictionaryExportToFile(f)
	*/

	private Dictionary dictionaryData;

	// Default constructor: do nothing

	public DictionaryManagement(){}

	// Parameterized constructor: reference of dictionary

	public DictionaryManagement (Dictionary d) {
		this.dictionaryData = d;
	}

	// Method: insertFromCommandLine(): read data from command-line

	public void insertFromCommandLine() {
		Scanner sc = new Scanner(System.in);

		// Read number of words
		System.out.print("Number of words: ");
		int wordCount = sc.nextInt();

		/*
			Read data:
				- Line 1: new word
				- Line 2: word's meaning
		*/

		sc.nextLine();	// skipping '\n'
		for (int i = 0; i < wordCount; ++i) {
			System.out.println("Word " + (i + 1));
			System.out.print("\tWord: ");
			String t = sc.nextLine();		// word_target
			System.out.print("\tExplain: ");
			String e = sc.nextLine();		// word_explain
			Word w = new Word(t, e);		// create new word
			this.dictionaryData.addWord(w);	// add word
		}
	}

	// Method: insertFromFile(f): read data from file f

	public boolean insertFromFile (String f) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(f));

			// Read number of words
			int wordCount = sc.nextInt();

			// Read data: {word}'\t'{meaning}

			sc.nextLine();	// skipping '\n'

			for (int i = 0; i < wordCount; ++i) {
				String l = sc.nextLine();		// get full line and then split by '\t'
				String[] tmp = l.split("\t", 2);
				String t = tmp[0];				// word_target
				String e = tmp[1];				// word_explain
				Word w = new Word(t, e);		// create new word
				this.dictionaryData.addWord(w);	// add word
			}
			return true;
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: file not found!");
			e.printStackTrace();
			return false;
		}
		catch (NoSuchElementException e) {
			System.out.println("Error: NoSuchElementException!");
			e.printStackTrace();
			return false;
		}
	}

	public boolean insertFromDatabase (String host, String port, String user, String pass, String dbname, String tbl) {
		Scanner sc = null;
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbname, user, pass);
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `" + tbl + "`");
			while (rs.next()) {
				String t = rs.getString(2);
				String e = rs.getString(3);
				Word w = new Word(t, e);
				this.dictionaryData.addWord(w);
			}
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error: SQLException!");
			e.printStackTrace();
			return false;
		}
	}

	// Method: dictionaryLookup(t): searching for word (t)

	public Word dictionaryLookup (String t) {
		try {
			return this.dictionaryData.getWord(t);
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
			return null;
		}
	}

	// Method: dictionaryAddWord(nt, ne): add new word (nt, ne)

	public boolean dictionaryAddWord (String nt, String ne) {
		try {
			// check if word has already been added
			if (this.dictionaryData.getWord(nt) != null) {
				System.out.println("This word has already been in dictionary!");
				System.out.println("You can use option 1 to edit a word!");
				return false;
			}

			this.dictionaryData.addWord(new Word(nt, ne));
			return true;
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
			return false;
		}
	}

	// Method: dictionaryEditWord(t, ne): edit word (t, e) to (t, ne)

	public boolean dictionaryEditWord (String t, String ne) {
		try {
			Word w = this.dictionaryData.getWord(t);
			if (w != null) {
				w.setExplain(ne);
				return true;
			}
			else
				return false;
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
			return false;
		}
	}

	// Method: dictionaryRemoveWord(t): remove word (t) from dictionary

	public boolean dictionaryRemoveWord (String t) {
		try {
			Word w = this.dictionaryData.getWord(t);
			if (w != null) {
				this.dictionaryData.removeWord(w);
				return true;
			}
			else
				return false;
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
			return false;
		}
	}

	// Method: dictionarySearcher(t): searching for words "t*"

	public Dictionary dictionarySearcher (String t) {
		try {
			Dictionary res = new Dictionary();

			int low = this.dictionaryData.getPrefixLowerBound(t);
			int high = this.dictionaryData.getPrefixUpperBound(t);

			for (int i = low; i < high; ++i)
				res.addWord(this.dictionaryData.getWord(i));

			return res;
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
			return null;
		}
	}

	// Method: dictionaryExportToFile(f): export data to file "f"

	public void dictionaryExportToFile (String f) {
		try {
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(new File("res/export/" + f));
				int sz = this.dictionaryData.getSize();
				pw.println(sz);
				for (int i = 0; i < sz; ++i) {
					Word w = this.dictionaryData.getWord(i);
					String t = w.getTarget();
					String e = w.getExplain().replace("\n", "\t");
					pw.printf("%s\t%s\n", t, e);
				}
				pw.flush();
				pw.close();
				System.out.println("Export successfully: res/export/" + f);
			}
			catch (IOException e) {
				System.out.println("Can't export to file!");
				e.printStackTrace();
			}
		}
		catch (NullPointerException e) {
			System.out.println("Error: NullPointerException");
			e.printStackTrace();
		}
	}

}