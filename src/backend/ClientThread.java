package backend;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    protected Socket socket;

    public ClientThread(Socket s) {
        socket = s;
    }

    @Override
    public void run() {
        InputStream inputStream;
        BufferedReader bReader;
        PrintWriter outputStream;

        try {
            inputStream = socket.getInputStream();
            bReader = new BufferedReader(new InputStreamReader(inputStream));
            outputStream = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        String request;
        while (socket.isConnected() && !socket.isInputShutdown()) {
            try {
                if((request = bReader.readLine()) != null) {
                    // todo блять не приходит сюда нихуя 
                    System.out.println(request);
                    outputStream.println("You requested " + request);
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
