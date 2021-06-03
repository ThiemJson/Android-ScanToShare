package teneocto.thiemjason.tlu_connect.ui.main;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.firebase.FirebaseDBHelper;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.ui.drawer.Drawer;
import teneocto.thiemjason.tlu_connect.ui.uimodels.UIMainSliderItemDTO;
import teneocto.thiemjason.tlu_connect.ui.register.RegisterProfile;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.CustomProgressDialog;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class MainActivity extends AppCompatActivity {
    ViewPager2 mViewPager;
    Handler mSliderHandler = new Handler();
    AdView adView;

    // Button
    Button mSkip;

    // Facebook Authentication
    LoginButton loginButton;
    CallbackManager mCallbackManager;
    FirebaseAuth mAuth;

    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAd();
        initSlider();

        loginButton = findViewById(R.id.btn_main_ac_register_facebook);
        mSkip = findViewById(R.id.btn_main_ac_skip);
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> connectFacebook());
        mSkip.setOnClickListener(v -> skip());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSliderHandler.removeCallbacks(sliderRunnable);
    }

    public void initSlider() {
        mViewPager = findViewById(R.id.main_slider_container);

        ArrayList<UIMainSliderItemDTO> UIMainSliderItemDTOS = new ArrayList<>();
        UIMainSliderItemDTOS.add(new UIMainSliderItemDTO(R.drawable.facebook, "Add your Social Network link", "Add social network you arr using and want to share with another"));
        UIMainSliderItemDTOS.add(new UIMainSliderItemDTO(R.drawable.instagram, "Scan another profile and share your own", "Scan another user's QR code to quickly retrieve the necessary information"));
        UIMainSliderItemDTOS.add(new UIMainSliderItemDTO(R.drawable.twiiter, "Create your Profile", "Set up your basic personal information to share with another"));
        UIMainSliderItemDTOS.add(new UIMainSliderItemDTO(R.drawable.facebook, "Add your Social Network link", "Add social network you arr using and want to share with another"));
        UIMainSliderItemDTOS.add(new UIMainSliderItemDTO(R.drawable.instagram, "Scan another profile and share your own", "Scan another user's QR code to quickly retrieve the necessary information"));
        UIMainSliderItemDTOS.add(new UIMainSliderItemDTO(R.drawable.twiiter, "Create your Profile", "Set up your basic personal information to share with another"));

        mViewPager.setAdapter(new MainSliderAdapter(UIMainSliderItemDTOS, mViewPager));

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

    /**
     * FACEBOOK Authentication
     */
    void connectFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setPermissions("user_friends");
        loginButton.setPermissions("public_profile");
        loginButton.setPermissions("email");
        loginButton.setPermissions("user_birthday");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Show loading spinner
                mProgressDialog = new CustomProgressDialog(MainActivity.this);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(AppConst.TAG_MainActivity, "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(AppConst.TAG_MainActivity, "facebook:onError", error);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        try {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        catch (Exception e){
            Toast.makeText(this, "Facebook login error", Toast.LENGTH_SHORT).show();
            Log.d(AppConst.TAG_MainActivity, "Facebook login error", e);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(AppConst.TAG_MainActivity, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(AppConst.TAG_MainActivity, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        Log.i(AppConst.TAG_MainActivity, "handleFacebookAccessToken:" + token);
                        Log.i(AppConst.TAG_MainActivity, "current user uid:" + mAuth.getCurrentUser().getUid());
                        Log.i(AppConst.TAG_MainActivity, "current user email:" + mAuth.getCurrentUser().getEmail());
                        Log.i(AppConst.TAG_MainActivity, "current user display name:" + mAuth.getCurrentUser().getDisplayName());
                        Log.i(AppConst.TAG_MainActivity, "current user image url:" + mAuth.getCurrentUser().getPhotoUrl());
                        registerWithFacebook(token);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(AppConst.TAG_MainActivity, "signInWithCredential:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication with Facebook failure", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void registerWithFacebook(AccessToken token) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(mAuth.getCurrentUser().getEmail());
        userDTO.setLastName("");
        userDTO.setFirstName(mAuth.getCurrentUser().getDisplayName());
        userDTO.setFirebaseId(mAuth.getCurrentUser().getUid());
        userDTO.setId(Utils.getRandomUUID());

        // Store
        Utils.setPrefer(this, AppConst.FB_accessToken, token.getToken());

        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large)");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {
                    URL newURL = new URL(mAuth.getCurrentUser().getPhotoUrl().toString() + "?width=500&height=500");
                    Bitmap profilePic = BitmapFactory.decodeStream(newURL.openConnection().getInputStream());
                    userDTO.setImageBase64(Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(profilePic)));

                    Utils.registerUserDTO = userDTO;
                    AppConst.USER_UID_Static = userDTO.getId();
                    Utils.setPrefer(getApplicationContext(), AppConst.USER_UID, userDTO.getId());

                    // Sync data into firebase
                    FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
                    firebaseDBHelper.USER_Insert(Utils.registerUserDTO);

                    Intent intent = new Intent(getApplicationContext(), Drawer.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Check if use have facebook account
     * @param userID
     */
    private void isHaveFacebookAccount(String userID){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null){
            mProgressDialog.deleteProgressDialog();
        }
    }
}