package frontend;

public interface ServerMessagesListener {
    default void onConnect() {
        System.out.println("Successfully connected to server!");
    }
}
