package jmmunoza.jmmunoza.bluechat.model.interfaces;

import java.util.List;

import jmmunoza.jmmunoza.bluechat.model.entities.Device;
import jmmunoza.jmmunoza.bluechat.model.listeners.OnMessageReceivedListener;

public interface IBluetoothService {
    List<Device> findDevices();
    void connect(Device device);
    void disconnect();
    void sendMessage(String message);
    void setOnMessageReceivedListener(OnMessageReceivedListener listener);
}