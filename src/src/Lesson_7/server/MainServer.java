package Lesson_7.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import Lesson_7.auth.AuthService;
import Lesson_7.auth.BaseAuthService;

public class MainServer {

    private static AuthService authService = new BaseAuthService ();

    private static Vector<ClientHandler> clients  = new Vector<>();


    public MainServer(int PORT){
        Socket socket  = null;

        try (ServerSocket server = new ServerSocket(PORT)){
            authService.start();
            System.out.println ("Сервер запущен");
            while (true){
                socket = server.accept();
                System.out.println ("Клиент подключился");
                new ClientHandler (this, socket);
            }

        } catch (IOException e) {
            System.err.println("Ошибка в работе сервера. Причина: " + e.getMessage());
            e.printStackTrace ();
        }finally {
            authService.stop();
        }
    }


    public static void broadcastMsg(String msg){
        for (ClientHandler o: clients){
            o.sendMsg(msg);
        }
    }
    public static void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public static AuthService getAuthService() {
        return authService;
    }

    public static synchronized boolean isNickBusy(String nick) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(nick)) {
                return true;
            }
        }
        return false;
    }
    public static synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public static void wisperMSG(ClientHandler from, String to, String msg) {
        for (ClientHandler client: clients) {
            if(client.getClientName().equals(to)) {
                client.sendMsg("[Приват от: " + from.getClientName() + msg);
                break;
            }
        }
        from.sendMsg("[Приват для: " + to + msg);
    }
}
