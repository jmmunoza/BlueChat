package jmmunoza.jmmunoza.bluechat.model.entities;

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
}
