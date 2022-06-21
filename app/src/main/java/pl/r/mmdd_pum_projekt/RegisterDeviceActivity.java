package pl.r.mmdd_pum_projekt;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import pl.r.mmdd_pum_projekt.Models.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

String userID;
String dbURL = "https://qrlocalizer-2b5a3-default-rtdb.europe-west1.firebasedatabase.app";
private FirebaseAuth mAuth;
private FirebaseAuth.AuthStateListener mAuthListener;
private FirebaseDatabase mFirebaseDatabase;
private DatabaseReference myRef;


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

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance(dbURL);
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null)
        {
            userID = user.getUid();
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };


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
                device.addLocalization(locationAndTime); //Pruje się o LocationAndTime, sam device jest ok
                //save to db
                myRef.child("devices").push().setValue(device);

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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
