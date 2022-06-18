package pl.r.mmdd_pum_projekt;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.r.mmdd_pum_projekt.Models.Device;
import pl.r.mmdd_pum_projekt.Models.LocationAndTime;

public class DeviceListActivity extends AppCompatActivity {
    Button toMainBtn;
    List<Device> devices;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        toMainBtn = (Button) findViewById(R.id.fromListToMain);
        getData();
        customAdapter = new CustomAdapter(getApplicationContext(),devices);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        toMainBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getData(){
        devices =new ArrayList<>();
        Device device = new Device("test");
        LocationAndTime locationAndTime = new LocationAndTime(new LatLng(20,20));
        device.addLocalization(locationAndTime);
        devices.add(device);
        //todo
    }
}
