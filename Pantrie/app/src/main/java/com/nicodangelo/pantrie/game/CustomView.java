package com.nicodangelo.pantrie.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.nicodangelo.pantrie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jetts on 3/29/2015.
 */
public class CustomView extends SurfaceView
{
    private SurfaceHolder holder;
    private GameThread gameThread;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    private long lastClick;
    private Bitmap blood;

    public CustomView(Context context)
    {
        super(context);
        gameThread = new GameThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder)
            {
                createSprites();
                gameThread.setRunning(true);
                gameThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) { }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder)
            {
                boolean retry = true;
                gameThread.setRunning(false);
                while(retry)
                {
                    try
                    {
                        gameThread.join();
                        retry = false;
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        blood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
    }
    private void createSprites()
    {
        for(int k = 0; k < 20; k++)
            sprites.add(createSprite(R.drawable.player));

    }

    private Sprite createSprite(int resouce)
    {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this, bmp);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK);

        for (int i = temps.size() - 1; i >= 0; i--)
        {
            temps.get(i).onDraw(canvas);
        }
        for(Sprite sprite : sprites)
            sprite.onDraw(canvas);
    }
    public boolean onTouchEvent(MotionEvent event)
    {
        if (System.currentTimeMillis() - lastClick > 300) {
            lastClick = System.currentTimeMillis();
            float x = event.getX();
            float y = event.getY();
            synchronized (getHolder()) {
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
                    if (sprite.isCollison(x, y)) {
                        sprites.remove(sprite);
                        temps.add(new TempSprite(temps, this, x, y, blood));
                        break;
                    }
                }
            }
        }
        return true;
    }
}
