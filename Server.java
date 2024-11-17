package Java_class_project;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Server started. Waiting for clients...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // Start a thread to receive messages
            Receiver receiver = new Receiver(clientSocket);
            Thread receiverThread = new Thread(receiver);
            receiverThread.start();

            // Start sending messages
            Sender sender = new Sender(clientSocket);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String message;
            while (!(message = userInput.readLine()).equalsIgnoreCase("exit")) {
                sender.sendMessage(message);
            }
            sender.close();
            receiver.stopReceiving();
            clientSocket.close();
            //receiverThread.join();
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

