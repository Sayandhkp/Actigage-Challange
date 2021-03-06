package saykp.dev.actigagechallange.other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by SAYANDH KP (sayandhkp59@gmail.com) on 15-03-2018.
 */

public class Utilities {
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
