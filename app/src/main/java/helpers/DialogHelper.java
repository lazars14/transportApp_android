package helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.WindowManager;

import com.transportapp.lazar.transportapp.activities.AddRequestActivity;

public class DialogHelper {

    public static void showDialogInfo(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
