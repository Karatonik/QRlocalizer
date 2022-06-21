package pl.r.mmdd_pum_projekt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.r.mmdd_pum_projekt.Models.Device;
import pl.r.mmdd_pum_projekt.Models.LatLng;
import pl.r.mmdd_pum_projekt.Models.LocationAndTime;

public class DeviceListActivity extends AppCompatActivity {
    Button toMainBtn;
    List<Device> devices;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    DatabaseReference myRef;
    String dbURL = "https://qrlocalizer-2b5a3-default-rtdb.europe-west1.firebasedatabase.app";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        getData();

        toMainBtn = (Button) findViewById(R.id.fromListToMain);
        toMainBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getData() {
        devices = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance(dbURL).getReference();
        myRef.child("devices").get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
                task.getResult().getChildren().forEach(v -> devices.add( v.getValue(Device.class)));
            recyclerView = (RecyclerView) findViewById(R.id.rv);

            customAdapter = new CustomAdapter(getApplicationContext(), devices);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        });
    }
}
