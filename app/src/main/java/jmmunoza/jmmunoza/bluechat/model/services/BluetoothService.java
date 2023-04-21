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
import android.os.SystemClock;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import jmmunoza.jmmunoza.bluechat.BlueChatApp;
import jmmunoza.jmmunoza.bluechat.model.entities.Device;
import jmmunoza.jmmunoza.bluechat.model.interfaces.IBluetoothService;
import jmmunoza.jmmunoza.bluechat.model.listeners.OnMessageReceivedListener;
import jmmunoza.jmmunoza.bluechat.model.observers.DeviceObserver;

public class BluetoothService implements IBluetoothService {
    private final Activity activity;
    private final BluetoothAdapter bluetoothAdapter;
    private  BroadcastReceiver bluetoothReceiver;

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
        bluetoothReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    Device device = new Device(bluetoothDevice.getName(), bluetoothDevice.getAddress());

                    if (device.getName() != null) {
                        DeviceObserver.notifyOnDeviceFoundListener(device);
                    }
                }
            }
        };

        activity.registerReceiver(bluetoothReceiver, filter);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(findDevicesRunnable);
    }

    private final Runnable findDevicesRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                assert bluetoothAdapter != null;
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }

                System.out.println(bluetoothAdapter.startDiscovery());
                SystemClock.sleep(6000);
            }
        }
    };

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

    @Override
    public void stop() {
        if (bluetoothReceiver != null) activity.unregisterReceiver(bluetoothReceiver);
    }
}
