package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.sql.*;



public class MySQL {
    @FXML
    public TextField hostName;
    @FXML
    public TextField portName;
    @FXML
    public TextField userName;
    @FXML
    public PasswordField password;
    @FXML
    public TextField dbName;
    @FXML
    public TextField table;
    @FXML
    public Button importSQL;
    @FXML
    public CheckBox choiceReplace;


    public Dictionary dictionaryData;

    public void setDictionaryData(Dictionary dictionaryData) {
        this.dictionaryData = dictionaryData;
    }

    private Connection connection;

    public void getMySQLConnection(){
        try{
            String connectionURL = "jdbc:mysql://" +hostName.getText()+ ":" + portName.getText()+"/" + dbName.getText()+"?"+"useUnicode=true&characterEncoding=utf-8";
            connection = DriverManager.getConnection(connectionURL,userName.getText(),password.getText());
        }
        catch (SQLException e){
            e.printStackTrace();
            errorGetConnection();
        }
    }


    public void shutdown(){
        try{
            if (connection != null) {
                connection.close();
            }
        }
        catch (SQLException e){
            errorImport();
            e.printStackTrace();
        }
    }

    public void importFromMySQL(){
        try {
            if(choiceReplace.isSelected()){
                dictionaryData.dictionary.clear();
            }
            getMySQLConnection();
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from "+table.getText());
            while (rs.next()) {
                String word_target = rs.getString("word");
                String word_explain = rs.getString("detail");
                dictionaryData.addWord(new Word(word_target,word_explain));
            }
            succsessImport();
            stmnt.close();
        }
        catch (SQLException e){
            errorImport();
            e.printStackTrace();
        }
    }

    public void exportToMySQL(){
        try {
            getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO "+table.getText()+" (word, detail) VALUES (?, ?)");
            int sz = this.dictionaryData.getSize();
            for(int i=0;i<sz;i++){
                Word w = this.dictionaryData.getWord(i);
                stmt.setString(1, w.getTarget());
                stmt.setString(2, w.getExplain());
                stmt.executeUpdate();
            }
            succsessImport();
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            errorExport();
        }
    }

    public void cancelImport(){
        shutdown();
        Stage stage = (Stage) importSQL.getScene().getWindow();
        stage.close();
    }

    public void errorImport(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/error.png").toString()));
        alert.setTitle("Lỗi!");
        alert.setContentText("Import MySQL thất bại!");
        alert.showAndWait();
        shutdown();
    }

    public void errorExport(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/error.png").toString()));
        alert.setTitle("Lỗi!");
        alert.setContentText("Export MySQL thất bại!");
        alert.showAndWait();
        shutdown();
    }

    public void errorGetConnection(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/error.png").toString()));
        alert.setTitle("Lỗi!");
        alert.setContentText("Không kết nối được tới cơ sở dữ liệu! Kiểm tra lại thông tin");
        alert.showAndWait();
    }

    public void succsessImport(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
        stagealert.getIcons().add(new Image(this.getClass().getResource("/img/success.png").toString()));
        alert.setTitle("Thành công!");
        alert.setContentText("Import/Export MySQL thành công!");
        alert.showAndWait();
        Stage stage = (Stage) importSQL.getScene().getWindow();
        stage.close();
    }
}
