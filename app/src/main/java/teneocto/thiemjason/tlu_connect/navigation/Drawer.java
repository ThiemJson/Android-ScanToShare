package teneocto.thiemjason.tlu_connect.navigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.home.Home;
import teneocto.thiemjason.tlu_connect.policies.PoliciesAndTerm;
import teneocto.thiemjason.tlu_connect.profile.Profile;
import teneocto.thiemjason.tlu_connect.scanninghistory.ScanningHistory;
import teneocto.thiemjason.tlu_connect.support.Support;

public class Drawer extends AppCompatActivity {
    private static String TAG = "Drawer ";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavController navController;
    private DrawerController drawerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        this.initDrawerLayout();
        this.drawerListener();
    }

    /**
     * Init drawer layout
     */
    private void initDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        Button openDrawer = findViewById(R.id.home_menu_icon);
        drawerController = new DrawerController(this, this);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(navigationView, navController);
        openDrawer.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeFragment:
                        drawerLayout.close();
                        break;
                    case R.id.profileFragment:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        break;
                    case R.id.scanningHistoryFragment:
                        startActivity(new Intent(getApplicationContext(), ScanningHistory.class));
                        break;
                    case R.id.policiesAndTermFragment:
                        startActivity(new Intent(getApplicationContext(), PoliciesAndTerm.class));
                        break;
                    case R.id.support:
                        startActivity(new Intent(getApplicationContext(), Support.class));
                        break;
                    case R.id.aboutFragment:
                        drawerController.onAboutClick();
                        break;
                }
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
        this.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
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
}