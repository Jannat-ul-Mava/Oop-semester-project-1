package Java_class_project;

import java.io.*;
import java.net.*;

public class Sender {
    private Socket socket;
    private PrintWriter writer;

    public Sender(Socket socket) throws IOException {
        this.socket = socket;
        try {
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error creating PrintWriter: " + e.getMessage());
            throw e; // Re-throw to let the caller handle the error
        }
    }

    public void sendMessage(String message) {
        if (writer != null) {
            writer.println(message);
        } else {
            System.err.println("Cannot send message, PrintWriter not initialized.");
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}

