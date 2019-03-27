package com.dry.messagestest;

/**
 * A class of contacts.
 *
 * @author DuRuyao
 * Create 19/03/20
 */
public class Contacts {
    private String name;
    private String phoneNumber;
    // private int imageID;
    // private boolean isFavorite = false;
    // private boolean isHateful = false;

    public Contacts(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
