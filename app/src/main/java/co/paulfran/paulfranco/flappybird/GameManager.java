package co.paulfran.paulfranco.flappybird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.paulfran.paulfranco.flappybird.sprites.Background;
import co.paulfran.paulfranco.flappybird.sprites.Bird;
import co.paulfran.paulfranco.flappybird.sprites.Obstacle;
import co.paulfran.paulfranco.flappybird.sprites.ObstacleManager;


public class GameManager extends SurfaceView implements SurfaceHolder.Callback, GameManagerCallback{

    public MainThread thread;

    private GameState gameState = GameState.PLAYING;

    private Bird bird;
    private Background background;
    private DisplayMetrics dm;
    private ObstacleManager obstacleManager;
    private Rect birdPosition;
    private HashMap<Obstacle, List<Rect>> obstaclePositions = new HashMap<>();


    public GameManager(Context context, AttributeSet attributeSet) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        initGame();
    }

    private void initGame() {
        bird = new Bird(getResources(), dm.heightPixels, this);
        background = new Background(getResources(), dm.heightPixels);
        obstacleManager = new ObstacleManager(getResources(), dm.heightPixels, dm.widthPixels, this);
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
        switch (gameState) {
            case PLAYING:
                bird.update();
                obstacleManager.update();
                break;
            case GAME_OVER:
                bird.update();
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
            super.draw(canvas);
            canvas.drawRGB(150, 255, 255);
            background.draw(canvas);
            switch (gameState) {
                case PLAYING:
                    bird.draw(canvas);
                    obstacleManager.draw(canvas);
                    calculateCollision();
                    break;
                case GAME_OVER:
                    bird.draw(canvas);
                    obstacleManager.draw(canvas);
                    break;
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (gameState) {
            case PLAYING:
                bird.onTouchEvent();
                break;
            case GAME_OVER:
                break;
        }
        bird.onTouchEvent();
        return super.onTouchEvent(event);
    }

    @Override
    public void updatePosition(Rect birdPosition) {
        this.birdPosition = birdPosition;
    }

    @Override
    public void updatePosition(Obstacle obstacle, ArrayList<Rect> positions) {
        if (obstaclePositions.containsKey(obstacle)) {
            obstaclePositions.remove(obstacle);
        }
        obstaclePositions.put(obstacle, positions);
    }

    @Override
    public void removeObstacle(Obstacle obstacle) {
        obstaclePositions.remove(obstacle);
    }

    public void calculateCollision() {
        boolean collision = false;
        if (birdPosition.bottom > dm.heightPixels) {
            collision = true;
        } else {
            for (Obstacle obstacle : obstaclePositions.keySet()) {
                Rect bottomRectangle = obstaclePositions.get(obstacle).get(0);
                Rect topRectangle = obstaclePositions.get(obstacle).get(1);
                if (birdPosition.right > bottomRectangle.left && birdPosition.left < bottomRectangle.right && birdPosition.bottom > bottomRectangle.top) {
                    collision = true;
                } else if (birdPosition.right > topRectangle.left && birdPosition.left < topRectangle.right && birdPosition.top < topRectangle.bottom){
                    collision = true;
                }
            }
        }
        if (collision) {
            // implement Game Over
            gameState = GameState.GAME_OVER;
            bird.collision();
        }
    }
}
