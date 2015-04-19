package com.nicodangelo.pantrie.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

import com.nicodangelo.pantrie.R;
import com.nicodangelo.pantrie.list.ListMain;
import com.nicodangelo.pantrie.util.Settings;
import com.parse.ParseUser;

public class Home extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void logOut(View view)
    {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        Intent i = new Intent(this, LogIn.class);
        startActivity(i);
    }

    public void goToSettings(View view)
    {
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    public void goToPantrie(View view)
    {
        Intent i = new Intent(this, ListMain.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
    Toast toast = Toast.makeText(getApplicationContext(), "There is no going back now:)", Toast.LENGTH_SHORT);
    toast.show();
    }
}
