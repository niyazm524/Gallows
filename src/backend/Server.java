package backend;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 3456;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client just connected!");
                System.out.printf("His IP: %s\n", socket.getInetAddress().getHostAddress());

                ClientThread newClientThread = new ClientThread(socket);
                newClientThread.start();
            }


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
