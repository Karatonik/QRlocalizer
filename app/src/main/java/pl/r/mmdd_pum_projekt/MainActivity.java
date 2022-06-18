package pl.r.mmdd_pum_projekt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
    //GPSHelper gpsHelper;
    private Button scanQRBtn, generateQrBtn, locationBtn ,showBtn , newBtn, regBtn, listBtn;
   // private String deviceName;
    private NotifyHelper notifyHelper;
    private QRScanner qrScanner;
  //  private ImageView qrCodeIV;
 //   private EditText dataEdt;

  /*  private GoogleMap map;
    private SupportMapFragment supportMapFragment;*/




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

       // this.gpsHelper = new GPSHelper(this);
      //  this.gpsHelper.getPermission(this);

        this.listBtn =(Button) findViewById(R.id.goToList);
        this.regBtn = (Button) findViewById(R.id.goToReg);

        listBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, DeviceListActivity.class);
            startActivity(intent);
        });
        regBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, RegisterDeviceActivity.class);
            startActivity(intent);
        });



        this.scanQRBtn = (Button) findViewById(R.id.bt_scan);

        scanQRBtn.setOnClickListener(view -> {
            qrScanner.scanCode();
        });
        //this.locationBtn = (Button) findViewById(R.id.bt_gps);
       // this.newBtn = (Button) findViewById(R.id.bt_new);
       // this.showBtn = (Button) findViewById(R.id.bt_showRecord);
       // this.dataEdt = (EditText) findViewById(R.id.idEdt);
       // generateQrBtn = (Button) findViewById(R.id.bt_generateQR);
       // qrCodeIV = (ImageView) findViewById(R.id.idIVQrcode);

       // supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
             //   .findFragmentById(R.id.map);
       // supportMapFragment.getMapAsync(this);

        //testowy
     //   this.device = new Device("Test");


        /*
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
        });*/

       /* locationBtn.setOnClickListener(v -> {
            if (gpsHelper.canGetLocation()) {

                double latitude = gpsHelper.getLatitude();
                double longitude = gpsHelper.getLongitude();
                LocationAndTime locationAndTime = new LocationAndTime(
                        new LatLng(latitude,longitude));

                //add location to object
                this.device.addLocalization(locationAndTime);

                notifyHelper.sendNotification("Location detected", locationAndTime.toString());
            } else {
                notifyHelper.sendNotification("Service Error", "Service error");
            }
        });*/

     /*   showBtn.setOnClickListener(v->{
            if(!this.dataEdt.getText().toString().equals("")) {
                this.device.setName(this.dataEdt.getText().toString());
            }
            setMap(device);
            notifyHelper.sendNotification("Device data", this.device.toString());

        });

        this.newBtn.setOnClickListener(v->{
            notifyHelper.sendNotification("The newest data", this.device.getNewestLocalization().toString());
        });*/
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
                        //regex
                        notifyHelper.sendNotification("Device detected", intentResult.getContents());

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

/*
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(50,50)));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setMap(Device device){
        map.addMarker(new MarkerOptions().position(device.getNewestLocalization().getLatLng())
                .title(device.getName()));
        map.moveCamera(CameraUpdateFactory.newLatLng(device.getNewestLocalization().getLatLng()));
    }
*/
}