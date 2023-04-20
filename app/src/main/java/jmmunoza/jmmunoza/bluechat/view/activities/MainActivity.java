package jmmunoza.jmmunoza.bluechat.view.activities;

import static jmmunoza.jmmunoza.bluechat.model.constants.RequestCodes.REQUEST_BLUETOOTH_SCAN;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import jmmunoza.jmmunoza.bluechat.R;
import jmmunoza.jmmunoza.bluechat.model.services.BluetoothService;

public class MainActivity extends AppCompatActivity {
    BluetoothService bluetoothService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothService = new BluetoothService(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BLUETOOTH_SCAN) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bluetoothService.findDevices();
            }
        }
    }
}