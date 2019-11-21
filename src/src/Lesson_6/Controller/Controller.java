package Lesson_6.Controller;

import Lesson_6.Controller.Server.MainServer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private MenuItem connectServer;

    @FXML
    private MenuItem disconnectServer;

    @FXML
    private MenuItem newServer;

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

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    String IP_ADDRESS = "localhost";
    int PORT = 8189;
    String Nickname = "Nickname";
    boolean connected = false;
    int serverPort = 8189;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        allChatMessage.appendText("***Для подключения к серверу, используйте команду \"/server ip:port\". \n" +
                "***Для создания сервера, используйте команду \"/newserver port\".\n" +
                "***Чтобы изменить Имя, используйте команду \"/nickname Имя\".\n\n");

        closeWindow.setOnAction(event -> {
            try {
                out.writeUTF (Nickname + "/end");
            } catch (IOException e) {
                e.printStackTrace ();
            }
            messegeEntryField.clear ();
            messegeEntryField.requestFocus ();
            connected = false;
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
            System.out.println("вызвать \"о программе\"");
            Stage stage = new Stage();
            try {
                startWindowUNDECORATED(stage, "../fxml/about.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        connectServer.setOnAction(event -> {
            System.out.println("меню конекта к серверу");
            Stage stage = new Stage();
            try {
                startWindow(stage, "../fxml/connectMenu.fxml", "Connect to server");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        newServer.setOnAction(event -> {
            System.out.println("меню создания сервера");
            Stage stage = new Stage();
            try {
                startWindow(stage, "../fxml/serverMenu.fxml", "Create server");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    private void startWindow(Stage stage, String fxml, String nameWindow) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle(nameWindow);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    private void startWindowUNDECORATED(Stage stage, String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }


    public void sendMassage() throws IOException {
        String inputText = messegeEntryField.getText();
        if (inputText.trim().equals("")) {
            System.out.println("пустая строка");

        }else{
            if (inputText.startsWith("/nickname ")) {
                String changeName = inputText.replaceAll ("/nickname ", "");
                System.out.println ("Cмена никнейма на " + changeName + "\"");

                if(connected) {
                    out.writeUTF (Nickname + " сменил имя на \"" + changeName + "\"");
                }else {
                    allChatMessage.appendText("Вы сменили имя на \"" + changeName + "\"\n");
                }
                Nickname = inputText.replaceAll ("/nickname ", "");
                messegeEntryField.clear ();
                messegeEntryField.requestFocus ();


            }else if(inputText.startsWith("/server ")) {
                String adress = inputText.replaceAll ("/server ", "");

                System.out.println ("Попытка подключения к серверу " + adress);
                if (adress.indexOf(':') > -1) {
                    String[] arr = adress.split(":");
                    IP_ADDRESS = arr[0];
                    try {
                        PORT = Integer.parseInt(arr[1]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else {
                    IP_ADDRESS = adress;
                }
                if(connected) out.writeUTF (Nickname + "/end");
                messegeEntryField.clear ();
                messegeEntryField.requestFocus ();
                connected = false;
                Thread threadConnectToServer = new Thread(() ->this.connectToServer (IP_ADDRESS, PORT));
                threadConnectToServer.start();
                System.out.println ("Попытка подключения к серверу " + IP_ADDRESS);


            }else if(inputText.startsWith("/connect")) {
                out.writeUTF (Nickname + "/end");
                messegeEntryField.clear ();
                messegeEntryField.requestFocus ();
                connected = false;
                connectToServer (IP_ADDRESS, PORT);
                System.out.println ("Попытка подключения к серверу " + IP_ADDRESS);


            }else if(inputText.startsWith("/newserver ")) {
                serverPort = Integer.parseInt(inputText.replaceAll ("/newserver ", ""));
                messegeEntryField.clear ();
                messegeEntryField.requestFocus ();
                allChatMessage.appendText("Сервер доступен по адресу " + Inet4Address.getLocalHost().getHostAddress() + ":" + serverPort + ".\n");
                Thread threadServer = new Thread(() ->this.createServer (serverPort));
                threadServer.start();
                connectToServer("localhost", serverPort);


            }else {
                try {
                    if(connected) {
                        out.writeUTF (Nickname + ": " + messegeEntryField.getText ());
                        System.out.println ("сообщение отправленно");
                        messegeEntryField.clear ();
                        messegeEntryField.requestFocus ();
                    }else{
                        allChatMessage.appendText("Подключитесь к серверу \"/server IP:PORT\"\n");
                        messegeEntryField.clear ();
                        messegeEntryField.requestFocus ();
                    }
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
    }

    private void createServer(int serverPort) {
        new MainServer (serverPort);
    }


    public void connectToServer(String IP_ADDRESS, int PORT) {
        int count = 0;
        while (!connected) {
            System.out.println("Переподключение сокета.");
            allChatMessage.appendText ("Попытка подключения к серверу\n");
            try {
                socket.close();
            } catch (Exception e) {
            }

            try {
                socket = new Socket(IP_ADDRESS, PORT);
                in = new DataInputStream (socket.getInputStream ());
                out = new DataOutputStream (socket.getOutputStream ());
                connected = true;

                new Thread (new Runnable () {
                    @Override
                    public void run() {
                        try {
                            allChatMessage.appendText("Успешное подключение!\n");
                            allChatMessage.appendText("*****Добро пожадловать на сервер " + IP_ADDRESS + ":" + PORT + "!*****\n");
                            while (true){

                                String str = in.readUTF ();
                                allChatMessage.appendText(str + "\n");
                            }
                        }catch (IOException e){
                            if(connected){
                                e.printStackTrace ();
                            }
                        }finally {
                            try {
                                socket.close ();
                            } catch (IOException e) {
                                e.printStackTrace ();
                            }
                        }
                    }
                }).start ();
            } catch (IOException ex) {
                connected = false;
                allChatMessage.appendText ("Невозможно подключиться к сокету.\n");
                System.out.println("Невозможно подключиться к сокету " + IP_ADDRESS);
                count++;
            }
            if (count >= 5){
                allChatMessage.appendText ("Превышенно колличество попыток " + count +", проверьте адрес. \n");
                break;
            }

            if (!connected) { // 1 сек задержку между соединениями
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
}
