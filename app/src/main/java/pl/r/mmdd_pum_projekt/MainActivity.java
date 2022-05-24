package pl.r.mmdd_pum_projekt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import pl.r.mmdd_pum_projekt.Helpers.GPSHelper;
import pl.r.mmdd_pum_projekt.Helpers.NotifyHelper;
import pl.r.mmdd_pum_projekt.Helpers.QRGenerator;
import pl.r.mmdd_pum_projekt.Helpers.QRScanner;
import pl.r.mmdd_pum_projekt.Models.Device;
import pl.r.mmdd_pum_projekt.Models.LocationAndTime;


public class MainActivity extends AppCompatActivity {
    private static final int RESULT_CAMERA_SCANNER = 4;
    GPSHelper gpsHelper;
    private Button scanQRBtn, generateQrBtn, locationBtn ,showBtn , newBtn;
    private String deviceName;
    private NotifyHelper notifyHelper;
    private QRScanner qrScanner;
    private ImageView qrCodeIV;
    private EditText dataEdt;




    //objekt testowy
    private Device device;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.notifyHelper = NotifyHelper.getInstance(getApplicationContext(),
                MainActivity.this);
        this.qrScanner =QRScanner.getInstance(getApplicationContext(),
                MainActivity.this);

        this.gpsHelper = new GPSHelper(this);
        this.gpsHelper.getPermission(this);

        this.locationBtn = (Button) findViewById(R.id.bt_gps);
        this.scanQRBtn = (Button) findViewById(R.id.bt_scan);
        this.newBtn = (Button) findViewById(R.id.bt_new);
        this.showBtn = (Button) findViewById(R.id.bt_showRecord);
        this.dataEdt = (EditText) findViewById(R.id.idEdt);
        generateQrBtn = (Button) findViewById(R.id.bt_generateQR);
        qrCodeIV = (ImageView) findViewById(R.id.idIVQrcode);

        //testowy
        this.device = new Device("Test");

        scanQRBtn.setOnClickListener(view -> {
            qrScanner.scanCode();
        });
        generateQrBtn.setOnClickListener(v -> {
            try {
                if(!this.dataEdt.getText().toString().equals("")) {
                    this.qrCodeIV.setImageBitmap(QRGenerator.generateQRCode(this.dataEdt.getText().toString()));
                }else{
                   this.notifyHelper.sendNotification("Service error","BLANK NAME !!!");

                }
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });

        locationBtn.setOnClickListener(v -> {
            if (gpsHelper.canGetLocation()) {

                double latitude = gpsHelper.getLatitude();
                double longitude = gpsHelper.getLongitude();
                LocationAndTime locationAndTime = new LocationAndTime(latitude,longitude);

                //add location to object
                this.device.addLocalization(locationAndTime);

                notifyHelper.sendNotification("Location detected", locationAndTime.toString());
            } else {
                notifyHelper.sendNotification("Service Error", "Service error");
            }
        });

        showBtn.setOnClickListener(v->{
            if(!this.dataEdt.getText().toString().equals("")) {
                this.device.setName(this.dataEdt.getText().toString());
            }
            notifyHelper.sendNotification("Device data", this.device.toString());

        });

        this.newBtn.setOnClickListener(v->{
            notifyHelper.sendNotification("The newest data", this.device.getNewestLocalization().toString());
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_CAMERA_SCANNER: { //scanner
                    IntentResult intentResult = IntentIntegrator.parseActivityResult(resultCode, data);
                    System.out.println(data.getData());
                    if (intentResult.getContents() != null) {
                        this.device.setName(intentResult.getContents());
                        notifyHelper.sendNotification("Device detected", this.device.getName());

                    } else {
                        notifyHelper.sendNotification("Service Scanner Error", "Cannot detected barcode");
                    }
                    break;
                }
            }
        } else {
            notifyHelper.sendNotification("Service Error", "Service error");
        }
    }
}