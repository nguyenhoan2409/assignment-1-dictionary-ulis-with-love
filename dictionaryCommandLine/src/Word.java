/**
 * Project: Dictionary (assignment 1)
 * Class Word
 * Group: ULIS with love
 */
public class Word {
    /*
        Attribute:
            - word_target: tu moi
            - word_explain: giai nghia
        Constructor:
            - Word(): Default
            - Word(wt,we): Parameterized
        Method:
            - setWord_target
            - setWord_explain
            - getWord_target
            - getWord_explain

     */
    private String word_target,word_explain;
    public Word(){};
    public Word(String word_target,String word_explain){
        this.word_target = word_target;
        this.word_explain = word_explain;
    }
    // setWord
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }
    public void setWord_explain(String word_explain){
        this.word_explain = word_explain;
    }
    //Get word
    public String getWord_target() {
        return word_target;
    }
    public String getWord_explain() {
        return word_explain;
    }
}
