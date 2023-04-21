package jmmunoza.jmmunoza.bluechat.model.interfaces;

import jmmunoza.jmmunoza.bluechat.model.entities.Device;
import jmmunoza.jmmunoza.bluechat.model.listeners.OnMessageReceivedListener;

public interface IBluetoothService {
    void findDevices();
    void connect(Device device);
    void disconnect(Device device);
    void sendMessage(Device device, String message);
    void setOnMessageReceivedListener(OnMessageReceivedListener listener);
    void stop();
}
