package pl.r.mmdd_pum_projekt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.r.mmdd_pum_projekt.Models.Device;
import pl.r.mmdd_pum_projekt.Models.LocationAndTime;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    Button button;
    private GoogleMap map;
    private SupportMapFragment supportMapFragment;
    private Device device;
    private LocationAndTime locationAndTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        locationAndTime = getIntent().getParcelableExtra("local");
        device = getIntent().getParcelableExtra("device");


        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        button = findViewById(R.id.bt_to_at1);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLng(locationAndTime.getLatLng().getGoogleLatLng()));
        map.addMarker(new MarkerOptions().position(locationAndTime.getLatLng().getGoogleLatLng())
                .title(device.getName()));
    }
}
