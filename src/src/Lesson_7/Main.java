package Lesson_7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/sample.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle ("MyChat");
//        primaryStage.setScene (new Scene (root, 600, 400));
        primaryStage.show ();

        File file = new File("res");
        file.mkdir ();
    }


    public static void main(String[] args) {
        launch();
    }
}
