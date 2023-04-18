package jmmunoza.jmmunoza.bluechat.model.entities;

import java.util.List;

public class Chat {
    private final Device device;
    private final List<Message> messages;

    public Chat(Device device, List<Message> messages) {
        this.device = device;
        this.messages = messages;
    }

    public Device getDevice() {
        return device;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
