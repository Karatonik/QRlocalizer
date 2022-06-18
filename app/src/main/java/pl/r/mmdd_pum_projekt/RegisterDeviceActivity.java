package pl.r.mmdd_pum_projekt;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.WriterException;

import pl.r.mmdd_pum_projekt.Helpers.GPSHelper;
import pl.r.mmdd_pum_projekt.Helpers.NotifyHelper;
import pl.r.mmdd_pum_projekt.Helpers.QRGenerator;
import pl.r.mmdd_pum_projekt.Models.Device;
import pl.r.mmdd_pum_projekt.Models.LocationAndTime;

public class RegisterDeviceActivity extends AppCompatActivity {

private Device device;
private EditText editText;

private GPSHelper gpsHelper;
private NotifyHelper notifyHelper;

private ImageView qrCodeIV;
private Button goMainBtn;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        gpsHelper = new GPSHelper(this);
        notifyHelper = NotifyHelper.getInstance(getApplicationContext(),this);
        editText = (EditText) findViewById(R.id.idEdt);
        Button submitBtn = (Button) findViewById(R.id.register);
        qrCodeIV = (ImageView) findViewById(R.id.idIVQrcode);

        goMainBtn = (Button) findViewById(R.id.fromRegToMain);

        goMainBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        submitBtn.setOnClickListener(v->{
            if(!editText.getText().toString().equals("")){
                device = new Device(editText.getText().toString());
                LocationAndTime locationAndTime = new LocationAndTime(new LatLng(
                        gpsHelper.getLatitude(),gpsHelper.getLongitude()));
                device.addLocalization(locationAndTime);
                //save to db

                try {
                    this.qrCodeIV.setImageBitmap(QRGenerator.generateQRCode(this.device.getName()));
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                notifyHelper.sendNotification("Success","Save Device");
            }else{
                notifyHelper.sendNotification("Error","Don't save Device");
            }

        });
    }
}
