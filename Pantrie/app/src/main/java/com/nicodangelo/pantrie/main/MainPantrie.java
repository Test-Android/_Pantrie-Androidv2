package com.nicodangelo.pantrie.main;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.nicodangelo.pantrie.R;
import com.parse.Parse;

public class MainPantrie extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pantrie);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ///////////////////////////////////////////////////////////////////////////////////////////
        // Enable Local Datastore, and make a Test Parse Object!
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "jGe97HPLspW4X6OfjyP3sHGiiPNvwobOJmoD86AP", "N14XxGfjhENApWZ9LyLBVxG1z09yrZxMUrdTf5IF");
        System.out.println("INITIALIZED");
        ///////////////////////////////////////////////////////////////////////////////////////////

        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                Intent i = new Intent(MainPantrie.this, LogIn.class);
                startActivity(i);
            }
        };

        Thread theThread = new Thread(r);
        theThread.start();
    }
    @Override
    public void onBackPressed(){}

    /*
        Toast toast = Toast.makeText(getApplicationContext(), "There is no going back now:)", Toast.LENGTH_SHORT);
        toast.show();
     */
}