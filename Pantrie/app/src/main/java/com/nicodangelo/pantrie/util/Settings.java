package com.nicodangelo.pantrie.util;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nicodangelo.pantrie.R;
import com.nicodangelo.pantrie.game.ArrowGame;
import com.nicodangelo.pantrie.main.LoadPantrie;
import com.nicodangelo.pantrie.main.MainPantrie;
import com.parse.ParseUser;

public class Settings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    public void launchConversionCalc(View view)
    {
        Intent i = new Intent(this, ConvertMass.class);
        startActivity(i);
    }

    public void signOut(View view)
    {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        Intent i = new Intent(this, LoadPantrie.class);
        startActivity(i);
    }
    public void startGame(View view)
    {
        Intent i = new Intent(this, ArrowGame.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
        Toast toast = Toast.makeText(getApplicationContext(), "There is no going back now:)", Toast.LENGTH_SHORT);
        toast.show();
    }
}

