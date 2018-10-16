/**
 * Project: Dictionary (assignment 1)
 * Class Word
 * Group: ULIS with love
 */
import java.util.List;
import java.util.ArrayList;
public class Dictionary {
    /*
        Attribute:
            -dictionary: Luu tru mang word
        Constructor: do nothing
        Method:
            - addWord(w)
            - removeWord(w)
            - getSize()
            - getWord(int i)
     */
    private List <Word> dictionary = new ArrayList <Word>();
    public boolean addWord(Word w){
        try {
            int low = 0, high = this.dictionary.size();
            String t = w.getWord_target();

            while (low < high) {
                int middle = (low + high) >> 1;
                String wmt = this.dictionary.get(middle).getWord_target();
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
    public boolean removeWord(Word w){
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
    public int getSize(){
        return this.dictionary.size();
    }
    public Word getWord(int i){
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
                if (w.getWord_target().equalsIgnoreCase(t))
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
