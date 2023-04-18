package jmmunoza.jmmunoza.bluechat.model.entities;

import java.util.Date;

public class Message {
    private final Date date;
    private final String message;
    private final Device device;

    public Message(Date date, String message, Device device) {
        this.device = device;
        this.date = date;
        this.message = message;
    }

    public Device getDevice() {
        return device;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
