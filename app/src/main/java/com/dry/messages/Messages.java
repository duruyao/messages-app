package com.dry.messages;

/**
 * A class of messages.
 *
 * @author DuRuyao
 * @version 1.0
 * Create 19/03/26 10:36 AM
 * Update [1] [yy/mm/dd hh:mm] [name] [description]
 */
public class Messages {

    private final int RECEIVED = 1;
    private final int SENT = 2;
    private final boolean BE_READ = true;
    private final boolean BE_NOT_READ = false;

    private String address;
    private int person;
    private String body;
    private long date;
    private int type;
    private int read;

    /**
     * Constructor of Messages class.
     *
     * @param address The phone number of contract.
     * @param body    The text of message.
     */
    public Messages(String address, int person, String body, long date, int type, int read) {
        this.address = address;
        this.person = person;
        this.body = body;
        this.date = date;
        this.type = type;
        this.read = read;

    }

    public String getAddress() {
        return this.address;
    }

    public int getPerson() {
        return this.person;
    }

    public String getBody() {
        return this.body;
    }

    public long getDate() {
        return this.date;
    }

    public int getType() {
        return this.type;
    }

    public int getRead() {
        return this.read;
    }

}
