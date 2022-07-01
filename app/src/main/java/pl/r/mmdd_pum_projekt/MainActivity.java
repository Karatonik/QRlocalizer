package pl.r.mmdd_pum_projekt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.regex.Pattern;

import pl.r.mmdd_pum_projekt.Helpers.FirebaseHelper;
import pl.r.mmdd_pum_projekt.Helpers.GPSHelper;
import pl.r.mmdd_pum_projekt.Helpers.NotifyHelper;
import pl.r.mmdd_pum_projekt.Helpers.QRScanner;
import pl.r.mmdd_pum_projekt.Models.Device;
import pl.r.mmdd_pum_projekt.Models.LatLng;


public class MainActivity extends AppCompatActivity {
    private static final int RESULT_CAMERA_SCANNER = 4;
    FirebaseHelper firebaseHelper;
    GPSHelper gpsHelper;
    private NotifyHelper notifyHelper;
    private QRScanner qrScanner;
    private Pattern pattern;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GPSHelper.getPermission(this, this);
        gpsHelper = new GPSHelper(this);

        firebaseHelper = FirebaseHelper.getInstance();

        this.pattern = Pattern.compile("QRLocalize:");

        this.notifyHelper = NotifyHelper.getInstance(getApplicationContext(),
                MainActivity.this);
        this.qrScanner = QRScanner.getInstance(getApplicationContext(),
                MainActivity.this);

        Button listBtn = findViewById(R.id.goToList);
        Button regBtn = findViewById(R.id.goToReg);

        listBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, DeviceListActivity.class);
            startActivity(intent);
        });
        regBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterDeviceActivity.class);
            startActivity(intent);
        });

        Button scanQRBtn = findViewById(R.id.bt_scan);

        scanQRBtn.setOnClickListener(view -> qrScanner.scanCode());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_CAMERA_SCANNER) {
                IntentResult intentResult = IntentIntegrator.parseActivityResult(resultCode, data);
                if (intentResult.getContents() != null) {

                    String deviceName = intentResult.getContents();
                    if (pattern.matcher(deviceName).find() && gpsHelper.isLocationEnabled() && gpsHelper.getLongitude() != 0.0) {

                        deviceName = deviceName.substring(12);
                        String finalDeviceName1 = deviceName;

                        firebaseHelper.db().child("devices").child(deviceName).get().addOnCompleteListener(v -> {
                            Device device = v.getResult().getValue(Device.class);
                            LatLng latLng = new LatLng(gpsHelper.getLatitude(),gpsHelper.getLongitude());
                                device.setLatLng(latLng);
                                firebaseHelper.db().child("devices").child(finalDeviceName1).setValue(device);
                        });
                    }

                } else {
                    notifyHelper.sendNotification("Service Scanner Error", "Cannot detected barcode");
                }
            }
        } else {
            notifyHelper.sendNotification("Service Error", "Service error");
        }
    }
}