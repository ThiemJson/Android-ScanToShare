package teneocto.thiemjason.tlu_connect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import teneocto.thiemjason.tlu_connect.profile.Profile;
import teneocto.thiemjason.tlu_connect.recycleview.SocialRecycleView;
import teneocto.thiemjason.tlu_connect.register.RegisterProfile;
import teneocto.thiemjason.tlu_connect.register.RegisterSocialNetwork;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}