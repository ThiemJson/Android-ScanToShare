package teneocto.thiemjason.tlu_connect.navigation;

import android.app.Activity;
import android.content.Context;

import teneocto.thiemjason.tlu_connect.about.AboutDialog;

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
