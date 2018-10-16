package sample;

import java.awt.*;
import java.net.URI;

public class Author {
        public void gotoMyFacebook(){
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("https://facebook.com/kenny.nguyen.153");
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void gotoDatabase(){
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("https://shimivn.blogspot.com/2016/03/share-database-tu-ien-anh-viet.html");
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
