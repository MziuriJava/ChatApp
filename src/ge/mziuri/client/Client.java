package ge.mziuri.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private DataOutputStream out;
    private DataInputStream in;

    public void start() {
        try {
            Socket socket = new Socket("localhost", 8080);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Thread scannerThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                try {
                    String text = scanner.nextLine();
                    out.writeUTF(text);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        scannerThread.start();
        Thread socketThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String text = in.readUTF();
                        System.out.println(text);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        socketThread.start();
    }
}
