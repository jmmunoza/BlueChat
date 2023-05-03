package jmmunoza.jmmunoza.bluechat.model.threads;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    private static final String NAME = "BluetoothExample";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    public AcceptThread(BluetoothAdapter bluetoothAdapter) {
        BluetoothServerSocket tmp = null;
        try {
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (IOException e) {
            System.out.println("Socket's listen() method failed");
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;

        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                System.out.println("Socket's accept() method failed");
                break;
            }
            System.out.println("Socket accepted");
            System.out.println(socket.getRemoteDevice().getName());
            try {
                InputStream inputStream = socket.getInputStream();
                byte[] buffer = new byte[1024];
                int bytes;

                while (true) {
                    try {
                        bytes = inputStream.read(buffer);
                    } catch (IOException e) {
                        System.out.println("Input stream was disconnected");
                        break;
                    }
                    String incomingMessage = new String(buffer, 0, bytes);
                    System.out.println("Message received: " + incomingMessage);
                }

                mmServerSocket.close();
            } catch (IOException e) {
                System.out.println("Could not close the connect socket");
            }
            break;
        }
    }

    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close the connect socket");
        }
    }
}
