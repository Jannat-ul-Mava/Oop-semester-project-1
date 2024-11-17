package Java_class_project;

import java.util.ArrayList;

public class MessageManager {
    private ArrayList<Message> messages;

    public MessageManager() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void searchMessages(String keyword) {
        boolean found = false;
        for (Message message : messages) {
            if (message.getText().contains(keyword)) {
                message.displayMessage();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages found with keyword: " + keyword);
        }
    }

    public void deleteMessages(int contactID) {
        int initialSize = messages.size();
        messages.removeIf(m -> m.getSenderID() == contactID || m.getReceiverID() == contactID);
        int deletedCount = initialSize - messages.size();
        System.out.println("Messages deleted for contact ID: " + contactID);
    }
    public Message findMessageByContactID(int contactID, int messageID) {
        for (Message message : messages) {
            if (message.getReceiverID() == contactID && message.getMessageID() == messageID) {
                return message;  // Return the message if found
            }
        }
        return null;  // Return null if no message is found
    }
    //Check status
    public void checkStatus(int contactID) {
        int unreadCount = 0;
        int totalCount = 0;

        for (Message message : messages) {
            if (message.getReceiverID() == contactID || message.getSenderID() == contactID) {
                totalCount++;
                if (message.getStatus() == Status.seen) {
                    unreadCount++;
                }
            }
        }

        System.out.println("Total Messages: " + totalCount + ", Unread Messages: " + unreadCount);
    }


    public void displayAllMessages(int contactID) {
        boolean found = false;
        for (Message message : messages) {
            if (message.getSenderID() == contactID || message.getReceiverID() == contactID) {
                message.displayMessage();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages found for contact ID: " + contactID);
        }
    }

    public void markMessagesAsRead(int contactID) {
        for (Message message : messages) {
            if (message.getSenderID() == contactID || message.getReceiverID() == contactID) {
                message.getStatus();
            }
        }
    }
}

