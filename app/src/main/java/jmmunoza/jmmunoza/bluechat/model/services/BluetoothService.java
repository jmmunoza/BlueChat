package jmmunoza.jmmunoza.bluechat.model.services;

import static jmmunoza.jmmunoza.bluechat.model.constants.RequestCodes.REQUEST_BLUETOOTH_SCAN;
import static jmmunoza.jmmunoza.bluechat.model.constants.RequestCodes.REQUEST_DISCOVERABLE_BT;
import static jmmunoza.jmmunoza.bluechat.model.constants.RequestCodes.REQUEST_ENABLE_BT;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import jmmunoza.jmmunoza.bluechat.BlueChatApp;
import jmmunoza.jmmunoza.bluechat.model.entities.Device;
import jmmunoza.jmmunoza.bluechat.model.interfaces.IBluetoothService;
import jmmunoza.jmmunoza.bluechat.model.listeners.OnMessageReceivedListener;

public class BluetoothService implements IBluetoothService {
    private Activity activity;
    private BluetoothAdapter bluetoothAdapter;

    public BluetoothService(Activity activity) {
        this.activity = activity;

        BluetoothManager bluetoothManager = (BluetoothManager) BlueChatApp.getContext().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // Ensures Bluetooth is discoverable
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 1000);
        activity.startActivityForResult(discoverableIntent, REQUEST_DISCOVERABLE_BT);

        // Ensures Location is enabled
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_BLUETOOTH_SCAN);
        } else {
            findDevices();
        }
    }

    @Override
    public void findDevices() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress();

                    if(deviceName != null) {
                        System.out.println(deviceName + " " + deviceHardwareAddress);
                    }
                }
            }
        };

        activity.registerReceiver(receiver, filter);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while(true){
                assert bluetoothAdapter != null;
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }

                bluetoothAdapter.startDiscovery();
                try {
                    Thread.sleep(12000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

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
