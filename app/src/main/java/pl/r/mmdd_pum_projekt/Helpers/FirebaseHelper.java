package pl.r.mmdd_pum_projekt.Helpers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    private static FirebaseHelper instance;

    private final DatabaseReference databaseReference;

    private FirebaseHelper() {
        String dbURL = "https://qrlocalizer-2b5a3-default-rtdb.europe-west1.firebasedatabase.app";
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance(dbURL);
        databaseReference = mFirebaseDatabase.getReference();
    }


    public static FirebaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseHelper();
        }
        return instance;
    }

    public DatabaseReference db() {
        return databaseReference;
    }

}
