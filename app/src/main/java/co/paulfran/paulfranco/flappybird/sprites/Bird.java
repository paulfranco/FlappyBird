package co.paulfran.paulfranco.flappybird.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import co.paulfran.paulfranco.flappybird.R;

public class Bird implements Sprite {

    private Bitmap bird;
    private int birdWidth;
    private int birdHeight;
    private int birdX;

    public Bird(Resources resources) {
        birdX = (int) resources.getDimension(R.dimen.bird_x);
        birdWidth = (int) resources.getDimension(R.dimen.bird_width);
        birdHeight = (int) resources.getDimension(R.dimen.bird_height);
        Bitmap birdBmp = BitmapFactory.decodeResource(resources, R.drawable.bird_down);
        bird = Bitmap.createScaledBitmap(birdBmp, birdWidth, birdHeight, false);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bird, birdX, 300, null);
    }

    @Override
    public void update() {

    }
}
