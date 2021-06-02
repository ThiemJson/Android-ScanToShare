package teneocto.thiemjason.tlu_connect.ui.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.support.Support;
import teneocto.thiemjason.tlu_connect.utils.AppConst;

public class NotificationWebView extends AppCompatActivity {
    private WebView mWebView;
    private Button mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_web_view);

        mWebView = findViewById(R.id.noti_web_view);
        mBackBtn = findViewById(R.id.noti_menu_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webViewInitial();
    }

    private void webViewInitial() {
        Bundle bundle = getIntent().getExtras();
        String notificationURL = bundle.getString("URL");
        Log.i(AppConst.TAG_Notification_web_view, notificationURL);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new Callback());
        mWebView.loadUrl(notificationURL);

        mBackBtn = findViewById(R.id.noti_menu_back);
        mBackBtn.setOnClickListener(v -> finish());
    }

    public static class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }
    }
}