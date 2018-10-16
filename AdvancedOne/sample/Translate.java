package sample;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import org.json.JSONArray;
public class Translate implements Initializable {

    @FXML
    TextArea textTarget;

    @FXML
    TextArea textExplain;

    @FXML
    ChoiceBox fromLang;

    @FXML
    ChoiceBox toLang;

    private ObservableList<String> listLang = FXCollections.observableArrayList("English","Vietnamese","French","Korean","Japanese","Chinese");
    private String callTranslate(String langFrom, String langTo, String word) throws Exception {

        String url = "https://translate.googleapis.com/translate_a/single?"+
                "client=gtx&"+
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return parseResult(response.toString());
        }
        catch (Exception e){
            return "Lỗi trong quá trình chuyển đổi\nLỗi này có thể do mất mạng";
        }
    }

    private String parseResult(String inputJson){


        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);

        return jsonArray3.get(0).toString();
    }

    @FXML
    public void translate() {
        Translate translate = new Translate();
        try {
            textExplain.setText(callTranslate(translate.getLangCode(fromLang.getValue().toString()),getLangCode(toLang.getValue().toString()), textTarget.getText()));
        } catch (Exception ex) {
            textExplain.setText("Lỗi trong quá trình dịch");
        }
    }

    private String getLangCode(String t){
        switch (t) {
            case "English":
                return "en";
            case "Vietnamese":
                return "vi";
            case "French":
                return "fr";
            case "Korean":
                return "ko";
            case "Chinese":
                return "zh";
            case "Japanese":
                return "ja";
            default:
                return null;
        }
    }

    public void autoTrans(){
        toLang.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            translate();
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fromLang.setItems(listLang);
        fromLang.getSelectionModel().select(1);
        toLang.setItems(listLang);
        toLang.getSelectionModel().select(0);
        autoTrans();
    }
}



