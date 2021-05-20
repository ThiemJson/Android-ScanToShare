package teneocto.thiemjason.tlu_connect.ui.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import teneocto.thiemjason.tlu_connect.R;

public class RegisterProfile extends AppCompatActivity {
    ImageView mImagePicker;
    int SELECT_PHOTO = 1;
    Uri uri;

    // Buttons
    Button mBackButton;
    Button mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_your_profile);
        this.initActivity();

        mBackButton = findViewById(R.id.register_profile_menu_icon);
        mNext = findViewById(R.id.register_btn_main_ac_skip);
        mBackButton.setOnClickListener(v -> backButton());
        mNext.setOnClickListener(v -> nextButton());
    }

    void initActivity() {
        mImagePicker = findViewById(R.id.register_profile_image);
        mImagePicker.setOnClickListener(v -> imagePicker());
    }

    /**
     * Image Picker
     */
    void imagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && data != null && data.getData() != null) {
            uri = data.getData();
            mImagePicker.setImageURI(uri);

        }
    }

    void backButton(){
        finish();
    }

    void nextButton(){
        Intent intent = new Intent(this, RegisterSocialNetwork.class);
        startActivity(intent);
    }
}