package teneocto.thiemjason.tlu_connect.ui.drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.notification.Notification;
import teneocto.thiemjason.tlu_connect.ui.policies.PoliciesAndTerm;
import teneocto.thiemjason.tlu_connect.ui.profile.Profile;
import teneocto.thiemjason.tlu_connect.ui.scannedhistory.ScannedHistory;
import teneocto.thiemjason.tlu_connect.ui.support.Support;

/**
 * Home Activity with left navigation
 */
public class Drawer extends AppCompatActivity {

    static String TAG = "Drawer ";

    DrawerLayout mDrawerLayout;

    NavigationView mNavigationView;

    NavController mNavController;

    DrawerController mDrawerController;

    Button mNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        this.initDrawerLayout();
        this.drawerListener();
        this.setUpPermission();

        mNotification = findViewById(R.id.home_notification_icon);
        mNotification.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Notification.class);
            startActivity(intent);
        });
    }

    /**
     * Init drawer layout
     */
    private void initDrawerLayout() {
        mDrawerLayout = findViewById(R.id.drawer);
        mNavigationView = findViewById(R.id.nav_view);
        Button openDrawer = findViewById(R.id.home_menu_icon);
        mDrawerController = new DrawerController(this, this);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(mNavigationView, mNavController);
        openDrawer.setOnClickListener(v -> mDrawerLayout.openDrawer(GravityCompat.START));
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeFragment:
                        break;
                    case R.id.profileFragment:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        break;
                    case R.id.scanningHistoryFragment:
                        startActivity(new Intent(getApplicationContext(), ScannedHistory.class));
                        break;
                    case R.id.policiesAndTermFragment:
                        startActivity(new Intent(getApplicationContext(), PoliciesAndTerm.class));
                        break;
                    case R.id.support:
                        startActivity(new Intent(getApplicationContext(), Support.class));
                        break;
                    case R.id.aboutFragment:
                        mDrawerController.onAboutClick();
                        break;
                };
                item.setChecked(false);
                mDrawerLayout.close();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(false);
        return super.onOptionsItemSelected(item);
    }

    private void drawerListener() {
        this.mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    /**
     * Request Permission
     */
    private void setUpPermission() {
        Thread background;
        background = new Thread() {
            public void run() {
                try {
                    int permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        makeRequest();
                    }
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1888);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}