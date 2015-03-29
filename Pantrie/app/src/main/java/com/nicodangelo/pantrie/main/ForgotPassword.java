package com.nicodangelo.pantrie.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jetts.pantrie.R;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgotPassword extends ActionBarActivity
{
    //I LIKE PANDAS
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Forgot Password");
    }

    public void resetPassword(View view)
    {
        EditText emailForPasswordReset = (EditText) findViewById(R.id.emailPasswordReset);
        String email = "nope";
        email = emailForPasswordReset.getText().toString();

        if(email.equalsIgnoreCase("nope"))
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Please Enter an Email", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        ParseUser.requestPasswordResetInBackground(email,
                new RequestPasswordResetCallback()
                {
                    @Override
                    public void done(com.parse.ParseException e)
                    {
                        if (e == null)
                        {
                            // An email was successfully sent with reset instructions.
                        } else
                        {
                            // Something went wrong. Look at the ParseException to see what's up.
                        }
                    }

                });

        Toast toast = Toast.makeText(getApplicationContext(), "Password Reset Email Sent", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void backToLogIn(View view)
    {
        Intent i = new Intent(this,ListMain.class);
        startActivity(i);
    }
}
