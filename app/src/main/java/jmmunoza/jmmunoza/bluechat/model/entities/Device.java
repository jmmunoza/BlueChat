package jmmunoza.jmmunoza.bluechat.model.entities;


import androidx.annotation.NonNull;

public class Device {
    private final String name;
    private final String address;

    public Device(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " (" + address + ")";
    }
}
