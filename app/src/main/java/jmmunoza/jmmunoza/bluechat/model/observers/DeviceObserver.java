package jmmunoza.jmmunoza.bluechat.model.observers;

import java.util.ArrayList;
import java.util.List;

import jmmunoza.jmmunoza.bluechat.model.entities.Device;
import jmmunoza.jmmunoza.bluechat.model.listeners.OnDeviceFoundListener;
import jmmunoza.jmmunoza.bluechat.model.listeners.OnDeviceSelectedListener;

public class DeviceObserver {
    private static List<OnDeviceFoundListener> onDeviceFoundListeners;
    private static List<OnDeviceSelectedListener> onDeviceSelectedListeners;

    private DeviceObserver(){
        onDeviceFoundListeners = new ArrayList<>();
        onDeviceSelectedListeners = new ArrayList<>();
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

    public static void addOnDeviceSelectedListener(OnDeviceSelectedListener listener) {
        if (onDeviceSelectedListeners == null) {
            new DeviceObserver();
        }

        if(!onDeviceSelectedListeners.contains(listener)){
            onDeviceSelectedListeners.add(listener);
        }
    }

    public static void removeOnDeviceSelectedListener(OnDeviceSelectedListener listener) {
        if (onDeviceSelectedListeners == null) {
            new DeviceObserver();
        }

        onDeviceSelectedListeners.remove(listener);
    }

    public static void notifyOnDeviceSelectedListener(Device device){
        if (onDeviceSelectedListeners == null) {
            new DeviceObserver();
        }

        for (OnDeviceSelectedListener listener : onDeviceSelectedListeners) {
            listener.onDeviceSelected(device);
        }
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
