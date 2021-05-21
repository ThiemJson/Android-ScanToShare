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
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import teneocto.thiemjason.tlu_connect.ui.main.MainSliderAdapter;
import teneocto.thiemjason.tlu_connect.ui.models.MainSliderItem;
import teneocto.thiemjason.tlu_connect.ui.models.User;
import teneocto.thiemjason.tlu_connect.ui.register.RegisterProfile;
import teneocto.thiemjason.tlu_connect.utils.AppConst;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "Main activity";
    ViewPager2 mViewPager;
    Handler mSliderHandler = new Handler();
    AdView adView;

    // Button
    Button mSkip;
    CallbackManager callbackManager;
    LoginButton mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setUpPermission();
        loadAd();
        initSlider();
        initFacebookLogin();

        mSkip = findViewById(R.id.btn_main_ac_skip);
        mSkip.setOnClickListener(v -> skip());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSliderHandler.removeCallbacks(sliderRunnable);
    }

    private void initFacebookLogin() {
        mLoginButton = findViewById(R.id.facebook_login_button);
        callbackManager = CallbackManager.Factory.create();
        mLoginButton.setPermissions(Arrays.asList("user_gender", "user_friends", "email", "user_status", "user_location"));
        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "Login Facebook successfully");
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "Login Facebook cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i(TAG, "Login Facebook failure");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
            Log.i(TAG, object.toString());
            try {
                String name = object.getString("name");
                String id = object.getString("id");
                Picasso.get().load("https://graph.facebook.com/" + id + "/picture?type=large").into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.i(TAG, "Bitmap: " + bitmap);

                        Gson gson = new Gson();
                        User user;
                        try {
                            user = new User(object.getString("first_name"),
                                    object.getString("last_name"),
                                    AppConst.BitMapToString(bitmap),
                                    object.getString("location"),
                                    object.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            user = null;
                        }

                        String dataJson = gson.toJson(user);
                        Log.i(TAG, "DataJson: " + dataJson);
                        SharedPreferences sharedPreferences = getSharedPreferences(AppConst.mainSharedPrefer, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USERDATA", dataJson);
                        editor.apply();
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("fields", "gender ,email, name,id,first_name, last_name, location");

        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                LoginManager.getInstance().logOut();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    public void initSlider() {
        mViewPager = findViewById(R.id.main_slider_container);

        ArrayList<MainSliderItem> mainSliderItems = new ArrayList<>();
        mainSliderItems.add(new MainSliderItem(R.drawable.facebook, "Add your Social Network link", "Add social network you arr using and want to share with another"));
        mainSliderItems.add(new MainSliderItem(R.drawable.instagram, "Scan another profile and share your own", "Scan another user's QR code to quickly retrieve the necessary information"));
        mainSliderItems.add(new MainSliderItem(R.drawable.twiiter, "Create your Profile", "Set up your basic personal information to share with another"));
        mainSliderItems.add(new MainSliderItem(R.drawable.facebook, "Add your Social Network link", "Add social network you arr using and want to share with another"));
        mainSliderItems.add(new MainSliderItem(R.drawable.instagram, "Scan another profile and share your own", "Scan another user's QR code to quickly retrieve the necessary information"));
        mainSliderItems.add(new MainSliderItem(R.drawable.twiiter, "Create your Profile", "Set up your basic personal information to share with another"));

        mViewPager.setAdapter(new MainSliderAdapter(mainSliderItems, mViewPager));

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

    void skip() {
        Intent intent = new Intent(this, RegisterProfile.class);
        startActivity(intent);
    }
}