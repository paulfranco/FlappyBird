package co.paulfran.paulfranco.flappybird;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import co.paulfran.paulfranco.flappybird.sprites.Bird;


public class GameManager extends SurfaceView implements SurfaceHolder.Callback{

    public MainThread thread;
    private Bird bird;

    public GameManager(Context context, AttributeSet attributeSet) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        initGame();
    }

    private void initGame() {
        bird = new Bird(getResources());
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

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        bird.draw(canvas);
    }
}
