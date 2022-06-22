package pl.r.mmdd_pum_projekt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import pl.r.mmdd_pum_projekt.Helpers.FirebaseHelper;
import pl.r.mmdd_pum_projekt.Helpers.GPSHelper;
import pl.r.mmdd_pum_projekt.Helpers.NotifyHelper;
import pl.r.mmdd_pum_projekt.Helpers.QRGenerator;
import pl.r.mmdd_pum_projekt.Models.Device;
import pl.r.mmdd_pum_projekt.Models.LatLng;

public class RegisterDeviceActivity extends AppCompatActivity {

    private Device device;
    private EditText editText;
    private GPSHelper gpsHelper;
    private NotifyHelper notifyHelper;
    private ImageView qrCodeIV;
    private FirebaseHelper firebaseHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        gpsHelper = new GPSHelper(this);
        firebaseHelper = FirebaseHelper.getInstance();


        notifyHelper = NotifyHelper.getInstance(getApplicationContext(), this);
        editText = findViewById(R.id.idEdt);
        Button submitBtn = findViewById(R.id.register);
        qrCodeIV = findViewById(R.id.idIVQrcode);


        Button goMainBtn = findViewById(R.id.fromRegToMain);

        goMainBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        submitBtn.setOnClickListener(v -> {
            if (!editText.getText().toString().equals("")) {

                firebaseHelper.db().child("devices").child(editText.getText()
                        .toString()).get().addOnCompleteListener(v1 -> {
                    if (!v1.getResult().exists()) {
                        this.device = new Device(editText.getText().toString(), new LatLng(
                                gpsHelper.getLatitude(), gpsHelper.getLongitude()));

                        firebaseHelper.db().child("devices").child(this.device.getName()).setValue(this.device);
                        try {
                            this.qrCodeIV.setImageBitmap(QRGenerator.generateQRCode(this.device.getName()));
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                        notifyHelper.sendNotification("Success", "Save Device");
                    } else {
                        notifyHelper.sendNotification("Error", "Object exist");
                    }
                });

            }
        });
    }
}

