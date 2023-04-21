package jmmunoza.jmmunoza.bluechat.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import jmmunoza.jmmunoza.bluechat.R;
import jmmunoza.jmmunoza.bluechat.view.adapters.DeviceListAdapter;

public class FragmentDevices extends Fragment {
    private RecyclerView list;

    @SuppressLint("InflatePrams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_devices, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadComponents();
    }

    private void loadComponents(){
        list = requireView().findViewById(R.id.fragment_devices_list);

        loadList();
    }

    private void loadList(){
        DeviceListAdapter adapter = new DeviceListAdapter(requireContext());
        list.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        list.setAdapter(adapter);

    }
}

