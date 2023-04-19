package jmmunoza.jmmunoza.bluechat.model.listeners;

import jmmunoza.jmmunoza.bluechat.model.entities.Message;

public interface OnMessageReceivedListener {
    void onMessageReceived(Message message);
}
