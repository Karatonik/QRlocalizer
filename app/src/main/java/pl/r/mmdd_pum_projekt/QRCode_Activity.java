package pl.r.mmdd_pum_projekt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import pl.r.mmdd_pum_projekt.Helpers.QRGenerator;
import pl.r.mmdd_pum_projekt.Models.LatLng;

public class QRCode_Activity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_activity);

        String deviceName = getIntent().getStringExtra("deviceName");
        LatLng latLng = getIntent().getParcelableExtra("latLng");


        Button backBtn = findViewById(R.id.fromQRToList);

        TextView deviceTv = findViewById(R.id.deviceTV);

        deviceTv.setText(deviceName + " " + latLng);


        ImageView imageView = findViewById(R.id.idIVQrcode1);

        try {
            imageView.setImageBitmap(QRGenerator.generateQRCode(deviceName));
        } catch (WriterException e) {
            e.printStackTrace();
        }

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, DeviceListActivity.class);
            startActivity(intent);
        });


    }
}
