package com.example.minasamirgerges.php_mysql_registration.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minasamirgerges.php_mysql_registration.HttpParse;
import com.example.minasamirgerges.php_mysql_registration.R;
import com.example.minasamirgerges.php_mysql_registration.Session_Manager.SessionManager;

import java.util.HashMap;

public class UserLoginActivity extends AppCompatActivity {

    EditText Email, Password;
    Button LogIn ;
    Button NotMember ;
    String PasswordHolder, EmailHolder;
    String finalResult ;
    String HttpURL = "http://YOUR_HOST/android-login.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String UserEmail = "";
    SharedPreferences shared2;
    SharedPreferences.Editor editor;
    private SessionManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initial Session Manager with Application Context
        manager = new SessionManager(getApplicationContext());

        if ( manager.getPrefUserLogInStatus().equals("TRUE") )
        {
            // in case of the user is logged in
            Intent intent = new Intent(UserLoginActivity.this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            // in case of the user is not logged in
            setContentView(R.layout.activity_user_login);

            Email = (EditText) findViewById(R.id.email);
            Password = (EditText) findViewById(R.id.password);
            LogIn = (Button) findViewById(R.id.Login);

            LogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CheckEditTextIsEmptyOrNot();

                    if (CheckEditText) {
                        UserLoginFunction(EmailHolder, PasswordHolder);
                    } else {
                        Toast.makeText(UserLoginActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                    }
                }
            });

            NotMember = (Button) findViewById(R.id.not_member);

            NotMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(UserLoginActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                }
            });
        }
/*

            // first parameter is key
            // second parameter is default value, if the SharedPreferences did not find the value with key name
            shared2 = getSharedPreferences(manager.getPrefName(), MODE_PRIVATE);
            editor = shared2.edit();
            String mailfromshared = shared2.getString(manager.getPrefStoredUserMail(), null);
            String passfromshared = shared2.getString(manager.getPrefStoredUserPass(), null);

            Email.setText(mailfromshared);
            Password.setText(passfromshared);
*/
    }


    public void CheckEditTextIsEmptyOrNot(){
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder ))
        {
            CheckEditText = false;
        }
        else {
            CheckEditText = true ;
        }
    }

    public void UserLoginFunction(final String email, final String password){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(UserLoginActivity.this,"Loading Data, PLease wait",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if( httpResponseMsg.equalsIgnoreCase("Data Matched") ){

                    manager.setPrefUserLogInStatus("TRUE");

                    Intent intent = new Intent(UserLoginActivity.this, UserProfileActivity.class);
                    intent.putExtra(UserEmail, EmailHolder);
                    startActivity(intent);
                    finish();

                    Toast.makeText(UserLoginActivity.this,"Log In Successfully",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(UserLoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("email",params[0]);

                hashMap.put("password",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute(email, password);
    }

}