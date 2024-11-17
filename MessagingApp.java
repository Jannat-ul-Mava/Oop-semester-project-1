package Java_class_project;

import java.util.*;
import java.io.*;
import java.net.*;

public class MessagingApp {
    private ArrayList<Contact> contacts;
    private MessageManager messageManager;
    private Socket socket;
    private Sender sender;
    private Thread receiverThread;
    private boolean isReceiving = true;

    public MessagingApp() {
        contacts = new ArrayList<>();
        messageManager = new MessageManager();
        initializeContacts();
        initializeMessages();
    }

    // Initialize some contacts for simulation
    private void initializeContacts() {
        contacts.add(new Contact(1, "Alice","0348439590"));
        contacts.add(new Contact(2, "Bob","0348439590"));
        contacts.add(new Contact(3, "Charlie","0348439590"));
    }

    // Initialize some messages for simulation
    private void initializeMessages() {
        messageManager.addMessage(new Message(1, 2, "Hello Bob!",Status.seen));
        messageManager.addMessage(new Message(2, 1, "Hi Alice, how are you?",Status.delivered));
        messageManager.addMessage(new Message(3, 1, "Hey Alice, long time no see!",Status.read));
    }

    // Display the main menu
    public void displayMenu() {
        System.out.println("\n================================================");
        System.out.println("\tWelcome to the Messaging Application!");
        System.out.println("================================================\n");
        System.out.println("1.  Display Contacts");
        System.out.println("2.  Send Message");
        System.out.println("3.  Receive Messages");
        System.out.println("4.  Search Messages");
        System.out.println("5.  Delete Messages by Contact");
        System.out.println("6.  Display Messages by Contact");
        System.out.println("7.  Mark Messages as Read");
        System.out.println("8.  Add Contact");
        System.out.println("9.  Edit Message");
        System.out.println("10. Search Contact");
        System.out.println("11. Delete Contact");
        System.out.println("12. Check Status of Message");
        System.out.println("13. Exit\n");
    }

