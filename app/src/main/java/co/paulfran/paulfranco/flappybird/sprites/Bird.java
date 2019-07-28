package co.paulfran.paulfranco.flappybird.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import co.paulfran.paulfranco.flappybird.GameManagerCallback;
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
    private boolean collision = false;
    private int screenHeight;
    private GameManagerCallback callback;

    public Bird(Resources resources, int screenHeight, GameManagerCallback callback) {
        this.screenHeight = screenHeight;
        this.callback = callback;
        birdX = (int) resources.getDimension(R.dimen.bird_x);
        birdY = screenHeight / 2;
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
        if(collision) {
            if (birdY + bird_down.getHeight() < screenHeight) {
                birdY += currentFallingSpeed;
                currentFallingSpeed += gravity;
                Rect birdPosition = new Rect(birdX, birdY, birdX + birdWidth, birdY + birdHeight);
                callback.updatePosition(birdPosition);
            }
        } else {
            birdY += currentFallingSpeed;
            currentFallingSpeed += gravity;
        }

    }

    public void onTouchEvent() {
        if (!collision) {
            currentFallingSpeed = flappyBoost;
        }
    }

    public void collision() {
        collision = true;
    }
}
