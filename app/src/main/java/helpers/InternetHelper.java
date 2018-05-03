package helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.transportapp.lazar.transportapp.R;

public class InternetHelper {

    public static boolean internet;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void checkIfConnected(Context context) {
        if(isNetworkAvailable(context)) {
            internet = true;
        } else {
            internet = false;
            Toast.makeText(context, R.string.turn_on_internet, Toast.LENGTH_LONG).show();
        }
    }

}
