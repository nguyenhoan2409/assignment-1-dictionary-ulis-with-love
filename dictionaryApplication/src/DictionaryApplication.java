/** +---------------------------------------+
 *  | Project: Dictionary (assignment 1)	|
 *  | Class: DictionaryApplication			|
 *  | Group: ULIS with love					|
 *  +---------------------------------------+
 */

package com.application;

import javafx.application.Application;
import javafx.beans.binding.*;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.Desktop;
import java.net.URI;
import java.net.URL;

import com.io.*;
import com.util.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 *  Create a Stage using JavaFX
 *  and run application
 */

public class DictionaryApplication extends Application {

	/**  Dictionary fields for application */

	// Save all words in dictionary

	private Dictionary dictionaryData = new Dictionary();

	// Save recent word

	private Dictionary recentWord = new Dictionary();

	// Manage properties for application

	private static Properties prop = new Properties();
	private static ObservableResourceFactory resourceFactory = new ObservableResourceFactory();

	// Directory of config file

	private static final String CONFIG_PROPERTIES = "res/config/config.properties";

	// Directory of locale file for multi-language

	private static final String BUNDLE_BASENAME = "res/config/locale/locale";

	// Directory of en, vi locale file

	private static final Locale ENGLISH_LOCALE = new Locale("en", "US");
	private static final Locale VIETNAMESE_LOCALE = new Locale("vi", "VN");

	/**
	 *  Start application
	 *  If user runs application with "clean" argument, then reset all configs
	 */

	public static void main (String[] args) {
		for (int i = 0; i < args.length; ++i)
			if (args[i].equalsIgnoreCase("clean")) {
				DictionaryApplication.resetProperties();
				break;
			}
		Application.launch(args);
	}

	/**
	 *  Method for resetting all properties
	 */

