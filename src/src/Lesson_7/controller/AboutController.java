package Lesson_7.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {

    @FXML
    private Button okButton;

    @FXML
    public void handleCloseButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}


