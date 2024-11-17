package Java_class_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Receiver implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private volatile boolean running = true;
    public Receiver(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /*public void run() {
        try {
            String receivedMessage;
            while ((receivedMessage = reader.readLine()) != null) {
                System.out.println("Message received: " + receivedMessage);
            }
        } catch (IOException e) {
            System.out.println("Error receiving message: " + e.getMessage());
        }
    }
}*/
    @Override
    public void run() {
        String message;
        try {
            while (running && (message = reader.readLine()) != null) {
                System.out.println("Received: " + message);
            }
        } catch (IOException e) {
            if (running) {
                System.out.println("Receiver error: " + e.getMessage());
            }
        } finally {
            close();
        }
    }

    public void stopReceiving() {
        running = false; // Stop the receiving loop
        close();
    }

    private void close() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing receiver: " + e.getMessage());
        }
    }
}