	public static void resetProperties() {
		OutputStream out = null;

		try {
			out = new FileOutputStream(CONFIG_PROPERTIES);

			prop.setProperty("dictionary.voice", "us1");
			prop.setProperty("dictionary.defaultLocale", "vi");

			prop.setProperty("dictionary.dictlist.txt.source", "dict.txt");

			prop.setProperty("dictionary.dictlist.db.host", "localhost");
			prop.setProperty("dictionary.dictlist.db.port", "3306");
			prop.setProperty("dictionary.dictlist.db.username", "java");
			prop.setProperty("dictionary.dictlist.db.password", "07011999");
			prop.setProperty("dictionary.dictlist.db.database", "edict");
			prop.setProperty("dictionary.dictlist.db.table", "tbl_edict");

			prop.store(out, null);

			System.out.println("Config.properties reset!");
		}
		catch (IOException e) {
			System.out.println("Error while reset properties!");
			e.printStackTrace();
		}
		finally {
			if (out != null)
				try {
					out.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 *  Method for reloading wordWntries
	 */

	public void reloadEntries (ListView<String> lv, Dictionary d) {
		for (int i = 0; i < this.recentWord.getSize(); ++i)
			if ((new DictionaryManagement(this.dictionaryData)).dictionaryLookup(this.recentWord.getWord(i).getTarget()) == null)
				(new DictionaryManagement(this.recentWord)).dictionaryRemoveWord(this.recentWord.getWord(i).getTarget());
		lv.getItems().clear();
		lv.getItems().addAll(d.getAllWords());
	}

	/**
	 *  Start application
	 */

	@Override
	public void start (Stage stage) {

		// Read config.properties

		InputStream input = null;

		try {
			input = new FileInputStream(CONFIG_PROPERTIES);
			prop.load(input);
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: config.properties not found!");
			System.out.println("Creating file config.properties ...");
			DictionaryApplication.resetProperties();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {

			// Reload config

			try {
				input = new FileInputStream(CONFIG_PROPERTIES);
				prop.load(input);
			}
			catch (IOException e){}
			finally {
				if (input != null)
					try {
						input.close();
					}
					catch (IOException e){}
			}
		}

		// Set resource bundle

		resourceFactory.setResources(
			ResourceBundle.getBundle(BUNDLE_BASENAME,
			(prop.getProperty("dictionary.defaultLocale").equals("en") ? ENGLISH_LOCALE : VIETNAMESE_LOCALE)));

		// Create Scene

		Scene scene = new Scene(new Group());
		scene.getStylesheets().add("res/css/style.css");

		// Set properties of Stage

		stage.setMaximized(true);
		stage.setMinWidth(900);
		stage.setMinHeight(600);
		stage.getIcons().add(new Image("res/images/icon.png"));
		stage.titleProperty().bind(resourceFactory.getStringBinding("dictionary.title"));

		// Create box

		VBox[] mainVBox = new VBox[2];
		VBox[] subVBox = new VBox[2];
		HBox[] mainHBox = new HBox[4];
		HBox[] subHBox = new HBox[3];

		// 1. HBox of searchWord

		Label searchWord_Label = new Label();
		searchWord_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.search"));
		searchWord_Label.setStyle("-fx-min-width:40px;-fx-max-width:40px;");
		TextField searchWord_TextField = new TextField();
		searchWord_TextField.setMinWidth(170);

		mainHBox[0] = new HBox(15, searchWord_Label, searchWord_TextField);
		HBox.setHgrow(searchWord_Label, Priority.ALWAYS);
		mainHBox[0].setId("searchWord");

		// 2. HBox of wordList

		RadioButton allWord_Button = new RadioButton();
		allWord_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.all"));
		RadioButton recentWord_Button = new RadioButton();
		recentWord_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.recent"));
		
		allWord_Button.setSelected(true);

		ToggleGroup wordList = new ToggleGroup();
		wordList.getToggles().addAll(allWord_Button, recentWord_Button);

		mainHBox[1] = new HBox(allWord_Button, recentWord_Button);
		mainHBox[1].setId("wordlist");

		// 3. VBox of wordEntries

		Label wordEntries_Label = new Label();
		wordEntries_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.entries"));
		ListView<String> wordEntries_ListView = new ListView<String>();
		wordEntries_Label.getStyleClass().add("bold");

		wordEntries_ListView.setId("wordEntries");
		VBox.setVgrow(wordEntries_ListView, Priority.ALWAYS);

		subVBox[0] = new VBox(3, wordEntries_Label, wordEntries_ListView);

		// 4. HBox of menuBar

		Tab dictionariesMenu = new Tab();
		dictionariesMenu.textProperty().bind(resourceFactory.getStringBinding("dictionary.dictionaries"));
		Tab toolsMenu = new Tab();
		toolsMenu.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool"));
		Tab helpMenu = new Tab();
		helpMenu.textProperty().bind(resourceFactory.getStringBinding("dictionary.help"));

		// 4.1. Dictionaries tab

		Button dictTxt_Button = new Button("", new ImageView("res/images/text.png"));
		dictTxt_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.dictionary.txt"));
		dictTxt_Button.getStyleClass().add("w7button");
		dictTxt_Button.setPrefWidth(120);

		Button dictDb_Button = new Button("", new ImageView("res/images/database.png"));
		dictDb_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.dictionary.mysql"));
		dictDb_Button.getStyleClass().add("w7button");
		dictDb_Button.setPrefWidth(120);

		subHBox[0] = new HBox(10, dictTxt_Button, dictDb_Button);
		dictionariesMenu.setContent(subHBox[0]);
		subHBox[0].getStyleClass().add("tab-content");

		// 4.2. Tools tab

		Button dictMgmt_Button = new Button("", new ImageView("res/images/dictmgmt.png"));
		dictMgmt_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt"));
		dictMgmt_Button.getStyleClass().add("w7button");

		Button listening_Button = new Button("", new ImageView("res/images/listening.png"));
		listening_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.listening"));
		listening_Button.getStyleClass().add("w7button");

		Button googleApi_Button = new Button("", new ImageView("res/images/ggtrans.png"));
		googleApi_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.google"));
		googleApi_Button.getStyleClass().add("w7button");

		Button options_Button = new Button("", new ImageView("res/images/options.png"));
		options_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.option"));
		options_Button.getStyleClass().add("w7button");

		subHBox[1] = new HBox(5, dictMgmt_Button, listening_Button, googleApi_Button, options_Button);
		toolsMenu.setContent(subHBox[1]);
		subHBox[1].getStyleClass().add("tab-content");

		// 4.3. Help tab

		Button home_Button = new Button("", new ImageView("res/images/home.png"));
		home_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.help.home"));
		home_Button.getStyleClass().add("w7button");

		Button about_Button = new Button("", new ImageView("res/images/about.png"));
		about_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.help.about"));
		about_Button.getStyleClass().add("w7button");

		Button help_Button = new Button("", new ImageView("res/images/help.png"));
		help_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.help.help"));
		help_Button.getStyleClass().add("w7button");

		subHBox[2] = new HBox(5, home_Button, about_Button, help_Button);
		helpMenu.setContent(subHBox[2]);
		subHBox[2].getStyleClass().add("tab-content");

		// 4.4. Create TabPane

		TabPane menuBar = new TabPane();
		menuBar.getTabs().addAll(dictionariesMenu, toolsMenu, helpMenu);
		menuBar.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		HBox.setHgrow(menuBar, Priority.ALWAYS);

		Button langSwitch = new Button();
		langSwitch.textProperty().bind(resourceFactory.getStringBinding("dictionary.langswitch"));
		
		langSwitch.getStyleClass().add("w7button");
		langSwitch.getProperties().put("locale", prop.getProperty("dictionary.defaultLocale"));

		Button facebook_Button = new Button("", new ImageView("res/images/facebook.png"));
		facebook_Button.setStyle("-fx-focus-traversable:false;");		
		facebook_Button.setPadding(new Insets(3));
		
		subVBox[1] = new VBox(5, langSwitch, facebook_Button);
		subVBox[1].setStyle("-fx-alignment:top-right;");

		mainHBox[2] = new HBox(10, menuBar, subVBox[1]);

		// 5. HBox of word & speech

		Text wordSelected = new Text();
		wordSelected.getStyleClass().add("bold");
		wordSelected.setId("wordSelected");

		Button speech_Button = new Button("", new ImageView("res/images/speech.png"));
		speech_Button.getStyleClass().add("speech");

		mainHBox[3] = new HBox(10, speech_Button, wordSelected);

		// 6. HBox of explain

		Text explainText = new Text();

		ScrollPane scp = new ScrollPane(explainText);
		scp.getStyleClass().add("explainArea");
		VBox.setVgrow(scp, Priority.ALWAYS);
		HBox.setHgrow(scp, Priority.ALWAYS);
		scp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scp.setContent(explainText);

		// Build application

		mainVBox[0] = new VBox(20, mainHBox[0], mainHBox[1], subVBox[0]);
		mainVBox[0].setId("leftBox");

		mainVBox[1] = new VBox(10, mainHBox[2], mainHBox[3], scp);
		mainVBox[1].setId("rightBox");

		HBox root = new HBox(50, mainVBox[0], mainVBox[1]);
		root.setPadding(new Insets(4, 10, 20, 4));

		VBox.setVgrow(subVBox[0], Priority.ALWAYS);
		HBox.setHgrow(mainVBox[1], Priority.ALWAYS);

		// Add listener

		// 1. wordTextField listener

		searchWord_TextField.textProperty().addListener(
		(obs, oldText, newText) -> {
			int low = 0, high = newText.length();

			while (low < high) {
				int middle = (low + high + 1) >> 1;

				Dictionary t = (new DictionaryManagement(
					wordList.getSelectedToggle() == allWord_Button ? this.dictionaryData : this.recentWord
				)).dictionarySearcher(newText.substring(0, middle));

				if (t.getSize() > 0) low = middle;
				else high = middle - 1;
			}

			Dictionary t = (new DictionaryManagement(
				wordList.getSelectedToggle() == allWord_Button ? this.dictionaryData : this.recentWord
			)).dictionarySearcher(newText.substring(0, low));
			wordEntries_ListView.getItems().clear();
			wordEntries_ListView.getItems().addAll(t.getAllWords());
		});

		// 2. wordEntries listener

		wordEntries_ListView.getSelectionModel().selectedItemProperty().addListener(
		(obs, oldValue, newValue) -> {
			if (newValue == null) return;

			Word w = this.dictionaryData.getWord((String)newValue);
			String t = w.getExplain().replace("\t", "\n");

			wordSelected.setText((String)newValue);
			explainText.setText(t);
			if ((new DictionaryManagement(this.recentWord)).dictionaryLookup(newValue) == null)
				recentWord.addWord(w);
		});

		// 3. wordList listener

		wordList.selectedToggleProperty().addListener(
		(obs, oldValue, newValue) -> {
			if (newValue == allWord_Button) {
				wordEntries_ListView.getItems().clear();
				wordEntries_ListView.getItems().addAll(this.dictionaryData.getAllWords());
			}
			else {
				wordEntries_ListView.getItems().clear();
				wordEntries_ListView.getItems().addAll(this.recentWord.getAllWords());
			}
		});

		// 4. dictionarySelection listener

		// 4.1. Text Dictionary listener

		dictTxt_Button.setOnAction((e) -> {
			HBox[] hb = new HBox[2];

			Label dictFile_Label = new Label();
			dictFile_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.dictionary.txt.txtfile"));
			TextField dictFile_TextField = new TextField(prop.getProperty("dictionary.dictlist.txt.source"));

			Button ok_Button = new Button("", new ImageView("res/images/ok.png"));
			ok_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.ok"));
			ok_Button.getStyleClass().add("w7button");
			ok_Button.setStyle("-fx-content-display:left;");

			Button cancel_Button = new Button("", new ImageView("res/images/cancel.png"));
			cancel_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.cancel"));
			cancel_Button.getStyleClass().add("w7button");
			cancel_Button.setStyle("-fx-content-display:left;");

			hb[0] = new HBox(20, dictFile_Label, dictFile_TextField);
			hb[0].setStyle("-fx-alignment:baseline-center;");
			HBox.setHgrow(dictFile_TextField, Priority.ALWAYS);

			hb[1] = new HBox(30, ok_Button, cancel_Button);
			hb[1].setStyle("-fx-alignment:baseline-center;");

			VBox parent = new VBox(20, hb[0], hb[1]);
			parent.setStyle("-fx-padding:10px;");

			Scene scn = new Scene(parent, 300, 100);
			scn.getStylesheets().add("res/css/style.css");

			Stage stg = new Stage();
			stg.titleProperty().bind(resourceFactory.getStringBinding("dictionary.dictionary.txt.title"));
			stg.setScene(scn);
			stg.initModality(Modality.WINDOW_MODAL);
			stg.initOwner(stage);
			stg.setResizable(false);
			stg.getIcons().add(new Image("res/images/load_text.png"));
			stg.show();

			// Add listener

			dictFile_TextField.setOnAction((e1) -> {
				ok_Button.fire();
			});

			ok_Button.setOnAction((e1) -> {
				String t = (String)dictFile_TextField.getText();
				this.dictionaryData = new Dictionary();
				this.recentWord = new Dictionary();
				boolean chk = (new DictionaryManagement(this.dictionaryData)).insertFromFile("res/dictionaries/" + t);
				stg.close();
				if (chk) {
					prop.setProperty("dictionary.dictlist.txt.source", t);
					reloadEntries(wordEntries_ListView, (wordList.getSelectedToggle() == allWord_Button ? this.dictionaryData : this.recentWord));
					dictTxt_Button.setGraphic(new ImageView("res/images/text_selected.png"));
					dictDb_Button.setGraphic(new ImageView("res/images/database.png"));
					System.out.println(dictionaryData.getSize());
				}
				else
					dictTxt_Button.setGraphic(new ImageView("res/images/text.png"));
			});

			cancel_Button.setOnAction((e1) -> {
				stg.close();
			});
		});

		// 4.2. MySQL Dictionary listener

		dictDb_Button.setOnAction((e) -> {
			Label dictHost_Label = new Label();
			dictHost_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.dictionary.mysql.host"));
			TextField dictHost_TextField = new TextField(prop.getProperty("dictionary.dictlist.db.host"));
			HBox.setHgrow(dictHost_TextField, Priority.ALWAYS);

			Label dictPort_Label = new Label();
			dictPort_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.dictionary.mysql.port"));
			TextField dictPort_TextField = new TextField(prop.getProperty("dictionary.dictlist.db.port"));
			HBox.setHgrow(dictPort_TextField, Priority.ALWAYS);

			Label dictUser_Label = new Label();
			dictUser_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.dictionary.mysql.user"));
			TextField dictUser_TextField = new TextField(prop.getProperty("dictionary.dictlist.db.username"));
			HBox.setHgrow(dictUser_TextField, Priority.ALWAYS);

			Label dictPass_Label = new Label();
			dictPass_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.dictionary.mysql.pass"));
			PasswordField dictPass_PasswordField = new PasswordField();
			dictPass_PasswordField.setText(prop.getProperty("dictionary.dictlist.db.password"));
			HBox.setHgrow(dictPass_PasswordField, Priority.ALWAYS);

			Label dictDbName_Label = new Label();
			dictDbName_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.dictionary.mysql.db"));
			TextField dictDbName_TextField = new TextField(prop.getProperty("dictionary.dictlist.db.database"));
			HBox.setHgrow(dictDbName_TextField, Priority.ALWAYS);

			Label dictTbl_Label = new Label();
			dictTbl_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.dictionary.mysql.tbl"));
			TextField dictTbl_TextField = new TextField(prop.getProperty("dictionary.dictlist.db.table"));
			HBox.setHgrow(dictTbl_TextField, Priority.ALWAYS);

			Button ok_Button = new Button("", new ImageView("res/images/ok.png"));
			ok_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.ok"));
			ok_Button.getStyleClass().add("w7button");
			ok_Button.setStyle("-fx-content-display:left;");

			Button cancel_Button = new Button("", new ImageView("res/images/cancel.png"));
			cancel_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.cancel"));
			cancel_Button.getStyleClass().add("w7button");
			cancel_Button.setStyle("-fx-content-display:left;");

			HBox hb = new HBox(30, ok_Button, cancel_Button);
			hb.setStyle("-fx-alignment:baseline-center;");

			GridPane parent = new GridPane();
			HBox.setHgrow(parent, Priority.ALWAYS);
			parent.setHgap(30); parent.setVgap(15);
			parent.setStyle("-fx-padding:10px;");

			parent.add(dictHost_Label, 0, 0);   parent.add(dictHost_TextField, 1, 0);
			parent.add(dictPort_Label, 0, 1);   parent.add(dictPort_TextField, 1, 1);
			parent.add(dictUser_Label, 0, 2);   parent.add(dictUser_TextField, 1, 2);
			parent.add(dictPass_Label, 0, 3);   parent.add(dictPass_PasswordField, 1, 3);
			parent.add(dictDbName_Label, 0, 4); parent.add(dictDbName_TextField, 1, 4);
			parent.add(dictTbl_Label, 0, 5);    parent.add(dictTbl_TextField, 1, 5);
			parent.add(hb, 0, 6, 2, 1);

			Scene scn = new Scene(parent, 260, 300);
			scn.getStylesheets().add("res/css/style.css");

			Stage stg = new Stage();
			stg.titleProperty().bind(resourceFactory.getStringBinding("dictionary.dictionary.mysql.title"));
			stg.setScene(scn);
			stg.initModality(Modality.WINDOW_MODAL);
			stg.initOwner(stage);
			stg.setResizable(false);
			stg.getIcons().add(new Image("res/images/load_database.png"));
			stg.show();

			// Add listener

			dictHost_TextField.setOnAction((e1) -> {
				ok_Button.fire();
			});

			dictPort_TextField.setOnAction((e1) -> {
				ok_Button.fire();
			});

			dictUser_TextField.setOnAction((e1) -> {
				ok_Button.fire();
			});

			dictPass_PasswordField.setOnAction((e1) -> {
				ok_Button.fire();
			});

			dictDbName_TextField.setOnAction((e1) -> {
				ok_Button.fire();
			});

			dictTbl_TextField.setOnAction((e1) -> {
				ok_Button.fire();
			});

			ok_Button.setOnAction((e1) -> {
				String host = (String)dictHost_TextField.getText();
				String port = (String)dictPort_TextField.getText();
				String user = (String)dictUser_TextField.getText();
				String pass = (String)dictPass_PasswordField.getText();
				String dbname = (String)dictDbName_TextField.getText();
				String tbl = (String)dictTbl_TextField.getText();
				this.dictionaryData = new Dictionary();
				this.recentWord = new Dictionary();
				boolean chk = (new DictionaryManagement(this.dictionaryData)).insertFromDatabase(host, port, user, pass, dbname, tbl);
				stg.close();
				if (chk) {
					prop.setProperty("dictionary.dictlist.db.host", host);
					prop.setProperty("dictionary.dictlist.db.port", port);
					prop.setProperty("dictionary.dictlist.db.username", user);
					prop.setProperty("dictionary.dictlist.db.password", pass);
					prop.setProperty("dictionary.dictlist.db.database", dbname);
					prop.setProperty("dictionary.dictlist.db.table", tbl);
					reloadEntries(wordEntries_ListView, (wordList.getSelectedToggle() == allWord_Button ? this.dictionaryData : this.recentWord));
					dictDb_Button.setGraphic(new ImageView("res/images/database_selected.png"));
					dictTxt_Button.setGraphic(new ImageView("res/images/text.png"));
				}
				else
					dictDb_Button.setGraphic(new ImageView("res/images/database.png"));
			});

			cancel_Button.setOnAction((e1) -> {
				stg.close();
			});
		});

		// 5. Dict. Mgmt listener

		dictMgmt_Button.setOnAction((e) -> {
			Button addWord_Button = new Button("", new ImageView("res/images/add.png"));
			addWord_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.add"));
			addWord_Button.setStyle("-fx-content-display:left;");

			Button editWord_Button = new Button("", new ImageView("res/images/edit.png"));
			editWord_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.edit"));
			editWord_Button.setStyle("-fx-content-display:left;");

			Button removeWord_Button = new Button("", new ImageView("res/images/remove.png"));
			removeWord_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.remove"));
			removeWord_Button.setStyle("-fx-content-display:left;");

			Button export_Button = new Button("", new ImageView("res/images/export.png"));
			export_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.export"));
			export_Button.setStyle("-fx-content-display:left;");

			VBox parent = new VBox(20, addWord_Button, editWord_Button, removeWord_Button, export_Button);
			parent.setPadding(new Insets(10));
			parent.setStyle("-fx-alignment:top-center;");

			Scene scn = new Scene(parent, 200, 220);

			addWord_Button.setMinWidth(170);
			editWord_Button.setMinWidth(170);
			removeWord_Button.setMinWidth(170);
			export_Button.setMinWidth(170);

			Stage stg = new Stage();
			stg.titleProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt"));
			stg.setScene(scn);
			stg.initModality(Modality.WINDOW_MODAL);
			stg.initOwner(stage);
			stg.setResizable(false);
			stg.getIcons().add(new Image("res/images/dictmgmt.png"));
			stg.show();

			// Add listener

			addWord_Button.setOnAction((e1) -> {
				Label wordTarget_Label = new Label();
				wordTarget_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.word"));
				TextField wordTarget_TextField = new TextField();
				GridPane.setHgrow(wordTarget_TextField, Priority.ALWAYS);

				Label wordExplain_Label = new Label();
				wordExplain_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.meaning"));
				TextArea wordExplain_TextArea = new TextArea();
				GridPane.setHgrow(wordExplain_TextArea, Priority.ALWAYS);
				GridPane.setVgrow(wordExplain_TextArea, Priority.ALWAYS);

				Button ok_Button = new Button("", new ImageView("res/images/ok.png"));
				ok_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.ok"));
				ok_Button.getStyleClass().add("w7button");
				ok_Button.setStyle("-fx-content-display:left;");

				Button cancel_Button = new Button("", new ImageView("res/images/cancel.png"));
				cancel_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.cancel"));
				cancel_Button.getStyleClass().add("w7button");
				cancel_Button.setStyle("-fx-content-display:left;");

				HBox hb = new HBox(30, ok_Button, cancel_Button);
				hb.setStyle("-fx-alignment:baseline-center;");

				GridPane gp = new GridPane();
				HBox.setHgrow(parent, Priority.ALWAYS);
				gp.setHgap(30); gp.setVgap(15);
				gp.setStyle("-fx-padding:10px;");

				Scene scn1 = new Scene(gp, 400, 350);
				scn1.getStylesheets().add("res/css/style.css");

				gp.add(wordTarget_Label, 0, 0);
				gp.add(wordTarget_TextField, 1, 0);
				gp.add(wordExplain_Label, 0, 1, 2, 1);
				gp.add(wordExplain_TextArea, 0, 2, 2, 1);
				gp.add(hb, 0, 3, 2, 1);

				Stage stg1 = new Stage();
				stg1.titleProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.add"));
				stg1.setScene(scn1);
				stg1.initModality(Modality.WINDOW_MODAL);
				stg1.initOwner(stg);
				stg1.setResizable(false);
				stg1.getIcons().add(new Image("res/images/add.png"));
				stg1.show();

				ok_Button.setOnAction((e2) -> {
					String tg = (String)wordTarget_TextField.getText();
					String ex = (String)wordExplain_TextArea.getText();
					boolean chk = (new DictionaryManagement(this.dictionaryData)).dictionaryAddWord(tg, ex);
					stg1.close();
					if (chk) reloadEntries(wordEntries_ListView, (wordList.getSelectedToggle() == allWord_Button ? this.dictionaryData : this.recentWord));
				});

				cancel_Button.setOnAction((e2) -> {
					stg1.close();
				});
			});

			editWord_Button.setOnAction((e1) -> {
				Label wordTarget_Label = new Label();
				wordTarget_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.word"));
				TextField wordTarget_TextField = new TextField();
				GridPane.setHgrow(wordTarget_TextField, Priority.ALWAYS);

				Label wordExplain_Label = new Label();
				wordExplain_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.newmeaning"));
				TextArea wordExplain_TextArea = new TextArea();
				GridPane.setHgrow(wordExplain_TextArea, Priority.ALWAYS);
				GridPane.setVgrow(wordExplain_TextArea, Priority.ALWAYS);

				Button ok_Button = new Button("", new ImageView("res/images/ok.png"));
				ok_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.ok"));
				ok_Button.getStyleClass().add("w7button");
				ok_Button.setStyle("-fx-content-display:left;");

				Button cancel_Button = new Button("", new ImageView("res/images/cancel.png"));
				cancel_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.cancel"));
				cancel_Button.getStyleClass().add("w7button");
				cancel_Button.setStyle("-fx-content-display:left;");

				HBox hb = new HBox(30, ok_Button, cancel_Button);
				hb.setStyle("-fx-alignment:baseline-center;");

				GridPane gp = new GridPane();
				HBox.setHgrow(parent, Priority.ALWAYS);
				gp.setHgap(30); gp.setVgap(15);
				gp.setStyle("-fx-padding:10px;");

				gp.add(wordTarget_Label, 0, 0);
				gp.add(wordTarget_TextField, 1, 0);
				gp.add(wordExplain_Label, 0, 1, 2, 1);
				gp.add(wordExplain_TextArea, 0, 2, 2, 1);
				gp.add(hb, 0, 3, 2, 1);

				Scene scn1 = new Scene(gp, 400, 350);
				scn1.getStylesheets().add("res/css/style.css");

				Stage stg1 = new Stage();
				stg1.titleProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.edit"));
				stg1.setScene(scn1);
				stg1.initModality(Modality.WINDOW_MODAL);
				stg1.initOwner(stg);
				stg1.setResizable(false);
				stg1.getIcons().add(new Image("res/images/edit.png"));
				stg1.show();

				ok_Button.setOnAction((e2) -> {
					String tg = (String)wordTarget_TextField.getText();
					String ex = (String)wordExplain_TextArea.getText();
					boolean chk = (new DictionaryManagement(this.dictionaryData)).dictionaryEditWord(tg, ex);
					stg1.close();
					if (chk) reloadEntries(wordEntries_ListView, (wordList.getSelectedToggle() == allWord_Button ? this.dictionaryData : this.recentWord));
				});

				cancel_Button.setOnAction((e2) -> {
					stg1.close();
				});
			});

			removeWord_Button.setOnAction((e1) -> {
				Label wordTarget_Label = new Label();
				wordTarget_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.word"));
				TextField wordTarget_TextField = new TextField();
				GridPane.setHgrow(wordTarget_TextField, Priority.ALWAYS);

				Button ok_Button = new Button("", new ImageView("res/images/ok.png"));
				ok_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.ok"));
				ok_Button.getStyleClass().add("w7button");
				ok_Button.setStyle("-fx-content-display:left;");

				Button cancel_Button = new Button("", new ImageView("res/images/cancel.png"));
				cancel_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.cancel"));
				cancel_Button.getStyleClass().add("w7button");
				cancel_Button.setStyle("-fx-content-display:left;");

				HBox hb = new HBox(30, ok_Button, cancel_Button);
				hb.setStyle("-fx-alignment:baseline-center;");

				GridPane gp = new GridPane();
				HBox.setHgrow(parent, Priority.ALWAYS);
				gp.setHgap(30); gp.setVgap(15);
				gp.setStyle("-fx-padding:10px;");

				gp.add(wordTarget_Label, 0, 0);
				gp.add(wordTarget_TextField, 1, 0);
				gp.add(hb, 0, 1, 2, 1);

				Scene scn1 = new Scene(gp, 300, 110);
				scn1.getStylesheets().add("res/css/style.css");

				Stage stg1 = new Stage();
				stg1.titleProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.remove"));
				stg1.setScene(scn1);
				stg1.initModality(Modality.WINDOW_MODAL);
				stg1.initOwner(stg);
				stg1.setResizable(false);
				stg1.getIcons().add(new Image("res/images/remove.png"));
				stg1.show();

				wordTarget_TextField.setOnAction((e2) -> {
					ok_Button.fire();
				});

				ok_Button.setOnAction((e2) -> {
					String tg = (String)wordTarget_TextField.getText();
					boolean chk = (new DictionaryManagement(this.dictionaryData)).dictionaryRemoveWord(tg);
					stg1.close();
					if (chk) reloadEntries(wordEntries_ListView, (wordList.getSelectedToggle() == allWord_Button ? this.dictionaryData : this.recentWord));
				});

				cancel_Button.setOnAction((e2) -> {
					stg1.close();
				});
			});

			export_Button.setOnAction((e1) -> {
				Label file_Label = new Label();
				file_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.filename"));
				TextField file_TextField = new TextField();
				GridPane.setHgrow(file_TextField, Priority.ALWAYS);

				Button ok_Button = new Button("", new ImageView("res/images/ok.png"));
				ok_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.ok"));
				ok_Button.getStyleClass().add("w7button");
				ok_Button.setStyle("-fx-content-display:left;");

				Button cancel_Button = new Button("", new ImageView("res/images/cancel.png"));
				cancel_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.cancel"));
				cancel_Button.getStyleClass().add("w7button");
				cancel_Button.setStyle("-fx-content-display:left;");

				HBox hb = new HBox(30, ok_Button, cancel_Button);
				hb.setStyle("-fx-alignment:baseline-center;");

				GridPane gp = new GridPane();
				HBox.setHgrow(parent, Priority.ALWAYS);
				gp.setHgap(30); gp.setVgap(15);
				gp.setStyle("-fx-padding:10px;");

				Scene scn1 = new Scene(gp, 300, 110);
				scn1.getStylesheets().add("res/css/style.css");

				gp.add(file_Label, 0, 0);
				gp.add(file_TextField, 1, 0);
				gp.add(hb, 0, 1, 2, 1);

				Stage stg1 = new Stage();
				stg1.titleProperty().bind(resourceFactory.getStringBinding("dictionary.tool.mgmt.export"));
				stg1.setScene(scn1);
				stg1.initModality(Modality.WINDOW_MODAL);
				stg1.initOwner(stg);
				stg1.setResizable(false);
				stg1.getIcons().add(new Image("res/images/export.png"));
				stg1.show();

				file_TextField.setOnAction((e2) -> {
					ok_Button.fire();
				});

				ok_Button.setOnAction((e2) -> {
					String f = (String)file_TextField.getText();
					(new DictionaryManagement(this.dictionaryData)).dictionaryExportToFile(f);
					stg1.close();
				});

				cancel_Button.setOnAction((e2) -> {
					stg1.close();
				});
			});
		});

		// 6. listening_Button listener

		listening_Button.setOnAction((e) -> {
			Label text_Label = new Label();
			text_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.listening.text"));
			TextArea text_TextArea = new TextArea();

			Button speak_Button = new Button("", new ImageView("res/images/speaker.png"));
			speak_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.listening.listen"));
			speak_Button.getStyleClass().add("w7button");
			speak_Button.setStyle("-fx-content-display:left;");

			VBox parent = new VBox(10, text_Label, text_TextArea, speak_Button);
			parent.setPadding(new Insets(10));
			VBox.setVgrow(text_TextArea, Priority.ALWAYS);

			Scene scn = new Scene(parent, 450, 450);
			scn.getStylesheets().add("res/css/style.css");

			Stage stg = new Stage();
			stg.titleProperty().bind(resourceFactory.getStringBinding("dictionary.tool.listening"));
			stg.setScene(scn);
			stg.initModality(Modality.WINDOW_MODAL);
			stg.initOwner(stage);
			stg.setResizable(false);
			stg.getIcons().add(new Image("res/images/listening.png"));
			stg.show();

			speak_Button.setOnAction((e1) -> {
				VoiceManager vm = VoiceManager.getInstance();
				Voice voice = vm.getVoice("mbrola_" + prop.getProperty("dictionary.voice"));
				voice.allocate();
				voice.speak(text_TextArea.getText());
				voice.deallocate();
			});
		});

		// 7. googleApi_Button listener

		googleApi_Button.setOnAction((e) -> {
			Label text_Label = new Label();
			text_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.google.text"));
			TextArea text_TextArea = new TextArea();

			Label result_Label = new Label();
			result_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.google.translation"));
			Text result_Text = new Text();
			ScrollPane result_ScrollPane = new ScrollPane(result_Text);

			Button translate_Button = new Button("", new ImageView("res/images/translate.png"));
			translate_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.google.translate"));
			translate_Button.getStyleClass().add("w7button");
			translate_Button.setStyle("-fx-content-display:left;");

			VBox parent = new VBox(10, text_Label, text_TextArea, result_Label, result_ScrollPane, translate_Button);
			parent.setPadding(new Insets(10));
			text_TextArea.setMinHeight(180);
			VBox.setVgrow(result_ScrollPane, Priority.ALWAYS);

			Scene scn = new Scene(parent, 450, 450);
			scn.getStylesheets().add("res/css/style.css");

			Stage stg = new Stage();
			stg.titleProperty().bind(resourceFactory.getStringBinding("dictionary.tool.google"));
			stg.setScene(scn);
			stg.initModality(Modality.WINDOW_MODAL);
			stg.initOwner(stage);
			stg.setResizable(false);
			stg.getIcons().add(new Image("res/images/ggtrans.png"));
			stg.show();

			translate_Button.setOnAction((e1) -> {
				String k = text_TextArea.getText();
				String ans = null;
				GoogleAPI translate = new GoogleAPI();
				try {
					ans = translate.callTranslate("en", "vi", k);
				}
				catch (Exception ex) {
					System.out.println("Error!");
					ex.printStackTrace();
				}
				result_Text.setText(ans);
			});
		});

		// 8. options_Button listener

		options_Button.setOnAction((e) -> {
			Label voice_Label = new Label();
			voice_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.option.voice"));
			voice_Label.getStyleClass().add("bold");

			RadioButton male_Button = new RadioButton();
			male_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.option.voice.male"));
			RadioButton female_Button = new RadioButton();
			female_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.option.voice.female"));

			(prop.getProperty("dictionary.voice").equals("us1") ? female_Button : male_Button).setSelected(true);

			ToggleGroup voice_Toggle = new ToggleGroup();
			voice_Toggle.getToggles().addAll(male_Button, female_Button);

			Label lang_Label = new Label();
			lang_Label.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.option.defaultLocale"));
			lang_Label.getStyleClass().add("bold");

			RadioButton vi_Button = new RadioButton();
			vi_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.option.defaultLocale.vi"));
			RadioButton en_Button = new RadioButton();
			en_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.tool.option.defaultLocale.en"));

			(prop.getProperty("dictionary.defaultLocale").equals("vi") ? vi_Button : en_Button).setSelected(true);

			ToggleGroup lang_Toggle = new ToggleGroup();
			lang_Toggle.getToggles().addAll(vi_Button, en_Button);

			Button ok_Button = new Button("", new ImageView("res/images/ok.png"));
			ok_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.ok"));
			ok_Button.getStyleClass().add("w7button");
			ok_Button.setStyle("-fx-content-display:left;");

			Button cancel_Button = new Button("", new ImageView("res/images/cancel.png"));
			cancel_Button.textProperty().bind(resourceFactory.getStringBinding("dictionary.cancel"));
			cancel_Button.getStyleClass().add("w7button");
			cancel_Button.setStyle("-fx-content-display:left;");

			GridPane parent = new GridPane();
			parent.setHgap(50); parent.setVgap(15);

			HBox[] hb = new HBox[3];
			hb[0] = new HBox(25, male_Button, female_Button);
			hb[1] = new HBox(25, vi_Button, en_Button);
			hb[2] = new HBox(10, ok_Button, cancel_Button);
			hb[2].setStyle("-fx-alignment:baseline-center;");

			parent.add(voice_Label, 0, 0);
			parent.add(hb[0], 1, 0);
			parent.add(lang_Label, 0, 1);
			parent.add(hb[1], 1, 1);
			parent.add(hb[2], 0, 2, 2, 1);
			parent.setPadding(new Insets(10));

			Scene scn = new Scene(parent, 370, 120);
			scn.getStylesheets().add("res/css/style.css");

			Stage stg = new Stage();
			stg.titleProperty().bind(resourceFactory.getStringBinding("dictionary.tool.option"));
			stg.setScene(scn);
			stg.initModality(Modality.WINDOW_MODAL);
			stg.initOwner(stage);
			stg.setResizable(false);
			stg.getIcons().add(new Image("res/images/options.png"));
			stg.show();

			ok_Button.setOnAction((e1) -> {
				prop.setProperty("dictionary.voice", (voice_Toggle.getSelectedToggle() == male_Button ? "us2" : "us1"));
				prop.setProperty("dictionary.defaultLocale", (lang_Toggle.getSelectedToggle() == vi_Button ? "vi" : "en"));
				if (!prop.getProperty("dictionary.defaultLocale").equals(langSwitch.getProperties().get("locale")))
					langSwitch.fire();
				stg.close();
			});

			cancel_Button.setOnAction((e1) -> {
				stg.close();
			});
		});

		// 9. langSwitch listener

		langSwitch.setOnAction((e) -> {
			String lc = (String)(langSwitch.getProperties().get("locale"));
			switch (lc) {
				case "vi":
					resourceFactory.setResources(ResourceBundle.getBundle(BUNDLE_BASENAME, ENGLISH_LOCALE));
					langSwitch.getProperties().put("locale", "en");
					break;
				case "en":
					resourceFactory.setResources(ResourceBundle.getBundle(BUNDLE_BASENAME, VIETNAMESE_LOCALE));
					langSwitch.getProperties().put("locale", "vi");
					break;
			}
		});

		// 10. facebook_Button listener

		facebook_Button.setOnAction((e) -> {
			try {
				Desktop.getDesktop().browse(new URL("https://www.facebook.com/the.flash.912").toURI());
			}
			catch (Exception ex){}
		});

		// Set property for using MBrola
		
		System.setProperty("mbrola.base", "res/mbrola");
		System.setProperty("freetts.voices", "de.dfki.lt.freetts.en.us.MbrolaVoiceDirectory");

		// 11. speech_Button listener

		speech_Button.setOnAction((e) -> {
			VoiceManager vm = VoiceManager.getInstance();
			Voice voice = vm.getVoice("mbrola_" + prop.getProperty("dictionary.voice"));
			voice.allocate();
			voice.speak(wordSelected.getText());
			voice.deallocate();
		});

		// 12. home_Button listener

		home_Button.setOnAction((e) -> {
			try {
				Desktop.getDesktop().browse(new URL("http://www.lacviet.vn/san-pham/tudienlacviet").toURI());
			}
			catch (Exception ex){}
		});

		// 13. about_Button listener

		about_Button.setOnAction((e) -> {
			StringProperty aboutContent = new SimpleStringProperty();
			aboutContent.bind(resourceFactory.getStringBinding("dictionary.help.about.content"));
			ScrollPane parent = new ScrollPane(new Text(aboutContent.get()));
			parent.setPadding(new Insets(10));

			Stage stg = new Stage();
			stg.titleProperty().bind(resourceFactory.getStringBinding("dictionary.help.about"));
			stg.setScene(new Scene(parent, 450, 450));
			stg.initModality(Modality.WINDOW_MODAL);
			stg.initOwner(stage);
			stg.setResizable(false);
			stg.getIcons().add(new Image("res/images/about.png"));
			stg.show();
		});

		// 14. help_Button listener

		help_Button.setOnAction((e) -> {
			StringProperty helpContent = new SimpleStringProperty();
			helpContent.bind(resourceFactory.getStringBinding("dictionary.help.help.content"));
			ScrollPane parent = new ScrollPane(new Text(helpContent.get()));
			parent.setPadding(new Insets(10));

			Stage stg = new Stage();
			stg.titleProperty().bind(resourceFactory.getStringBinding("dictionary.help.help"));
			stg.setScene(new Scene(parent, 450, 450));
			stg.initModality(Modality.WINDOW_MODAL);
			stg.initOwner(stage);
			stg.setResizable(false);
			stg.getIcons().add(new Image("res/images/help.png"));
			stg.show();
		});

		// 15. Stage on close - save properties

		stage.setOnHiding((e) -> {
			OutputStream out = null;

			try {
				out = new FileOutputStream(CONFIG_PROPERTIES);
				prop.store(out, null);
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
			finally {
				if (out != null)
					try {
						out.close();
					}
					catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		});

		// Add root to scene

		scene.setRoot(root);

		// Add Scene to Stage

		stage.setScene(scene);

		// Display the Stage

		stage.show();
	}
}