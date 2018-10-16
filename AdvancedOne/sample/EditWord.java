package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EditWord {

    //Word target minh chon ban dau
    private String wto;

    //Data

    private Dictionary dictionaryData;

    public void setDictionaryData(Dictionary dictionaryData){
        this.dictionaryData = dictionaryData;
    }

    @FXML
    private TextField word_target;

    @FXML
    private TextArea word_explain;

    public void setOldWord(String wto,String weo){
        this.wto = wto;
        this.word_target.setText(wto);
        this.word_explain.setText(weo);
    }

    public void editWord() {
        try {
            if(!(word_target.getText().trim().isEmpty()||word_explain.getText().trim().isEmpty())){
                (new DictionaryManagement(dictionaryData)).editWord(wto, word_target.getText(), word_explain.getText());
                succsessEdit();
            }
            else{
                errorEdit();
            }
        } catch (Exception ex) {
            errorEdit();
            throw ex;
        }
    }
    public void cancelEdit(){
        Stage stage = (Stage) word_target.getScene().getWindow();
        stage.close();
    }
    public void errorEdit(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/error.png").toString()));
        alert.setTitle("Lỗi!");
        alert.setContentText("Sửa từ thất bại lỗi có thể do bạn để trống trường!");
        alert.showAndWait();
    }
    public void succsessEdit(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
        stagealert.getIcons().add(new Image(this.getClass().getResource("/img/success.png").toString()));
        alert.setTitle("Thành công!");
        alert.setContentText("Sửa từ thành công!");
        alert.showAndWait();
        Stage stage = (Stage) word_target.getScene().getWindow();
        stage.close();
    }
}
