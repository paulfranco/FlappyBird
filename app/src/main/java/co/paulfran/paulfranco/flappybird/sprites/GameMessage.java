package co.paulfran.paulfranco.flappybird.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import co.paulfran.paulfranco.flappybird.R;

public class GameMessage implements Sprite {
    private int screenHeight, screenWidth;
    private Bitmap message;

    public GameMessage(Resources resources, int screenHeight, int screenWidth){

        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        message = BitmapFactory.decodeResource(resources, R.drawable.message);

    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(message, screenWidth / 2 - message.getWidth() / 2, screenHeight / 4, null);

    }

    @Override
    public void update() {

    }
}
