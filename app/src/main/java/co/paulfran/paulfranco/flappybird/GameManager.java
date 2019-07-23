package co.paulfran.paulfranco.flappybird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import co.paulfran.paulfranco.flappybird.sprites.Background;
import co.paulfran.paulfranco.flappybird.sprites.Bird;
import co.paulfran.paulfranco.flappybird.sprites.Obstacle;


public class GameManager extends SurfaceView implements SurfaceHolder.Callback{

    public MainThread thread;
    private Bird bird;
    private Background background;
    private DisplayMetrics dm;


    public GameManager(Context context, AttributeSet attributeSet) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        initGame();
    }

    private void initGame() {
        bird = new Bird(getResources());
        background = new Background(getResources(), dm.heightPixels);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (thread.getState() == Thread.State.TERMINATED) {
            thread = new MainThread(holder, this);
        }
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        //System.out.println("GameManager update call");
        bird.update();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(150, 255, 255);
        background.draw(canvas);
        bird.draw(canvas);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        bird.onTouchEvent();
        return super.onTouchEvent(event);
    }
}
