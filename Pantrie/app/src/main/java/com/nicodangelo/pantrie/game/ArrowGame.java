package com.nicodangelo.pantrie.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nicodangelo.pantrie.R;

public class ArrowGame extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new CustomView(this));
    }

}
