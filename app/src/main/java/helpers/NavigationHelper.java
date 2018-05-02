package helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class NavigationHelper {

    private Context appContext;

    public NavigationHelper(Context appContext) {
        this.appContext = appContext;
    }

    public void navigateTo(Class activity, AppCompatActivity currentActivity){
        Intent intent = new Intent(appContext, activity);
        currentActivity.startActivity(intent);
    }

}
