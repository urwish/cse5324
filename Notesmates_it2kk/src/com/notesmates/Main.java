package com.notesmates;


import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.notesmates.library.DatabaseHandler;
import com.notesmates.library.UserFunctions;

public class Main extends Activity {
    Button btnLogout;
    Button changepas;
    Button btnEnterPro;
    Button btnSeledtClass;
    




    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        changepas = (Button) findViewById(R.id.btchangepass);
        btnLogout = (Button) findViewById(R.id.logout);
        btnEnterPro = (Button)  findViewById(R.id.enterprofile);
        btnSeledtClass = (Button) findViewById(R.id.seledtcourse);
        
        
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        /**
         * Hashmap to load data from the Sqlite database
         **/
         HashMap<String,String> user = new HashMap<String, String>();
         user = db.getUserDetails();


        /**
         * Change Password Activity Started
         **/
        changepas.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){

                Intent chgpass = new Intent(getApplicationContext(), ChangePassword.class);

                startActivity(chgpass);
            }

        });

       /**
        *LogoutFragment from the User Panel which clears the data in Sqlite database
        **/
        btnLogout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                UserFunctions logout = new UserFunctions();
                logout.logoutUser(getApplicationContext());
                Intent login = new Intent(getApplicationContext(), Login.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                finish();
            }
        });
        
        /**
         *Enter profile from the Main page which directs user to main profile .
         **/
        btnEnterPro.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent mainprofile = new Intent(getApplicationContext(), MainProfile.class);
                mainprofile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainprofile);
                finish();
            }
        });
        
        /**
         *Enter profile from the Main page which directs user to main profile .
         **/
        btnSeledtClass.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent seledtcourse = new Intent(getApplicationContext(), Select_courses.class);
                seledtcourse.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(seledtcourse);
                finish();
            }
        });

/**
 * Sets user first name and last name in text view.
 **/
        final TextView login = (TextView) findViewById(R.id.textwelcome);
        login.setText("Welcome  "+user.get("fname"));
        final TextView lname = (TextView) findViewById(R.id.lname);
        lname.setText(user.get("lname"));


    }}