package Lesson_6.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class serverMenuController {

        @FXML
        private Button createServerButton;

        @FXML
        private TextField idServerHost;


        @FXML
        private TextField idServerPort;

        @FXML
        private TextField idServerNickname;

        @FXML
        void createServer(ActionEvent event) {

        }
        public void initialize() throws UnknownHostException {
            idServerHost.appendText(Inet4Address.getLocalHost().getHostAddress());
        }
}
