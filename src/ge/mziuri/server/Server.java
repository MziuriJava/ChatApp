package ge.mziuri.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static int counter = 1;

    private static List<SocketThread> activeClients = new ArrayList<>();

    public static void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                Socket socket = serverSocket.accept();
                SocketThread socketThread = new SocketThread(counter, socket);
                Thread thread = new Thread(socketThread);
                thread.start();
                activeClients.add(socketThread);
                counter++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void sendMessageForAllExceptOne(String message, int id) {
        for (SocketThread socketThread :  activeClients) {
            if (socketThread.getId() != id) {
                socketThread.sentMessage(id + ": " + message);
            }
        }
    }
}
