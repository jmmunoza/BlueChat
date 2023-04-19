package jmmunoza.jmmunoza.bluechat.model.services;

import java.util.List;

import jmmunoza.jmmunoza.bluechat.model.entities.Device;
import jmmunoza.jmmunoza.bluechat.model.interfaces.IBluetoothService;
import jmmunoza.jmmunoza.bluechat.model.listeners.OnMessageReceivedListener;

public class BluetoothService implements IBluetoothService {
    @Override
    public List<Device> findDevices() {
        return null;
    }

    @Override
    public void connect(Device device) {

    }

    @Override
    public void disconnect(Device device) {

    }

    @Override
    public void sendMessage(Device device, String message) {

    }

    @Override
    public void setOnMessageReceivedListener(OnMessageReceivedListener listener) {

    }
}
