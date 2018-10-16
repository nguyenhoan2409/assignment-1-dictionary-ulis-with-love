package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class AddWord{
    @FXML
    public TextField word_target;

    @FXML
    public TextArea word_explain;

    public Dictionary dictionaryData;

    public void setDictionaryData(Dictionary dictionaryData) {
        this.dictionaryData = dictionaryData;
    }


    public void dictionaryAddWord() {
        //Kiem tra tu da duoc add chua. Co roi == false
        boolean check = true;
        int sz = dictionaryData.getSize();
        for (int i = 0; i < sz; i++) {
            Word w = dictionaryData.getWord(i);
            if (w.getTarget().equalsIgnoreCase(word_target.getText())) {
                check = false;
                break;
            }
        }
        if((!check)){
            errorExist();
        }
        else if(!word_target.getText().trim().isEmpty()&&!word_explain.getText().trim().isEmpty()){
            dictionaryData.addWord(new Word(word_target.getText(),word_explain.getText()));
            succsessAdd();
        }
        else{
            errorEmpty();
        }
    }

    public void errorExist(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/error.png").toString()));
        alert.setTitle("Lỗi!");
        alert.setContentText("Không thể thêm từ vì từ này đã có trong từ điển");
        alert.showAndWait();
    }

    public void errorEmpty(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/error.png").toString()));
        alert.setTitle("Lỗi!");
        alert.setContentText("Không thể thêm từ vì bạn nhập thiếu trường");
        alert.showAndWait();
    }

    public void succsessAdd(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
        stagealert.getIcons().add(new Image(this.getClass().getResource("/img/success.png").toString()));
        alert.setTitle("Thành công!");
        alert.setContentText("Thêm từ thành công!");
        alert.showAndWait();
        Stage stage = (Stage) word_target.getScene().getWindow();
        stage.close();
    }

    public void CancelAdd(){
        Stage stage = (Stage) word_target.getScene().getWindow();
        stage.close();
    }

}
