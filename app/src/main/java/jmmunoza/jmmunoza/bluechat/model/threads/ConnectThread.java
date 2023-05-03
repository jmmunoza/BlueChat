package jmmunoza.jmmunoza.bluechat.model.threads;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public ConnectThread(BluetoothDevice device) {
        BluetoothSocket tmp = null;
        mmDevice = device;

        try {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            System.out.println("Socket's create() method failed");
        }
        mmSocket = tmp;
        System.out.println("Socket created");
    }

    public void run() {
        System.out.println("Running");

        try {

            mmSocket.connect();
            System.out.println("Socket connected");
        } catch (IOException connectException) {
            return;
        }

        OutputStream outputStream = null;
        try {
            outputStream = mmSocket.getOutputStream();
            String message = "Mensaje que deseas XXXXXXXXXXX";
            outputStream.write(message.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
        ConnectedThread connectedThread = new ConnectedThread(mmSocket);
        connectedThread.start();
        connectedThread.write("KILIAM EL PAPU".getBytes());

         */
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close the client socket");
        }
    }
}