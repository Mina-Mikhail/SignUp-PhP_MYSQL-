package com.example.minasamirgerges.php_mysql_registration.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minasamirgerges.php_mysql_registration.R;
import com.example.minasamirgerges.php_mysql_registration.Session_Manager.SessionManager;

public class UserProfileActivity extends AppCompatActivity {

    Button LogOut;
    TextView NameShow;
    String EmailHolder;
    SharedPreferences shared2;
    SharedPreferences.Editor editor;
    private SessionManager manager;
    String namefromshared;
    String mailfromshared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initial Session Manager with Application Context
        manager = new SessionManager(getApplicationContext());


        if ( manager.getPrefUserLogInStatus().equals("FALSE") )
        {
            // in case of the user is not logged in
            Intent intent = new Intent(UserProfileActivity.this, UserLoginActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            // in case of the user is logged in
            setContentView(R.layout.activity_user_profile);

            LogOut = (Button)findViewById(R.id.button);
            NameShow = (TextView)findViewById(R.id.NameShow);


            Intent intent = getIntent();
            EmailHolder = intent.getStringExtra(UserLoginActivity.UserEmail);


            // first parameter is key
            // second parameter is default value, if the SharedPreferences did not find the value with key name
            shared2 = getSharedPreferences("user_Data", MODE_PRIVATE);
            String namefromshared = shared2.getString("name", "username");
            String mailfromshared = shared2.getString("mail", "usermail");

            if ( EmailHolder == null )
            {
                if ( shared2.getString("mailFromLogIn", "usermail").equals(shared2.getString(manager.getPrefStoredUserMail(), "usermail")) )
                {
                    NameShow.setText(namefromshared);
                }
                else
                {
                    NameShow.setText( shared2.getString("mailFromLogIn", "usermail") );
                }
            }

            else if ( EmailHolder != null )
            {
                // to edit the data
                SharedPreferences.Editor editor = shared2.edit();
                editor.putString("mailFromLogIn", EmailHolder);
                // use commit to save the data in the file
                editor.apply();
                // if the user registered from the same mobile show his name from SharedPreferences, if not show his mail
                if( EmailHolder.equals(mailfromshared) )
                {
                    NameShow.setText(namefromshared);
                }
                else
                {
                    NameShow.setText( shared2.getString("mailFromLogIn", "username") );
                }
            }

/*

            if( EmailHolder.equals(mailfromshared) )
            {
                NameShow.setText(namefromshared);
            }
            else
            {
                NameShow.setText(EmailHolder);
            }
*/
            LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.setPrefUserLogInStatus("FALSE");

                Intent intent = new Intent(UserProfileActivity.this, UserLoginActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(UserProfileActivity.this, "Log Out Successfully", Toast.LENGTH_LONG).show();
            }
            });
        }
    }
}