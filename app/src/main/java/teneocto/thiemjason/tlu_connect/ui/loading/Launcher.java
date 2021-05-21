package teneocto.thiemjason.tlu_connect.ui.loading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import teneocto.thiemjason.tlu_connect.MainActivity;
import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.utils.AppConst;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        this.initFirebaseMessaging();
        this.setUpPermission();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void initFirebaseMessaging(){
        FirebaseMessaging.getInstance().subscribeToTopic(AppConst.notification_topic)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()){
                        Log.i("NOTIFICATION", "FAILURE");
                    }

                    Log.i("NOTIFICATION", "SUCCESSFULL");
                });
    }



    void setUpPermission() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1888);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}