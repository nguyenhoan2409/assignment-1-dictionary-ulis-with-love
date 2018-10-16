package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DictionaryManagement {
    private Dictionary dictionaryData;
    // Default Constructor: do nothing
    public DictionaryManagement(){

    }
    // Parameterized constructor: tham chieu tu dien
    public DictionaryManagement(Dictionary d){
        this.dictionaryData = d;
    }

    public ObservableList<String> listTarget(String t) {
        int sz = this.dictionaryData.getSize();
        ObservableList<String> listTarget = FXCollections.observableArrayList();
        if(t!=null){
            if (t.trim().isEmpty()){
                for(int i=0;i<sz;i++){
                    Word w = this.dictionaryData.getWord(i);
                    listTarget.add(w.getTarget());
                }
            }
            else {
                for (int i = 0;i<sz;i++) {
                    Word w = this.dictionaryData.getWord(i);
                    if (w.getTarget().toUpperCase().startsWith(t.toUpperCase()))
                        listTarget.add(w.getTarget());
                }
            }
        }
        else{
            for(int i=0;i<sz;i++){
                Word w = this.dictionaryData.getWord(i);
                listTarget.add(w.getTarget());
            }
        }
        return listTarget;
    }
    public Word getWord(String t){
        return this.dictionaryData.getWord(t);
    }

    public void removeWord(String t){
        Word remove = this.dictionaryData.getWord(t);
        this.dictionaryData.removeWord(remove);
    }

    public void addWord(String target,String explain){
        Word w = new Word(target,explain);
        this.dictionaryData.addWord(w);
    }

    public void editWord (String wto,String word_target,String word_explain) {
        // check if word (wt) is in dictionary
        boolean check = false;
        // remove old word
        int sz = this.dictionaryData.getSize();
        for (int i = 0; i < sz; ++i) {
            Word w = this.dictionaryData.getWord(i);
            if (w.getTarget().equalsIgnoreCase(wto)) {
                check = true;
                this.dictionaryData.removeWord(w);
                break;
            }
        }

        // add new word if word (t) exist!
        if (check){
            this.dictionaryData.addWord(new Word(word_target,word_explain));
        }
    }
}
