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

public class RegistrationActivity extends AppCompatActivity {

    Button register, log_in;
    EditText Name, Email, Password ;
    String Name_Holder, EmailHolder, PasswordHolder;
    String finalResult ;
    String HttpURL = "http://YOUR_HOST/insert-registration-data.php";
    Boolean CheckEditText ;
    Boolean MailCheckr = false ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    private SessionManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //initial Session Manager with Application Context
        manager = new SessionManager(getApplicationContext());


        Name = (EditText)findViewById(R.id.editTextName);
        Email = (EditText)findViewById(R.id.editTextEmail);
        Password = (EditText)findViewById(R.id.editTextPassword);

        register = (Button)findViewById(R.id.Submit);
        log_in = (Button)findViewById(R.id.Login);

        //Adding Click Listener on button.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    // If EditText is not empty and CheckEditText = True then this block will execute
                    UserRegisterFunction(Name_Holder, EmailHolder, PasswordHolder);

                }
                else {
                    // If EditText is empty then this block will execute
                    Toast.makeText(RegistrationActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this,UserLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void CheckEditTextIsEmptyOrNot(){
        Name_Holder = Name.getText().toString();
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        if( TextUtils.isEmpty(Name_Holder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder) )
        {
            CheckEditText = false;
        }
        else
        {
            if ( EmailHolder.endsWith("@gmail.com") || EmailHolder.endsWith("@hotmail.com") ||EmailHolder.endsWith("@yahoo.com") || EmailHolder.endsWith("@outlook.com") )
            {
                CheckEditText = true;
            }
            else
            {
                Email.setError("E-mail domain must be @gmail, @yahoo, @outlook, @hotmail");
                Email.requestFocus();

                CheckEditText = false;
                MailCheckr = true;
            }
        }
    }

    public void UserRegisterFunction(final String F_Name, final String email, final String password){
        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(RegistrationActivity.this,"Submitting Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(RegistrationActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                if ( (httpResponseMsg.toString()).equals("Data Inserted Successfully") )
                {
                    Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("name",params[0]);

                hashMap.put("email",params[1]);

                hashMap.put("password",params[2]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);


                // we use private mode, because i did not want any other application see the data
                SharedPreferences shared1 = getSharedPreferences(manager.getPrefName(), MODE_PRIVATE);
                // to edit the data
                SharedPreferences.Editor editor = shared1.edit();
                // add data
                // first parameter is key
                // second parameter is value
                editor.putString(manager.getPrefStoredUserName(), Name_Holder);
                editor.putString(manager.getPrefStoredUserPass(), PasswordHolder);
                editor.putString(manager.getPrefStoredUserMail(), EmailHolder);
                // use commit to save the data in the file
                editor.apply();

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(F_Name,email,password);
    }

}