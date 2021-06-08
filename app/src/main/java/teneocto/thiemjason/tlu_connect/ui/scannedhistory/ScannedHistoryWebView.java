package teneocto.thiemjason.tlu_connect.ui.scannedhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.notification.NotificationWebView;
import teneocto.thiemjason.tlu_connect.utils.AppConst;

public class ScannedHistoryWebView extends AppCompatActivity {

    private WebView mWebView;

    private Button mBackBtn;

    private ProgressBar mLoadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_history_web_view);

        mWebView = findViewById(R.id.scanned_web_view);
        mBackBtn = findViewById(R.id.scanned_webview_menu_back);
        mLoadingSpinner = findViewById(R.id.scanned_web_view_loading_spinner);
        mBackBtn.setOnClickListener(v -> finish());

        webViewInitial();
    }

    /**
     * Initial WebView
     */
    private void webViewInitial() {
        Bundle bundle = getIntent().getExtras();
        String userURL = bundle.getString(AppConst.SCANNED_userUrl);
        Log.i(AppConst.TAG_ScannedWebView, userURL);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoadingSpinner.setVisibility(View.GONE);
            }
        });
        mWebView.loadUrl(userURL);
    }
}