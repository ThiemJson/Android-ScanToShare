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
}
