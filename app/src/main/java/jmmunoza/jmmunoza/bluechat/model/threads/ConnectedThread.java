package jmmunoza.jmmunoza.bluechat.model.threads;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jmmunoza.jmmunoza.bluechat.model.constants.MessageConstants;

public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private byte[] mmBuffer;

    private Handler handler;

    public ConnectedThread(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        handler = new Handler(Looper.getMainLooper());

        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            System.out.println("Error occurred when creating input stream");
        }
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Error occurred when creating output stream");
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        mmBuffer = new byte[1024];
        int numBytes;

        while (true) {
            try {
                numBytes = mmInStream.read(mmBuffer);
            } catch (IOException e) {
                System.out.println("Input stream was disconnected");
                break;
            }
            String incomingMessage = new String(mmBuffer, 0, numBytes);
            System.out.println("Message received: " + incomingMessage);
        }


    }

    public void write(byte[] bytes) {
        try {
            System.out.println("Sending message");
            mmOutStream.write(bytes);
            System.out.println("Message sent");

            Message writtenMsg = handler.obtainMessage(
                    MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
            writtenMsg.sendToTarget();
        } catch (IOException e) {
            System.out.println("Error occurred when sending data");

            Message writeErrorMsg = handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString("toast", "Couldn't send data to the other device");
            writeErrorMsg.setData(bundle);
            handler.sendMessage(writeErrorMsg);
        }
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close the connect socket");
        }
    }
}
