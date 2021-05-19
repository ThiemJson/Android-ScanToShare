package teneocto.thiemjason.tlu_connect.scanninghistory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import teneocto.thiemjason.tlu_connect.R;

public class ScanningHistory extends AppCompatActivity {
    Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_history);

        this.initView();
    }

    void initView() {
        mBackButton = findViewById(R.id.scanning_menu_icon);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}