/**
 * Project: Dictionary (assignment 1)
 * Class Word
 * Group: ULIS with love
 */
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.String;
public class DictionaryManagement {
    /*
        Attribute:
            - dictionaryData: reference of Dictionary
        Constructor:
            - Default
            - Parameterized
        Method:
            - insertFromCommandLine()
            - insertFromFile()
    */
    private Dictionary dictionaryData;
    // Default Constructor: do nothing
    public DictionaryManagement(){

    }
    // Parameterized constructor: tham chieu tu dien
    public DictionaryManagement(Dictionary d){
        this.dictionaryData = d;
    }
    public void insertFromCommandline(){
        Scanner sc = new Scanner(System.in);
        // Read number word
        int wordCount = sc.nextInt();

        /*
                Read data Word:
                    - Line 1: new word
                    - Line 2: word's meaning
         */
        sc.nextLine();
        for(int i=0;i<wordCount;i++){
            String t = sc.nextLine();
            String e = sc.nextLine();
            this.dictionaryData.addWord(new Word(t,e));
        }
    }
    public void insertFromFile(){
        Scanner sc = null;
        try {
            sc = new Scanner(new File("dictionaries.txt"));
            // Read number of words
            int wordCount = sc.nextInt();

			/*
				Read data:
					- word_target|word_explain
			*/

            sc.nextLine();    // skipping '\n'
            for(int i=0;i<wordCount;i++){
                String line = sc.nextLine();
                String word_target = line.split("\t")[0];
                String word_explain = line.split("\t")[1];
                this.dictionaryData.addWord(new Word(word_target,word_explain));
            }
        } catch (FileNotFoundException e){
            System.out.println("Loi: Khong tim thay file dictionaries.txt\n");
        }
    }
    public void dictionaryAddWord(String word_target, String word_explain) {
        // Kiem tra tu da duoc add chua. Co roi == false
        boolean check = true;
        boolean empty = true;
        if(word_target.trim().isEmpty()||!word_explain.trim().isEmpty()){
            empty = false;
        }
        int sz = this.dictionaryData.getSize();
        for (int i = 0; i < sz; i++) {
            Word w = this.dictionaryData.getWord(i);
            if (w.getWord_target().equalsIgnoreCase(word_target)) {
                check = false;
                break;
            }
        }
        if(!check){
            System.out.println("Tu nay da co trong du lieu");
            System.out.println("Ban co the su dung option 2 de sua tu nay");
            return;
        }
        else if(empty){
            System.out.println("Tu vua nhap khong hop le");
        }
        else{
            this.dictionaryData.addWord(new Word(word_target,word_explain));
            (new DictionaryCmd(this.dictionaryData)).showAllWords();
        }
    }
    public void dictionaryEditWord (String wt) {
        // check if word (wt) is in dictionary
        boolean check = false;
        // remove old word
        int sz = this.dictionaryData.getSize();
        for (int i = 0; i < sz; ++i) {
            Word w = this.dictionaryData.getWord(i);
            if (w.getWord_target().equalsIgnoreCase(wt)) {
                check = true;
                this.dictionaryData.removeWord(w);
                break;
            }
        }

        // add new word if word (t) exist!
        if (check){
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhap lai tu moi:");
            wt = sc.nextLine();
            System.out.println("Nhap vao giai nghia moi:");
            String we = sc.nextLine();
            this.dictionaryData.addWord(new Word(wt,we));
        }
        // show new dictionary
        (new DictionaryCmd(this.dictionaryData)).showAllWords();
    }
    public void dictionaryRemoveWord (String t) {
        // remove word
        int sz = this.dictionaryData.getSize();
        for (int i = 0; i < sz; ++i) {
            Word w = this.dictionaryData.getWord(i);
            if (w.getWord_target().equalsIgnoreCase(t)) {
                this.dictionaryData.removeWord(w);
                break;
            }
        }
        // show new dictionary
        (new DictionaryCmd(this.dictionaryData)).showAllWords();
    }

    // Method: dictionarySearcher(t): searching for words "t*"

    public void dictionarySearcher (String t) {
        int sz = this.dictionaryData.getSize();
        System.out.printf("%-8s|%-30s|%s\n", "No", "English", "Tieng Viet");

        for (int i = 0, no = 0; i < sz; ++i) {
            Word w = this.dictionaryData.getWord(i);
            if (w.getWord_target().toUpperCase().startsWith(t.toUpperCase()))
                System.out.printf("%-8s|%-30s|%s\n", String.valueOf(++no), w.getWord_target(), w.getWord_explain());
        }
    }

    // Method: dictionaryExportToFile(f): export data to file "f"

    public void dictionaryExportToFile (String f) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(f));
            int sz = this.dictionaryData.getSize();
            pw.println(sz);
            for (int i = 0; i < sz; ++i) {
                Word w = this.dictionaryData.getWord(i);
                pw.printf("%s\t%s\n", w.getWord_target(), w.getWord_explain());
            }
            pw.flush();
            System.out.printf("Xuat file %s thanh cong\n",f);
        }
        catch (IOException e) {
            System.err.println("\nLoi: Khong ghi duoc file");
        }
    }
}
