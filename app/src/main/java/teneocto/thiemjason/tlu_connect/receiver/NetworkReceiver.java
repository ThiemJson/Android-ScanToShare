package teneocto.thiemjason.tlu_connect.receiver;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import teneocto.thiemjason.tlu_connect.R;

/**
 * NETWORK RECEIVER
 */
public class NetworkReceiver extends BroadcastReceiver {
    private static String TAG = "BroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (this.isInternetConnected(context)) {
            Toast.makeText(context, "Internet connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Internet disconnected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Check Internet connection
     * @param context Context
     * @return Boolean
     */
    private boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) {
                return false;
            }
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);

            return networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } else {
            NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return ((wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected()));
        }
    }
}