    // Handle menu options
    public void handleUserInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayContacts();
                    break;
                case 2:
                    sendMessage();
                    break;
                case 3:
                    startReceivingMessages();
                    break;
                case 4:
                    searchMessages();
                    break;
                case 5:
                    deleteMessagesByContact();
                    break;
                case 6:
                    displayMessagesByContact();
                    break;
                case 7:
                    markMessagesAsRead();
                    break;
                case 8:
                    addContact(contacts);
                    break;
                case 9:
                    editMessage();
                    break;
                case 10:
                    searchContact(contacts);
                    break;
                case 11:
                    deleteContact();
                    break;
                case 12:
                    checkStatusOfMessage();
                    break;
                case 13:
                    System.out.println("\n--------------------------");
                    System.out.println("\tGoodbye!");
                    System.out.println("--------------------------\n");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 13);
    }

    // Display all contacts
    private void displayContacts() {
        for (Contact contact : contacts) {
            contact.displayContact();
        }
    }
    private void checkStatusOfMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Contact ID to check status: ");
        int contactID = scanner.nextInt();

        // Use MessageManager to check status
        messageManager.checkStatus(contactID);}
    // Send a message to a contact
    private void sendMessage() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Contact ID to send a message: ");
        int contactID = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("\nEnter your message: ");
        String text = scanner.nextLine();

        // Find the contact and send the message
        for (Contact contact : contacts) {
            if (contact.getContactID() == contactID) {
                Message message = new Message(1, contactID, text,Status.seen);  // Assuming sender ID is 1 for this demo
                messageManager.addMessage(message);
                sender.sendMessage(text);
                System.out.println("\n============================================");
                System.out.println("\tMessage sent to " + contact.getName());
                System.out.println("=============================================\n");
                return;
            }
        }

        System.out.println("Contact not found.\n");
    }

   public void addContact(ArrayList<Contact> contacts) {
       Scanner sc = new Scanner(System.in);
       String c;
       do {
           System.out.println("\nEnter new contact name: ");
           String newName = sc.nextLine();
           System.out.println("\nEnter new phone number: ");
           String newPhone = sc.nextLine();

           // Creating the new contact
           Contact contact = new Contact(contacts.size() + 1, newName, newPhone);  // Unique contact ID based on size
           contacts.add(contact);

           System.out.println("\n===========================================");
           System.out.println("\tContact added successfully");
           System.out.println("============================================\n");

           System.out.println("\nDo you want to add another contact? (yes/no)");
           c = sc.nextLine();
       } while (c.equalsIgnoreCase("Yes"));
   }

    public void searchContact(ArrayList<Contact> contacts){
        Scanner scanner = new Scanner(System.in);
        String c="yes";
        do{
            System.out.println("\nEnter the contact name you want to search:");
            String name=scanner.nextLine();
            boolean found = false;  // A flag to check if the contact was found

            // Search through the contacts
            for (Contact contact : contacts) {
                if (contact.getName().equalsIgnoreCase(name)) {  // Case-insensitive comparison
                    System.out.println("Contact info:");
                    System.out.println("Name: " + contact.getName());
                    System.out.println("Phone Numbers: " + contact.getPhoneNumber());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Contact not found.");
            }
            c=scanner.nextLine();
        }while(c.equalsIgnoreCase("Yes"));
    }
    //delete contact
    private void deleteContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Contact ID to delete: ");
        int contactID = scanner.nextInt();

        // Find and remove the contact
        Contact contactToRemove = null;
        for (Contact contact : contacts) {
            if (contact.getContactID() == contactID) {
                contactToRemove = contact;
                break;
            }
        }

        if (contactToRemove != null) {
            contacts.remove(contactToRemove);
            System.out.println("Contact '" + contactToRemove.getName() + "' deleted.");

            // Remove all messages related to the contact
            messageManager.deleteMessages(contactID);
            System.out.println("\n\tAll messages related to the contact have been deleted.");
        } else {
            System.out.println("Contact not found.");
        }}
    public void stopReceivingMessages() {
        isReceiving = false;
    }

    // Start receiving messages using a separate thread
    private void startReceivingMessages() throws IOException {
        if (receiverThread == null) {
            receiverThread = new Thread(new Receiver(socket));
            receiverThread.start();
            System.out.println("Receiving messages...");
        } else {
            System.out.println("Already receiving messages.");
        }
    }

    // Search for messages by keyword
    private void searchMessages() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Name to search messages: ");
        String keyword = scanner.nextLine();
        messageManager.searchMessages(keyword);
    }

    // Delete messages for a specific contact
    private void deleteMessagesByContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Contact ID to delete messages: ");
        int contactID = scanner.nextInt();
        messageManager.deleteMessages(contactID);
        System.out.println("Message deleted");
    }

    // Display messages for a specific contact
    private void displayMessagesByContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Contact ID to display messages: ");
        int contactID = scanner.nextInt();
        messageManager.displayAllMessages(contactID);
    }
    // Method to edit a message for a specific contact
    public void editMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Contact ID to edit messages: ");
        int contactID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        System.out.print("\nEnter the message ID to edit: ");
        int messageID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        // Search for the message with the given contact ID and message ID
        Message messageToEdit = messageManager.findMessageByContactID(contactID, messageID);

        if (messageToEdit != null) {
            System.out.print("\nEnter the new message text: ");
            String newText = scanner.nextLine();
            messageToEdit.setText(newText);
            System.out.println("\n=========================================");// Update the message text
            System.out.println("\tMessage updated successfully.");
            System.out.println("=========================================\n");
        } else {
            System.out.println("Message not found for the given contact and message ID.");
        }
    }

    // Mark messages as read for a specific contact
    private void markMessagesAsRead() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Contact ID to mark messages as read: ");
        int contactID = scanner.nextInt();
        messageManager.markMessagesAsRead(contactID);
    }

    // Set up the socket connection (either client or server)
    public void setupConnection(boolean isServer) throws IOException {
        if (isServer) {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started. Waiting for connection...");
            socket = serverSocket.accept();
            System.out.println("\n\tClient connected.");
        } else {
            socket = new Socket("localhost", 1234);
            System.out.println("\nConnected to the server.");
        }

        // Initialize sender after socket connection
        sender = new Sender(socket);
    }
    public void closeConnection() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            System.out.println("Connection closed.");
        }
    }

    public static void main(String[] args) throws IOException {
        MessagingApp app = new MessagingApp();

        // Decide whether this is the server or client side
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter '1' to start as server or '2' to start as client: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            app.setupConnection(true);  // Server
        } else {
            app.setupConnection(false); // Client
        }

        // Start the main menu
        app.handleUserInput();

        // Close connection when the app exits
        app.closeConnection();
    }

}

