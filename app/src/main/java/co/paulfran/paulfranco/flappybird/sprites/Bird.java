package co.paulfran.paulfranco.flappybird.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import co.paulfran.paulfranco.flappybird.R;

public class Bird implements Sprite {

    private Bitmap bird_down;
    private Bitmap bird_up;
    private int birdWidth;
    private int birdHeight;
    private int birdX, birdY;
    private float gravity;
    private float currentFallingSpeed;
    private float flappyBoost;


    public Bird(Resources resources) {
        birdX = (int) resources.getDimension(R.dimen.bird_x);
        birdWidth = (int) resources.getDimension(R.dimen.bird_width);
        birdHeight = (int) resources.getDimension(R.dimen.bird_height);
        gravity = (int) resources.getDimension(R.dimen.gravity);
        flappyBoost = (int) resources.getDimension(R.dimen.flappy_boost);

        Bitmap birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird_down);
        bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false);
        Bitmap birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird_up);
        bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false);
    }

    @Override
    public void draw(Canvas canvas) {
        if (currentFallingSpeed < 0) {
            canvas.drawBitmap(bird_down, birdX, birdY, null);
        } else {
            canvas.drawBitmap(bird_up, birdX, birdY, null);
        }
    }


    @Override
    public void update() {
        birdY += currentFallingSpeed;
        currentFallingSpeed += gravity;
    }

    public void onTouchEvent() {
        currentFallingSpeed = flappyBoost;
    }
}
