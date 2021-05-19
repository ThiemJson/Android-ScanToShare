package teneocto.thiemjason.tlu_connect.ui.drawer;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.about.AboutDialog;

public class DrawerController {
    Activity activity;
    Context context;

    public DrawerController(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void onAboutClick() {
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.showDialog(activity);
    }

    public void onSupportClick() {
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.showDialog(activity);
    }

    private void initialWebView() {
        WebView webView = activity.findViewById(R.id.support_web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new DrawerController.Callback());
        webView.loadUrl("https://facebook.com/thiemtinhte");
    }

    public static class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }
    }
}
