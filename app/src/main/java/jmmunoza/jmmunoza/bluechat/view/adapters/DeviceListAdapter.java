package jmmunoza.jmmunoza.bluechat.view.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import jmmunoza.jmmunoza.bluechat.R;
import jmmunoza.jmmunoza.bluechat.model.entities.Device;
import jmmunoza.jmmunoza.bluechat.model.listeners.OnDeviceFoundListener;
import jmmunoza.jmmunoza.bluechat.model.observers.DeviceObserver;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> implements OnDeviceFoundListener {

    private final LayoutInflater inflater;
    private List<Device> devices;
    private final Context context;

    public DeviceListAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.devices = new ArrayList<>();

        DeviceObserver.addOnDeviceFoundListener(this);
    }

    public void onDestroy(){
        DeviceObserver.removeOnDeviceFoundListener(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_device_list, parent, false);
        return new ViewHolder(view);
    }

    public void addDevice(Device device){
        for (Device d : devices) {
            if (d.getAddress().equals(device.getAddress())) {
                return;
            }
        }

        devices.add(0, device);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> notifyItemInserted(0));
    }

    public void clearDevices(){
        int range = devices.size();
        devices.clear();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> notifyItemRangeRemoved(0, range));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Device device = devices.get(position);

        holder.deviceName.setText(device.getName());
        holder.deviceAddress.setText(device.getAddress());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    @Override
    public void onDeviceFound(Device device) {
        System.out.println(device);
        addDevice(device);
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView deviceName;
        TextView deviceAddress;

        ViewHolder(View itemView) {
            super(itemView);

            deviceName     = itemView.findViewById(R.id.adapter_device_list_name);
            deviceAddress    = itemView.findViewById(R.id.adapter_device_list_address);
        }
    }
}