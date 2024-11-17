package Java_class_project;

import java.time.LocalDateTime;

public class Message {
    private int senderID;
    private int receiverID;
    private String text;
    private LocalDateTime timestamp;
    private Status status;
    private int messageID=1;

    public Message(int senderID, int receiverID, String text, Status status) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.text = text;
        this.timestamp = LocalDateTime.now();
        this.status=status;
        this.messageID = messageID++;
    }
    public void setText(String text) {
        this.text = text;
    }
    public int getMessageID() {
        return messageID++;
    }
    public int getSenderID() {
        return senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public Status getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public void displayMessage() {
        System.out.println("From: " + senderID + " To: " + receiverID);
        System.out.println("Text: " + text);
        System.out.println("Time: " + timestamp);
        System.out.println("Status: " + Status.values());
        System.out.println("MessageID: " + messageID);
    }


}

