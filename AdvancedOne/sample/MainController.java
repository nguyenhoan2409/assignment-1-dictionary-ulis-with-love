package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainController implements Initializable {
    private Dictionary dictionaryData;

    @FXML
    public Label word_explain;

    @FXML
    public Label spell;

    @FXML
    public Label target;


    @FXML
    public ListView<String> listWord;

    @FXML
    public TextField word_target;

    @FXML
    public ImageView speaker;

    @FXML
    public Button removeButton;

    @FXML
    public Button editButton;

    /*
    Load Stage Google Trans()
     */

    @FXML
    public void loadGoogleTrans() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/translate.fxml"));
        Parent apisence = loader.load();
        Stage api = new Stage();
        api.initModality(Modality.APPLICATION_MODAL);
        api.setResizable(false);
        api.setScene(new Scene(apisence));
        api.getIcons().add(new Image(Main.class.getResourceAsStream("/img/api.png")));
        api.setTitle("Dictionary Advanced ULIS TEAM - GOOGLE API");
        api.show();
    }

    /*
    Load Stage Add Word
     */

    @FXML
    public void loadAddWord() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addWord.fxml"));
        Parent addwordsence = loader.load();
        Stage addWord = new Stage();
        addWord.initModality(Modality.APPLICATION_MODAL);
        addWord.setResizable(false);
        addWord.setScene(new Scene(addwordsence));

        // Trỏ dictionaryData của AddWord vào đối tượng mà dictionaryData ở đây trở đến
        AddWord data = loader.getController();
        data.setDictionaryData(dictionaryData);

        addWord.getIcons().add(new Image(Main.class.getResourceAsStream("/img/add-3.png")));
        addWord.setTitle("Dictionary Advanced ULIS TEAM - ADD WORD");
        addWord.showAndWait();
        updateListView();
    }


    /*
    Load Stage Edit Word
     */



    @FXML
    public void loadEditWord() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editWord.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        //Trỏ dictionaryData của EditWord vào đối tượng mà dictionaryData ở đây trở đến,truyển vào word_target được select
        EditWord data = loader.getController();
        data.setDictionaryData(dictionaryData);
        String wto = listWord.getSelectionModel().getSelectedItem();
        String weo = (new DictionaryManagement(this.dictionaryData)).getWord(wto).getExplain();
        data.setOldWord(wto,weo);

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/edit-1.png")));
        stage.setTitle("Dictionary Advanced ULIS TEAM - EDIT WORD");
        stage.showAndWait();
        updateListView();
    }

    /*
    Load Export To File
     */
    @FXML
    public void loadExportToFile() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/exportToFile.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        //Trỏ dictionaryData của Export To File vào đối tượng mà dictionaryData ở đây trở đến,truyển vào word_target được select
        ExportToFile data = loader.getController();
        data.setDictionaryData(dictionaryData);

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/save.png")));
        stage.setTitle("Dictionary Advanced ULIS TEAM - EXPORT TO FILE");
        stage.showAndWait();
        updateListView();
    }
    /*
    load Import From File
     */

    @FXML
    public void loadImportFromFile() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/importFromFile.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        //Trỏ dictionaryData của ImportFromFile vào đối tượng mà dictionaryData ở đây trở đến,truyển vào word_target được select
        ImportFromFile data = loader.getController();
        data.setDictionaryData(dictionaryData);

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/folder-15.png")));
        stage.setTitle("Dictionary Advanced ULIS TEAM - IMPORT FROM FILE");
        stage.showAndWait();
        updateListView();
    }

    /*
    Load Import/Export From MySQL
     */

    @FXML
    public void loadImportFromMySQL() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mysql.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        //Trỏ dictionaryData của ImportMySQL vào đối tượng mà dictionaryData ở đây trở đến,truyển vào word_target được select
        MySQL data = loader.getController();
        data.setDictionaryData(dictionaryData);

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/database-1.png")));
        stage.setTitle("Dictionary Advanced ULIS TEAM - IMPORT/EXPORT MySQL");
        stage.showAndWait();
        updateListView();
    }


    /*
    Load Author
     */

    @FXML
    public void loadAuthor() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/author.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/user-3.png")));
        stage.setTitle("Dictionary Advanced ULIS TEAM - AUTHOR");
        stage.showAndWait();
        updateListView();
    }


    // Update List view dựa trên ô search
    public void updateListView() {
        String findWord = word_target.getText();
        listWord.getItems().removeAll();
        //Nếu findWord == NULL thì listTarget trả về All != Null thì trả về danh sách từ bắt đầu bằng findWord
        listWord.setItems((new DictionaryManagement(this.dictionaryData)).listTarget(findWord));
        listWord.getSelectionModel().selectFirst();

        // Ẩn nút phát âm, tắt 2 nút xóa và sửa nếu không có từ nào được chọn
        if(listWord.getSelectionModel().getSelectedItem()==null){
            speaker.setVisible(false);
            removeButton.setDisable(true);
            editButton.setDisable(true);
        }
    }

    private void selectWord(){
        listWord.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String line = (new DictionaryManagement(this.dictionaryData)).getWord(newValue).getExplain();
            if(line != null){
                String [] ans = line.split("\t");
                StringBuilder result= new StringBuilder();
                for (int i=1;i<ans.length;i++) {
                    result.append(ans[i]).append("\n");
                }
                spell.setText(ans[0]);
                word_explain.setText(result.toString());
                target.setText(listWord.getSelectionModel().getSelectedItem());
                //hiện nút phát âm,bật nút sửa và xóa từ nếu có từ được chọn
                speaker.setVisible(true);
                removeButton.setDisable(false);
                editButton.setDisable(false);
            }
            else{
                target.setText("");
                word_explain.setText("");
                spell.setText("");
            }
        });
    }

    public void speakEng(){
        System.setProperty("mbrola.base","D:/Study/OPP-JAVA/Dictionaryver4/src/res/mbrola");
        System.setProperty("freetts.voice","de.dfki.lt.freetts.en.us.MbrolaVoiceDirectory");
        VoiceManager vm = VoiceManager.getInstance();
        Voice voice = vm.getVoice("mbrola_us1");
        voice.allocate();
        voice.speak(listWord.getSelectionModel().getSelectedItem());
        voice.deallocate();
    }

    public void removeWord(){
        Alert confirmRemove = new Alert(Alert.AlertType.NONE);
        Stage stage = (Stage) confirmRemove.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/garbage-2.png").toString()));
        confirmRemove.setTitle("Xác nhận xóa");
        confirmRemove.setContentText("Xóa từ "+listWord.getSelectionModel().getSelectedItem()+" ?");
        ButtonType buttonTypeOk = new ButtonType("Có", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmRemove.getButtonTypes().addAll(buttonTypeOk,buttonTypeCancel);
        Optional<ButtonType> resultRemove = confirmRemove.showAndWait();
        if(resultRemove.get()==buttonTypeOk){
            (new DictionaryManagement(this.dictionaryData)).removeWord(listWord.getSelectionModel().getSelectedItem());
            updateListView();
        }
        else{
            confirmRemove.close();
        }
    }

    public void removeAllWord(){
        Alert confirmRemove = new Alert(Alert.AlertType.NONE);
        Stage stage = (Stage) confirmRemove.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/garbage-1.png").toString()));
        confirmRemove.setTitle("Xác nhận xóa");
        confirmRemove.setContentText("Bạn có chắc chắn muốn xóa toàn bộ từ điển không?");
        ButtonType buttonTypeOk = new ButtonType("Có", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmRemove.getButtonTypes().addAll(buttonTypeOk,buttonTypeCancel);
        Optional<ButtonType> resultRemove = confirmRemove.showAndWait();
        if(resultRemove.get()==buttonTypeOk){
            try{
                this.dictionaryData.dictionary.clear();
                updateListView();
            }
            catch (NullPointerException e){
                System.out.println("Tu dien trong");
            }
        }
        else{
            confirmRemove.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resource){
        dictionaryData = new Dictionary();
        listWord.setItems((new DictionaryManagement(this.dictionaryData)).listTarget(null));
        selectWord();
    }
}