package Lesson_4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class Controller {

    @FXML
    private MenuItem connectServer;

    @FXML
    private MenuItem disconnectServer;

    @FXML
    private MenuItem closeWindow;

    @FXML
    private MenuItem clearChar;

    @FXML
    private MenuItem helpMenu;

    @FXML
    private MenuItem about;

    @FXML
    TextField messegeEntryField;

    @FXML
    private Button sendMessageButton;

    @FXML
    TextArea allChatMessage;

    @FXML
    void initialize() {
        closeWindow.setOnAction(event -> {
            System.exit(0);
            System.out.println("закрыть программу");
        });

        clearChar.setOnAction(event -> {
            allChatMessage.setText("");
            System.out.println("отчистить тело чата");
        });

        helpMenu.setOnAction(event -> {
            System.out.println("вызвать помощь");
        });

        about.setOnAction(event -> {
            Stage stage = new Stage();
            try {
                FXMLDocumentController(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    private void FXMLDocumentController(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("about.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    public void sendMassage() {
        allChatMessage.appendText("Me: " + messegeEntryField.getText() + "\n");
        System.out.println("сообщение отправленно");
        messegeEntryField.clear();
        messegeEntryField.requestFocus();
    }

}
