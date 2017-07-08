package com.example.minasamirgerges.php_mysql_registration.Session_Manager;

/**
 * Created by Mina Samir Gerges on 5/26/2017.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    private SharedPreferences pref;
    private Editor editor;
    private Context context;

    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "user_Data";
    private static final String PREF_STORED_USER_NAME = "user_Name";
    private static final String PREF_STORED_USER_PASS = "user_Pass";
    private static final String PREF_STORED_USER_MAIL = "user_Mail";

    private static String PREF_USER_LOG_IN_STATUS = "FALSE";


    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public static String getPrefStoredUserName() {
        return PREF_STORED_USER_NAME;
    }

    public static String getPrefStoredUserPass() {
        return PREF_STORED_USER_PASS;
    }

    public static String getPrefStoredUserMail() {
        return PREF_STORED_USER_MAIL;
    }

    public static String getPrefName() {
        return PREF_NAME;
    }

    public void setPrefUserLogInStatus(String prefUserLogInStatus) {
        editor.putString(PREF_USER_LOG_IN_STATUS, prefUserLogInStatus);
        editor.apply();
    }

    public String getPrefUserLogInStatus() {
        return pref.getString(PREF_USER_LOG_IN_STATUS, "FALSE");
    }
}