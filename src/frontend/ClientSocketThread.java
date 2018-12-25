package frontend;

import java.io.*;
import java.net.Socket;

class ClientSocketThread extends Thread {
    private Socket sock;
    private String host;
    private int port;
    private boolean isConnected = false;
    private OutputStream outStream;
    private InputStream inStream;
    private BufferedReader buffReader;
    private PrintWriter printWriter;
    private ServerMessagesListener listener;

    ClientSocketThread(ServerMessagesListener listener, String host, int port) {
        this.listener = listener;
        this.host = host;
        this.port = port;
    }

    public void run() {
        do {
            if(!connect()) {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!isConnected);
        listener.onConnect();
        try {
            String line;
            while( (line = buffReader.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void send(String request) {
        if(printWriter != null && isConnected) {
            System.out.println("sending string");
            printWriter.print(request + "\r\n");
        }
    }

    boolean connect() {
        try {
            if(sock != null && !sock.isClosed())
                sock.close();

            sock = new Socket(host, port);
            isConnected = sock.isConnected();
            inStream = sock.getInputStream();
            buffReader = new BufferedReader(new InputStreamReader(inStream));
            outStream = sock.getOutputStream();
            printWriter = new PrintWriter(outStream, true);
        } catch (IOException e) {
            isConnected = false;
            return false;
        }
        return isConnected;
    }

    void disconnect() {
        if(sock != null) {
            try {
                outStream.close();
                inStream.close();
                sock.close();
                isConnected = false;
            } catch (IOException e) {
                // sorry don't want anything
            }
        }

    }
}
