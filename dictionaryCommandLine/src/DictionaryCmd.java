/**
 * Project: Dictionary (assignment 1)
 * Class DictionaryCmd
 * Group: ULIS with love
 */
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
    	public DictionaryCmd(){
        }
        public DictionaryCmd(Dictionary d){
        this.dictionaryData = d;
        }
        public void showAllWords(){
            System.out.printf("%-8s|%-30s|%s\n", "No", "English", "Tieng Viet");
            int sz = this.dictionaryData.getSize();
            for (int i = 0; i < sz; ++i) {
                String no = String.valueOf(i + 1);			// No. of word
                Word w = this.dictionaryData.getWord(i);	// Word object
                String t = w.getWord_target();					// word_target
                String e = w.getWord_explain();					// word_explain
                System.out.printf("%-8s|%-30s|%s\n", no, t, e);
            }
        }
}
