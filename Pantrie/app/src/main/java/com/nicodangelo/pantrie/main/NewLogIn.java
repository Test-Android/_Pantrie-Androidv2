package com.nicodangelo.pantrie.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.nicodangelo.pantrie.R;
import com.nicodangelo.pantrie.list.ListMain;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class NewLogIn extends ActionBarActivity
{
    private static EditText newUsernameText;
    private static EditText newEmailText;
    private static EditText newPasswordText;
    private static EditText newConfirmPasswordText;

    public static String newUsername;
    private static String newEmail;
    private static String newPassword;
    private static String newConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log_in);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New User");
    }

    public void createNewAccount(View view)
    {
        newUsernameText = (EditText) findViewById(R.id.newUsernameText);
        newEmailText = (EditText) findViewById(R.id.newEmailText);
        newPasswordText = (EditText) findViewById(R.id.newPasswordText);
        newConfirmPasswordText = (EditText) findViewById(R.id.newConfirmPasswordText);

        newUsername = newUsernameText.getText().toString();
        newEmail = newEmailText.getText().toString();
        newPassword = newPasswordText.getText().toString();
        newConfirmPassword = newConfirmPasswordText.getText().toString();

        if(newPassword.equalsIgnoreCase(newConfirmPassword))
        {
            ParseUser user = new ParseUser();
            if(!newUsername.isEmpty())
            {
                user.setUsername(newUsername);
                user.setEmail(newEmail);
                user.setPassword(newPassword);
            }

            // other fields can be set just like with ParseObject

            user.signUpInBackground(new SignUpCallback()
            {
                @Override
                public void done(com.parse.ParseException e)
                {
                    if (e == null)
                    {
                        // Hooray! Let them use the app now.
                        newUserCreated();

                    } else
                    {
                        // Sign up didn't succeed. Look at the ParseException
                        System.out.println("the sign in failed!!");
                        // to figure out what went wrong
                    }
                }
            });
        }
        else
        {
            System.out.println("password don't match");
            newUserCreated();
        }

    }

    public void newUserCreated()
    {
        Intent i = new Intent(this, ListMain.class);
        startActivity(i);
    }
}
