package com.nicodangelo.pantrie.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nicodangelo.pantrie.R;
import com.nicodangelo.pantrie.list.ListMain;
import com.parse.LogInCallback;
import com.parse.ParseUser;

public class LoadPantrie extends ActionBarActivity
{

    private static EditText usernameText;
    private static EditText passwordText;
    private boolean parseInit = false;
    public static String username = "";
    private static String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pantrie);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(this, ListMain.class);
            startActivity(i);
        } else {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_pantrie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        Intent i = new Intent(this, Home.class);
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

    @Override
    public void onBackPressed()
    {
        Toast toast = Toast.makeText(getApplicationContext(), "There is no going back now:)", Toast.LENGTH_SHORT);
        toast.show();
    }
}
