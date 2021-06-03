package teneocto.thiemjason.tlu_connect.utils;

import android.app.ProgressDialog;
import android.content.Context;

import teneocto.thiemjason.tlu_connect.R;

public class CustomProgressDialog {
    ProgressDialog progressDialog;
    public CustomProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.loading_spinner);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent
        );
    }

    public CustomProgressDialog(Context context, String newStyle){
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.loading_spinner_2);
        progressDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent
        );
    }

    public void deleteProgressDialog(){
        progressDialog.dismiss();
    }
}
