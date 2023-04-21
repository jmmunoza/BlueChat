package jmmunoza.jmmunoza.bluechat.model.observers;

import java.util.ArrayList;
import java.util.List;

import jmmunoza.jmmunoza.bluechat.model.entities.Message;
import jmmunoza.jmmunoza.bluechat.model.listeners.OnMessageReceivedListener;

public class MessageObserver {
    private static List<OnMessageReceivedListener> onMessageReceivedListeners;

    private MessageObserver(){
        onMessageReceivedListeners = new ArrayList<>();
    }

    public static void addOnMessageReceivedListener(OnMessageReceivedListener listener) {
        if (onMessageReceivedListeners == null) {
            new MessageObserver();
        }

        if(!onMessageReceivedListeners.contains(listener)){
            onMessageReceivedListeners.add(listener);
        }
    }

    public static void removeOnMessageReceivedListener(OnMessageReceivedListener listener) {
        if (onMessageReceivedListeners == null) {
            new MessageObserver();
        }

        onMessageReceivedListeners.remove(listener);
    }

    public static void notifyOnMessageReceived(Message message){
        if (onMessageReceivedListeners == null) {
            new MessageObserver();
        }

        for (OnMessageReceivedListener listener : onMessageReceivedListeners) {
            listener.onMessageReceived(message);
        }
    }
}
