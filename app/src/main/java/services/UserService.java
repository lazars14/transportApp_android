package services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.transportapp.lazar.transportapp.R;

import model.User;

public class UserService {

    public User login(String email, String password) {
//        to do
//        store user in shared preferences

//        if successfull fillUserData
        return null;
    }

    public User register(String email, String password, String firstName, String lastName, String address) {
//        get phone number of user
//        generate firebase token and bind it to user

//        if successfull fillUserData

        return null;
    }

    public boolean updateInfo(User user) {
//        to do
        return true;
    }

    public void logout(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_id", "default_id");
        editor.putString("user_email", "default_email");
        editor.putString("user_firstName", "default_firstName");
        editor.putString("user_lastName", "default_lastName");
        editor.putString("user_address", "default_address");
        editor.putString("user_firebaseToken", "default_firebaseToken");

        editor.putString("user_authToken", context.getString(R.string.default_authToken));

        editor.commit();
    }

    private void fillUserData(Context context, User loggedUser, String authToken) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_id", loggedUser.getId());
        editor.putString("user_email", loggedUser.getEmail());
        editor.putString("user_firstName", loggedUser.getFirstName());
        editor.putString("user_lastName", loggedUser.getLastName());
        editor.putString("user_address", loggedUser.getAddress());
        editor.putString("user_firebaseToken", loggedUser.getFirebaseToken());

        editor.putString("user_authToken", authToken);

        editor.commit();
    }

}
