package teneocto.thiemjason.tlu_connect.about;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import teneocto.thiemjason.tlu_connect.R;

public class AboutDialog implements View.OnClickListener {
    Button closeButton;
    Button viewLicenses;
    TextView viewDev;
    Dialog dialog;
    Context context;

    public void showDialog(Activity activity) {
        context = activity;
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.about_dialog);

        closeButton = dialog.findViewById(R.id.about_view_close);
        viewLicenses = dialog.findViewById(R.id.about_view_licenses);
        viewDev = dialog.findViewById(R.id.about_view_dev);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_view_close:
                dialog.dismiss();
                break;
            case R.id.about_view_dev:
                Toast.makeText(context, "THIEM JASON", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_view_licenses:
                Toast.makeText(context, "VIEW LICENSES", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
