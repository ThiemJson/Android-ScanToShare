package teneocto.thiemjason.tlu_connect.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import info.androidhive.barcode.BarcodeReader;
import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.profile.Profile;

public class Home extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    HomeAdapter adapter;

    // Fragment
    HomeQRImage homeQRImage;
    HomeQRScanner homeQRScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.initTabLayout();
    }

    /**
     * Init tablayout
     */
    void initTabLayout() {
        tabLayout = findViewById(R.id.home_tablayout);
        viewPager = findViewById(R.id.home_view_paper);

        adapter = new Home.HomeAdapter(getSupportFragmentManager(), 12);

        homeQRImage = new HomeQRImage();
        homeQRScanner = new HomeQRScanner();
        adapter.AddFragment(homeQRImage , "QR List");
        adapter.AddFragment(homeQRScanner , "Scan QR");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

       homeQRScanner.onPause();

       tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               switch(tab.getPosition()) {
                   case 0:
                       homeQRScanner.onPause();
                       break;
                   case 1:
                       homeQRScanner.startPreview();
                       break;
               }
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });
    }

private class HomeAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String> stringArrayList = new ArrayList<>();

    public HomeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void AddFragment(Fragment fragment, String s) {
        fragmentArrayList.add(fragment);
        stringArrayList.add(s);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringArrayList.get(position);
    }
}
}