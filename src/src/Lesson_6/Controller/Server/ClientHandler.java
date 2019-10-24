package Lesson_6.Controller.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private MainServer serv;

    public ClientHandler(MainServer serv, Socket socket) {
        try {
            this.serv = serv;
            this.socket = socket;
            this.in = new DataInputStream (socket.getInputStream ());
            this.out = new DataOutputStream (socket.getOutputStream ());

            new Thread (() -> {
                try {
                    while (true) {
                        String str = in.readUTF ();
                        if (str.contains("/end")){
                            serv.broadcastMsg (str.replaceAll ("/end", "") + " покинул чат.");
                            this.close();
                            break;
                        }
                        serv.broadcastMsg (str);
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }finally {
                    try {
                        out.close ();
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                    try {
                        in.close ();
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                    try {
                        socket.close ();
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                    System.out.println ("Client disconnect");
                }
            }).start ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public void sendMsg(String msg){
        try {
            out.writeUTF (msg);
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
    public void close() {
        MainServer.removeClient(this);
    }
}
