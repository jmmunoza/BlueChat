package jmmunoza.jmmunoza.bluechat.view.activities;

import static jmmunoza.jmmunoza.bluechat.model.constants.RequestCodes.REQUEST_BLUETOOTH_SCAN;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import jmmunoza.jmmunoza.bluechat.R;
import jmmunoza.jmmunoza.bluechat.model.entities.Device;
import jmmunoza.jmmunoza.bluechat.model.listeners.OnDeviceSelectedListener;
import jmmunoza.jmmunoza.bluechat.model.observers.DeviceObserver;
import jmmunoza.jmmunoza.bluechat.model.services.BluetoothService;
import jmmunoza.jmmunoza.bluechat.view.fragments.FragmentChats;
import jmmunoza.jmmunoza.bluechat.view.fragments.FragmentDevices;

public class HomeActivity extends AppCompatActivity implements OnDeviceSelectedListener {
    // Fragments
    private FragmentChats fragmentChats;
    private FragmentDevices fragmentDevices;

    // Services
    BluetoothService bluetoothService;

    // Consonants
    private static final int DEVICES   = 0;
    private static final int CHATS     = 1;

    // Components
    private ViewPager2 pager;
    private BottomNavigationView bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);

        bluetoothService = new BluetoothService(this);

        if (savedInstanceState == null) {
            loadComponents();
        }

        DeviceObserver.addOnDeviceSelectedListener(this);

    }

    private void loadComponents() {
        fragmentChats = new FragmentChats();
        fragmentDevices = new FragmentDevices();

        loadBottom();
        loadPager();

    }

    private void loadPager() {
        PagerAdapter adapter = new PagerAdapter(this);
        pager = findViewById(R.id.activity_main_pager);
        pager.setUserInputEnabled(false);
        pager.setOffscreenPageLimit(4);
        pager.setAdapter(adapter);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case CHATS:
                        bottom.setSelectedItemId(R.id.action_chats);
                        break;
                    case DEVICES:
                        bottom.setSelectedItemId(R.id.action_devices);
                        break;
                }
            }
        });
    }

    private void loadBottom(){
        bottom = findViewById(R.id.activity_main_bottom);
        bottom.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_chats:
                    pager.setCurrentItem(CHATS);
                    break;
                case R.id.action_devices:
                    pager.setCurrentItem(DEVICES);
                    break;
            }
            return true;
        });
    }

    @Override
    public void onDeviceSelected(Device device) {
        bluetoothService.connect(device);
    }

    private class PagerAdapter extends FragmentStateAdapter {
        public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case CHATS:
                    return  fragmentChats;
                case DEVICES:
                    return  fragmentDevices;
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
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

    @Override
    public void onBackPressed() {
        if(pager.getCurrentItem() != DEVICES){
            pager.setCurrentItem(DEVICES);
        } else {
            moveTaskToBack(false);
        }
    }

    @Override
    public void onDestroy() {
        DeviceObserver.removeOnDeviceSelectedListener(this);
        bluetoothService.stop();
        super.onDestroy();
    }
}