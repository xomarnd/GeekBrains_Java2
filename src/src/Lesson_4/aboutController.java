package Lesson_4;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class aboutController {

    @FXML
    private Button okButton;

    @FXML
    public void handleCloseButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}


