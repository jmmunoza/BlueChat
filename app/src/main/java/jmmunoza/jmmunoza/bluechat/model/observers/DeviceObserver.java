package jmmunoza.jmmunoza.bluechat.model.observers;

import java.util.ArrayList;
import java.util.List;

import jmmunoza.jmmunoza.bluechat.model.entities.Device;
import jmmunoza.jmmunoza.bluechat.model.listeners.OnDeviceFoundListener;

public class DeviceObserver {
    private static List<OnDeviceFoundListener> onDeviceFoundListeners;

    private DeviceObserver(){
        onDeviceFoundListeners = new ArrayList<>();
    }

    public static void addOnDeviceFoundListener(OnDeviceFoundListener listener) {
        if (onDeviceFoundListeners == null) {
            new DeviceObserver();
        }

        if(!onDeviceFoundListeners.contains(listener)){
            onDeviceFoundListeners.add(listener);
        }
    }

    public static void removeOnDeviceFoundListener(OnDeviceFoundListener listener) {
        if (onDeviceFoundListeners == null) {
            new DeviceObserver();
        }

        onDeviceFoundListeners.remove(listener);
    }

    public static void notifyOnDeviceFoundListener(Device device){
        if (onDeviceFoundListeners == null) {
            new DeviceObserver();
        }

        for (OnDeviceFoundListener listener : onDeviceFoundListeners) {
            listener.onDeviceFound(device);
        }
    }
}
