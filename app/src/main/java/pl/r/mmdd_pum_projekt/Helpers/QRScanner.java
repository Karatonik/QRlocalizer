package pl.r.mmdd_pum_projekt.Helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.google.zxing.integration.android.IntentIntegrator;

public class QRScanner {
    private static final int RESULT_CAMERA_SCANNER = 4;

    private final Activity activity;

    @SuppressLint("StaticFieldLeak")
    private static QRScanner qrScanner_instance = null;


    public static QRScanner getInstance(Context context, Activity activity){
        if(qrScanner_instance == null){
            qrScanner_instance = new QRScanner(context,activity);
        }
        return qrScanner_instance;
    }

    private QRScanner(Context context, Activity activity) {
        this.activity = activity;
    }

    public void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this.activity);
        integrator.setPrompt("For flash use volume up key");
        integrator.setRequestCode(RESULT_CAMERA_SCANNER);
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(Capture.class);
        integrator.initiateScan();
    }
}
