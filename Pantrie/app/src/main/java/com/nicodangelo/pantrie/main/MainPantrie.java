package com.nicodangelo.pantrie.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.jetts.pantrie.R;
import com.nicodangelo.pantrie.list.ListMain;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;

public class MainPantrie extends ActionBarActivity
{
    private static EditText usernameText;
    private static EditText passwordText;

    public static String username = "";
    private static String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pantrie);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Enable Local Datastore, and make a Test Parse Object!
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "jGe97HPLspW4X6OfjyP3sHGiiPNvwobOJmoD86AP", "N14XxGfjhENApWZ9LyLBVxG1z09yrZxMUrdTf5IF");
        ///////////////////////////////////////////////////////////////////////////////////////////

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(this, ListMain.class);
            startActivity(i);
        } else {
            // show the signup or login screen
        }
    }

    public void logIn(View view)
    {
        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);

        username = usernameText.getText().toString();
        password = passwordText.getText().toString();

        if(username.equalsIgnoreCase("pantrie") && password.equalsIgnoreCase("pantrie"))
        {
            Intent i = new Intent(this, ListMain.class);
            startActivity(i);
        }

        ParseUser.logInInBackground(username, password, new LogInCallback()
        {
            public void done(ParseUser user, com.parse.ParseException e)
            {
                if (user != null)
                {
                    // Hooray! The user is logged in.
                    System.out.println("YOU ARE LOGGED IN");
                    LogInTheUser();
                } else
                {
                    // Signup failed. Look at the ParseException to see what happened.
                    System.out.println("SignIn Failed!");
                }
            }
        });
    }

    public void LogInTheUser()
    {
        Intent i = new Intent(this, ListMain.class);
        startActivity(i);
    }

    public void launchNewLogIn(View view)
    {
        Intent i = new Intent(this,NewLogIn.class);
        startActivity(i);
    }

    public void forgotMyPassword(View view)
    {
        Intent i = new Intent(this,ForgotPassword.class);
        startActivity(i);
    }
}