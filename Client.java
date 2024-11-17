package Java_class_project;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234)) {
            System.out.println("Connected to server.");

            // Start a thread to receive messages
            Receiver receiver = new Receiver(socket);
            Thread receiverThread = new Thread(receiver);
            receiverThread.start();

            // Start sending messages
            Sender sender = new Sender(socket);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String message;
            while (!(message = userInput.readLine()).equalsIgnoreCase("exit")) {
                sender.sendMessage(message);
            }
            sender.close();
            receiver.stopReceiving();  // Custom method in Receiver to stop receiving
           // receiverThread.join();
            socket.close();
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}

