package helpers;

import android.content.Context;
import android.location.LocationManager;

public class LocationHelper {

    public static boolean isGPSEnabled(Context mContext)
    {
        LocationManager lm = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
