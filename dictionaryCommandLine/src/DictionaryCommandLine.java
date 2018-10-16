import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Project: Dictionary (assignment 1)
 * Class DictionaryCommandLine
 * Group: ULIS with love
 */
public class DictionaryCommandLine {
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
    public void dictionaryBasic(){
        this.dictionaryData = new Dictionary();
        (new DictionaryManagement(this.dictionaryData)).insertFromCommandline();
        (new DictionaryCmd(this.dictionaryData)).showAllWords();
    }
    public void dictionaryAdvanced(){
        this.dictionaryData = new Dictionary();
        System.out.println("Nhap du lieu tu file dictionaries.txt");
        (new DictionaryManagement(this.dictionaryData)).insertFromFile();
        (new DictionaryCmd(this.dictionaryData)).showAllWords();
        Scanner sc = new Scanner(System.in);
        while(true){
            int option = -1;
            System.out.println("\nSu dung:  1. Them tu  |  2. Sua tu  |  3. Xoa tu  |  4. Tim tu  |  5. Xuat ra file| 6.Show All Word");
            System.out.print("Nhap lua chon: ");
            try{
                option = sc.nextInt();
            }
            catch (InputMismatchException e){
            }
            sc.nextLine();
            switch (option){
                case 1: {
                    System.out.println("Nhap vao new word:");
                    String t = sc.nextLine();
                    System.out.println("Nhap vao giai nghia:");
                    String e = sc.nextLine();
                    (new DictionaryManagement(this.dictionaryData)).dictionaryAddWord(t,e);
                    break;
                }
                case 2:{
                    System.out.println("Nhap tu can sua:");
                    String t = sc.nextLine();
                    (new DictionaryManagement(this.dictionaryData)).dictionaryEditWord(t);
                    break;
                }
                case 3: {
                    System.out.print("Nhap tu can xoa: ");
                    String t = sc.nextLine();
                    // remove word
                    (new DictionaryManagement(this.dictionaryData)).dictionaryRemoveWord(t);
                    break;
                }
                case 4: {
                    System.out.print("Nhap tu can tim: ");
                    String t = sc.nextLine();

                    // Look up this word
                    (new DictionaryManagement(this.dictionaryData)).dictionarySearcher(t);

                    break;
                }

                case 5: {
                    // Export data to file
                    System.out.print("Nhap ten file xuat: ");
                    String f = sc.nextLine();
                    (new DictionaryManagement(this.dictionaryData)).dictionaryExportToFile(f);

                    break;
                }
                case 6:{
                    //Show all word
                    (new DictionaryCmd(this.dictionaryData)).showAllWords();
                    break;
                }
                default:
                    System.out.println("Nhap lua chon trong khoang 1-6");
            }
        }
    }
    public static void main (String args[]){
        (new DictionaryCommandLine()).dictionaryAdvanced();
    }
}