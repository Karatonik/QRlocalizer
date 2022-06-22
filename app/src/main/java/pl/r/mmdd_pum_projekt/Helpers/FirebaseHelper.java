package pl.r.mmdd_pum_projekt.Helpers;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    private static FirebaseHelper instance;

    private final DatabaseReference databaseReference;

    private FirebaseHelper() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String dbURL = "https://qrlocalizer-2b5a3-default-rtdb.europe-west1.firebasedatabase.app";
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance(dbURL);
        databaseReference = mFirebaseDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
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
