package teneocto.thiemjason.tlu_connect.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

import teneocto.thiemjason.tlu_connect.R;

public class Drawer extends AppCompatActivity {
    private static String TAG = "Drawer ";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private DrawerController drawerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        drawerController = new DrawerController(this, this);
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
        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Toolbar toolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.nav_home_icon:
                        Log.i(TAG, " home");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_profile:
                        Log.i(TAG, " profile");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_scanning_history:
                        Log.i(TAG, " scanning history");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_support_icon:
                        Log.i(TAG, " support");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_policies_term:
                        Log.i(TAG, " policies and term");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_about:
                        drawerController.onAboutClick();
                        item.setChecked(false);
                        break;
                }
                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_icon:
                drawerLayout.openDrawer(GravityCompat.START);
        }

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