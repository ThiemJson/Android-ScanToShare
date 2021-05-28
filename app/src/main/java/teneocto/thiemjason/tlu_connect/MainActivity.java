package teneocto.thiemjason.tlu_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.database.DBHelper;
import teneocto.thiemjason.tlu_connect.ui.main.MainSliderAdapter;
import teneocto.thiemjason.tlu_connect.ui.models.MainSliderItemDTO;
import teneocto.thiemjason.tlu_connect.ui.register.RegisterProfile;

public class MainActivity extends AppCompatActivity {
    ViewPager2 mViewPager;
    Handler mSliderHandler = new Handler();
    AdView adView;

    // Button
    Button mConnectFacebook;
    Button mSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setUpPermission();
        loadAd();
        initSlider();

        mConnectFacebook = findViewById(R.id.btn_main_ac_register_facebook);
        mSkip = findViewById(R.id.btn_main_ac_skip);

        mConnectFacebook.setOnClickListener(v -> connectFacebook());
        mSkip.setOnClickListener(v -> skip());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSliderHandler.removeCallbacks(sliderRunnable);
    }

    public void initSlider() {
        mViewPager = findViewById(R.id.main_slider_container);

        ArrayList<MainSliderItemDTO> mainSliderItemDTOS = new ArrayList<>();
        mainSliderItemDTOS.add(new MainSliderItemDTO(R.drawable.facebook, "Add your Social Network link", "Add social network you arr using and want to share with another"));
        mainSliderItemDTOS.add(new MainSliderItemDTO(R.drawable.instagram, "Scan another profile and share your own", "Scan another user's QR code to quickly retrieve the necessary information"));
        mainSliderItemDTOS.add(new MainSliderItemDTO(R.drawable.twiiter, "Create your Profile", "Set up your basic personal information to share with another"));
        mainSliderItemDTOS.add(new MainSliderItemDTO(R.drawable.facebook, "Add your Social Network link", "Add social network you arr using and want to share with another"));
        mainSliderItemDTOS.add(new MainSliderItemDTO(R.drawable.instagram, "Scan another profile and share your own", "Scan another user's QR code to quickly retrieve the necessary information"));
        mainSliderItemDTOS.add(new MainSliderItemDTO(R.drawable.twiiter, "Create your Profile", "Set up your basic personal information to share with another"));

        mViewPager.setAdapter(new MainSliderAdapter(mainSliderItemDTOS, mViewPager));

        mViewPager.setClipToPadding(false);
        mViewPager.setClipChildren(false);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOrientation(mViewPager.ORIENTATION_HORIZONTAL);
        mViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        mViewPager.setPageTransformer(compositePageTransformer);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mSliderHandler.removeCallbacks(sliderRunnable);
                mSliderHandler.postDelayed(sliderRunnable, 1750);
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i("Main", "Running");
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    };


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

    void loadAd() {
        MobileAds.initialize(this, initializationStatus -> {
        });

        adView = findViewById(R.id.main_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    void connectFacebook(){
//        DBHelper dbHelper = new DBHelper(this);
//        dbHelper.dropDatabase(DBConst.DB_NAME);
    }

    void skip(){
        Intent intent = new Intent(this, RegisterProfile.class);
        startActivity(intent);
    }
}