package ge.mziuri.server;

import java.io.*;
import java.net.Socket;

public class SocketThread implements Runnable {

    private int id;

    private Socket socket;

    public SocketThread(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            while (true) {
                String text = in.readUTF();
                Server.sendMessageForAllExceptOne(text, id);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sentMessage(String message) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }
}
