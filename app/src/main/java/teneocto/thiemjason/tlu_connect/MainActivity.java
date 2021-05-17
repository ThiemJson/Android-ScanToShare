package teneocto.thiemjason.tlu_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.view.View;

import java.time.Duration;
import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.home.Home;
import teneocto.thiemjason.tlu_connect.imageslider.ImageSliderTest;
import teneocto.thiemjason.tlu_connect.loading.Launcher;
import teneocto.thiemjason.tlu_connect.main.MainSliderAdapter;
import teneocto.thiemjason.tlu_connect.models.MainSliderItem;
import teneocto.thiemjason.tlu_connect.profile.Profile;
import teneocto.thiemjason.tlu_connect.recycleview.SocialRecycleView;
import teneocto.thiemjason.tlu_connect.register.RegisterProfile;
import teneocto.thiemjason.tlu_connect.register.RegisterSocialNetwork;
import teneocto.thiemjason.tlu_connect.scanninghistory.ScanningHistory;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    Handler sliderHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setUpPermission();

        Intent intent = new Intent(this, ScanningHistory.class);
        startActivity(intent);
        finish();
//        initSlider();
    }

    public void initSlider() {
        viewPager = findViewById(R.id.main_slider_container);

        ArrayList<MainSliderItem> mainSliderItems = new ArrayList<>();
        mainSliderItems.add(new MainSliderItem(R.drawable.facebook, "Add your Social Network link", "Add social network you arr using and want to share with another"));
        mainSliderItems.add(new MainSliderItem(R.drawable.instagram, "Scan another profile and share your own", "Scan another user's QR code to quickly retrieve the necessary information"));
        mainSliderItems.add(new MainSliderItem(R.drawable.twiiter, "Create your Profile", "Set up your basic personal information to share with another"));
        mainSliderItems.add(new MainSliderItem(R.drawable.facebook, "Add your Social Network link", "Add social network you arr using and want to share with another"));
        mainSliderItems.add(new MainSliderItem(R.drawable.instagram, "Scan another profile and share your own", "Scan another user's QR code to quickly retrieve the necessary information"));
        mainSliderItems.add(new MainSliderItem(R.drawable.twiiter, "Create your Profile", "Set up your basic personal information to share with another"));

        viewPager.setAdapter(new MainSliderAdapter(mainSliderItems, viewPager));

        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOrientation(viewPager.ORIENTATION_HORIZONTAL);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
//            float myOffset = position * -(2 * 10 + 50);
//            if (position < -1) {
//                page.setTranslationX(-myOffset);
//            } else if (position <= 1) {
//                float scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f));
//                page.setTranslationX(myOffset);
//                page.setScaleY(scaleFactor);
//                page.setAlpha(scaleFactor);
//            } else {
//                page.setAlpha(0);
//                page.setTranslationX(myOffset);
//            }
        });

        viewPager.setPageTransformer(compositePageTransformer);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 1750);
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i("Main", "Running");
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
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
}