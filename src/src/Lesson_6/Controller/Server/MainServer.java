package Lesson_6.Controller.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MainServer {
    private static Vector<ClientHandler> clients  = new Vector<>();

    public MainServer(int PORT){
        ServerSocket server = null;
        Socket socket  = null;
        boolean serverRun = false;
        try {
            server = new ServerSocket (PORT);
            serverRun = true;
            System.out.println ("Server started");

            while (true){
                socket = server.accept();
                System.out.println ("Client connect");
                clients.add(new ClientHandler (this, socket));
            }

        } catch (IOException e) {
            e.printStackTrace ();
        }finally {
            try {
                if(serverRun){
                    socket.close ();
                }
            }catch (IOException e){
                e.printStackTrace ();
            }
        }
    }


    public void broadcastMsg(String msg){
        for (ClientHandler o: clients){
            o.sendMsg(msg);
        }
    }
    public static void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}
