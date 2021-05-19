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

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import teneocto.thiemjason.tlu_connect.R;

public class Drawer extends AppCompatActivity {
    private static String TAG = "Drawer ";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private DrawerController drawerController;
    private NavController navController;

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
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(navigationView, navController);
        openDrawer.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        final TextView navAppTile = findViewById(R.id.nav_app_title);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getLabel() == "Home"){
                navAppTile.setText("TLU Connect");
                return;
            }
            navAppTile.setText(destination.getLabel());
        });
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