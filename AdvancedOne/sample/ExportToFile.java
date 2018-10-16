package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ExportToFile {
    public Dictionary dictionaryData;

    public void setDictionaryData(Dictionary dictionaryData) {
        this.dictionaryData = dictionaryData;
    }

    @FXML
    TextField source;



    public void dictionaryExportToFile() {

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(source.getText());
            int sz = this.dictionaryData.getSize();
            pw.println(sz);
            for (int i = 0; i < sz; ++i) {
                Word w = this.dictionaryData.getWord(i);
                pw.printf("%s|%s\n", w.getTarget(), w.getExplain());
            }
            pw.flush();
            succsessExport();
        }
        catch (IOException e) {
            errorExport();
        }
    }

    public void cancelExport(){
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void errorExport(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/error.png").toString()));
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().addAll(buttonTypeOk);
        alert.setTitle("Lỗi!");
        alert.setContentText("Xuất file thất bại!");
        alert.showAndWait();
    }

    public void succsessExport(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
        stagealert.getIcons().add(new Image(this.getClass().getResource("/img/success.png").toString()));
        alert.setTitle("Thành công!");
        alert.setContentText("Xuất file thành công!");
        alert.showAndWait();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void selectDir(){
        source.clear();
        Stage primaryStage =(Stage) source.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("CHỌN THƯ MỤC LƯU");

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);


        //Show save file dialog
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            source.setText(file.getPath());
        }
    }
}
