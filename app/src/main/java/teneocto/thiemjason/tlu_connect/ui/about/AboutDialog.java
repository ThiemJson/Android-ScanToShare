package teneocto.thiemjason.tlu_connect.ui.about;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import teneocto.thiemjason.tlu_connect.R;

public class AboutDialog implements View.OnClickListener {
    Button mCloseButton;
    Button mViewLicenses;
    TextView mViewDev;
    Dialog mDialog;
    Context context;

    public void showDialog(Activity activity) {
        context = activity;
        mDialog = new Dialog(activity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.about_dialog);

        mCloseButton = mDialog.findViewById(R.id.about_view_close);
        mViewLicenses = mDialog.findViewById(R.id.about_view_licenses);
        mViewDev = mDialog.findViewById(R.id.about_view_dev);
        mDialog.show();

        mCloseButton.setOnClickListener(v -> {
            Log.i("AboutDialog", " Close");
            mDialog.dismiss();
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_view_close:
                Log.i("AboutDialog", " Close");
                mDialog.dismiss();
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
