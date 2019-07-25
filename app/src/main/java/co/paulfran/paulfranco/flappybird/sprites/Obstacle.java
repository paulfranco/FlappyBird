package co.paulfran.paulfranco.flappybird.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import co.paulfran.paulfranco.flappybird.R;

public class Obstacle implements Sprite {

    private int height, width, separation, xPosition, speed, screenHeight, screenWidth, headHeight, headExtraWidth;
    private int obstacleMinPosition;
    private Bitmap image;
    private ObstacleCallback callback;

    public Obstacle(Resources resources, int screenHeight, int screenWidth, ObstacleCallback callback) {
        image = BitmapFactory.decodeResource(resources, R.drawable.pipes);
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.callback = callback;
        xPosition = screenWidth;
        width = (int) resources.getDimension(R.dimen.obstacle_width);
        speed = (int) resources.getDimension(R.dimen.obstacle_speed);
        headHeight = (int) resources.getDimension(R.dimen.head_height);
        separation = (int) resources.getDimension(R.dimen.obstacle_separation);
        headExtraWidth = (int) resources.getDimension(R.dimen.head_extra_width);
        obstacleMinPosition = (int) resources.getDimension(R.dimen.obstacle_min_position);


        Random random = new Random(System.currentTimeMillis());
        height = random.nextInt(screenHeight - 2 * obstacleMinPosition - separation) + obstacleMinPosition;

    }

    // left off at 12:51
    @Override
    public void draw(Canvas canvas) {
        Rect bottomPipe = new Rect(xPosition + headExtraWidth, screenHeight - height, xPosition + width + headExtraWidth, screenHeight);
        Rect bottomHead = new Rect(xPosition, screenHeight - height - headHeight, xPosition + width + 2*headExtraWidth, screenHeight - height);
        Rect topPipe = new Rect(xPosition + headExtraWidth, 0, xPosition + headExtraWidth + width, screenHeight - height - separation - 2*headHeight);
        Rect topHead = new Rect(xPosition, screenHeight - height - separation - 2*headHeight, xPosition + width + 2*headExtraWidth, screenHeight - height - separation - headHeight);

        Paint paint = new Paint();
        canvas.drawBitmap(image, null, bottomPipe, paint);
        canvas.drawBitmap(image, null, bottomHead, paint);
        canvas.drawBitmap(image, null, topPipe, paint);
        canvas.drawBitmap(image, null, topHead, paint);


    }

    @Override
    public void update() {

        xPosition -= speed;
        if (xPosition <= 0 - width - 2*headExtraWidth) {
            callback.obstacleOffScreen(this);
        }

    }
}
