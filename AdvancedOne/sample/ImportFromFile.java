package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ImportFromFile {
    public Dictionary dictionaryData;

    public void setDictionaryData(Dictionary dictionaryData) {
        this.dictionaryData = dictionaryData;
    }

    @FXML
    public TextField source;

    @FXML
    public CheckBox remove;



    public void insertFromFile() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(source.getText()));
            if(remove.isSelected()){
                dictionaryData.dictionary.clear();
            }
            // Read number of words
            int wordCount = sc.nextInt();

			/*
				Read data:
					- Line 1: new word
					- Line 2: word's meaning
			*/

            sc.nextLine();	// skipping '\n'
            for (int i = 0; i < wordCount; ++i) {
                String l = sc.nextLine();		// get full line: {target}'\t'{explain}
                String[] readLine = l.split("\t",2);
                String t = readLine[0];	// word_target
                String e = readLine[1];	// word_explain
                Word w = new Word(t, e);		// create new word
                this.dictionaryData.addWord(w);	// add word
            }
            succsessExport();
        }
        catch (FileNotFoundException e){
            errorExport();
        }
    }

    public void cancelImport(){
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void selectFile(){
        source.clear();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("CHỌN FILE IMPORT");
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            source.setText(file.getPath());
        }
    }

    public void errorExport(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/error.png").toString()));
        alert.setTitle("Lỗi!");
        alert.setContentText("Import file thất bại!");
        alert.showAndWait();
    }

    public void succsessExport(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
        stagealert.getIcons().add(new Image(this.getClass().getResource("/img/success.png").toString()));
        alert.setTitle("Thành công!");
        alert.setContentText("Import file thành công!");
        alert.showAndWait();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
