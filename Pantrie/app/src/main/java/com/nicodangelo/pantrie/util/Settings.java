package com.nicodangelo.pantrie.util;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nicodangelo.pantrie.R;
import com.nicodangelo.pantrie.database.DBHandler;
import com.nicodangelo.pantrie.game.ArrowGame;
import com.nicodangelo.pantrie.list.ListMain;
import com.nicodangelo.pantrie.main.Home;
import com.nicodangelo.pantrie.main.LogIn;
import com.parse.ParseUser;

public class Settings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    public void launchConversionCalc(View view) {
        Intent i = new Intent(this, ConvertMass.class);
        startActivity(i);
    }

    public void signOut(View view) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        Intent i = new Intent(this, LogIn.class);
        startActivity(i);
    }

    public void startGame(View view) {
        Intent i = new Intent(this, ArrowGame.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Toast toast = Toast.makeText(getApplicationContext(), "There is no going back now:)", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void goBack(View view)
    {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }
    public void deleteDB(View view)
    {
        DBHandler db = new DBHandler(this);
        db.open();
        db.deleteAll();
        db.close();
        Intent i = new Intent(Settings.this, ListMain.class);
        startActivity(i);
    }
}

