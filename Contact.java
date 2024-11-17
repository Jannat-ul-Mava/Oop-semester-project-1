package Java_class_project;

public class Contact {
    private int contactID;
    private String name;
    private String phoneNumber;

    public Contact(int contactID, String name, String phoneNumber) {
        this.contactID = contactID;
        setName(name);
        setPhoneNumber(phoneNumber);
    }
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("\\d{10}")) { // Ensuring the phone number is 10 digits
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Phone number must be 10 digits");
        }
    }
    public int getContactID() {
        return contactID;
    }

    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void displayContact() {
        System.out.println("ID:SP" + contactID + "  Name: " + name + "  Phone Number: " + phoneNumber);
    }
}

