package Lesson_7.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private MainServer serv;
    private String clientName;
    private boolean authOk = false;
    private String nick = null;
    public static final int TIMEOUT = 20 * 1000;
    public static int TRY = 5;



    public ClientHandler(MainServer serv, Socket socket) {
        try {
            this.serv = serv;
            this.socket = socket;

            this.in = new DataInputStream (socket.getInputStream ());
            this.out = new DataOutputStream (socket.getOutputStream ());

            new Thread (() -> {
                try {
                    while (!authOk) {
                        authentication ();
                    }
                    readMessages ();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
    private void readMessages() throws IOException {
        while (true) {
            String clientMessage = in.readUTF();
            System.out.printf("Message '%s' from client %s%n", clientMessage, clientName);
            if (clientMessage.equals("/end")) {
                break;
            }
            if (clientMessage.startsWith("/w")) {
                String[] wispMsg = clientMessage.split ("\\s+");
                String to = wispMsg[1];
                String msg = "] ";
                for(int i = 2; i < wispMsg.length; i++){
                    msg = msg + " " + wispMsg[i];
                }
                MainServer.wisperMSG(this, to, msg);
            } else {
                MainServer.broadcastMsg (clientName + ": " + clientMessage);
            }
        }
    }

    public void sendMsg(String msg){
        try {
            out.writeUTF (msg);
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
    public void closeConnection() {
        MainServer.removeClient(this);
        MainServer.broadcastMsg("***" + clientName + " покинул беседу.");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Не получилось закрыть сокет!");
            e.printStackTrace();
        }
    }
    public class PeriodicChecker extends Thread{
        @Override
        public void run(){
            for (int i = (int) TIMEOUT / 1000; i >= 0; i--) {
                if (i % 5 == 0 & !authOk & nick == null) {
                    sendMsg ("***Осталось: " + ((i > 4) ? i + " секунд" : (i > 1) ? i + " секунды" : (i == 1) ? i + " секунда" : "0, истекло время ожидания аутентификации!"));
                }
                try {
                    Thread.sleep (1000);
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
            }
        }
    }


    private void authentication() throws IOException {
        String clientMessage = in.readUTF();
        sendMsg ("***Необходима аутентификация, введие логин и пароль \"/auth login pass\"");
        if (nick == null) {
            Thread t = new PeriodicChecker ();
            t.start ();
        }
        // https://docs.oracle.com/javase/1.5.0/docs/guide/lang/Countdown.java
        Timer timeoutTimer = new Timer(true);
        timeoutTimer.schedule(new TimerTask () {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        if (clientName == null) {
                            Thread.sleep(100);
                            socket.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, TIMEOUT);
        if (clientMessage.startsWith ("/auth")) {
            String[] loginAndPasswords = clientMessage.split ("\\s+");
            String login = loginAndPasswords[1];
            String password = loginAndPasswords[2];
            sendMsg ("***Попытка аутентификации: " + login);

            nick = MainServer.getAuthService ().getNickByLoginPass (login, password);
            if (nick == null) {
                TRY -= 1;
                if (TRY > 0 ) sendMsg ("***Неверные логин/пароль, осталось " + TRY + " попыток.");
                if (TRY <= 0){
                    sendMsg("***Истекло колличество попыток!");
                    socket.close();
                }
                return;
            }

            if (MainServer.isNickBusy (nick)) {
                sendMsg ("***Учетная запись уже используется");
                return;
            }
            authOk = true;
            sendMsg ("***Аутентификация прошла успешно для - " + nick);
            clientName = nick;
            sendMsg ("/authok" + clientName);

            MainServer.broadcastMsg ("***" + clientName + " теперь онлайн!");
            MainServer.subscribe(this);
        }
    }

    public String getClientName() {
        return clientName;
    }

}
