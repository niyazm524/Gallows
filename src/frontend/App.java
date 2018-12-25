package frontend;

import backend.Server;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame implements ServerMessagesListener {
    private ClientSocketThread clientSock;
    private JLabel statusText;
    public App() {
        super("Gallows");
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        statusText = new JLabel("Status");
        container.add(statusText);
        clientSock = new ClientSocketThread(this, "localhost", Server.PORT);
        clientSock.start();

    }

    @Override
    public void onConnect() {
        statusText.setText("Connected to server");
        clientSock.send("Wow frontend just started");
    }

    public static void main(String[] args) {
        App app = new App();
        app.setVisible(true);
    }
}
