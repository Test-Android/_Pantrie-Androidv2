package com.nicodangelo.pantrie.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Jetts on 3/29/2015.
 */
public class Sprite
{
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private static final int MAX_SPEED = 5;
    private CustomView customView;
    private Bitmap b;
    private int x = 0;
    private int y = 0;
    private int moveToX = 0;
    private int moveToY = 0;
    private int xSpeed;
    private int ySpeed;
    private int currentFrame = 0;
    private int width;
    private int height;


    public Sprite(CustomView customView, Bitmap b)
    {
        this.customView = customView;
        this.b = b;
        this.width = b.getWidth() / BMP_COLUMNS;
        this.height = b.getHeight() / BMP_ROWS;

        Random rnd = new Random();
        x = rnd.nextInt(customView.getWidth() - width);
        y = rnd.nextInt(customView.getHeight() - height);
        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;

    }
    public void moveTo(float x2, float y2)
    {
        moveToX = (int)x2;
        moveToY = (int)y2;
    }
    private void update()
    {

        if (x > customView.getWidth() - width - xSpeed || x + xSpeed < 0) {
            xSpeed = -xSpeed;
        }
        x = x + xSpeed;
        if (y > customView.getHeight() - height - ySpeed || y + ySpeed < 0) {
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }
    public void onDraw(Canvas canvas)
    {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(b,src,dst,null);
    }

    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    private int getAnimationRow()
    {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }
    public boolean isCollison(float x2, float y2)
    {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }

}
