package co.paulfran.paulfranco.flappybird.sprites;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import co.paulfran.paulfranco.flappybird.GameManagerCallback;
import co.paulfran.paulfranco.flappybird.R;

public class ObstacleManager implements ObstacleCallback{

    private int interval;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private int screenWidth, screenHeight;
    private Resources resources;
    private int progress = 0;
    private int speed;
    private GameManagerCallback callback;

    public ObstacleManager(Resources resources, int screenHeight, int screenWidth, GameManagerCallback callback) {
        this.resources = resources;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.callback = callback;

        interval = (int) resources.getDimension(R.dimen.obstacle_interval);
        speed = (int) resources.getDimension(R.dimen.obstacle_speed);
        obstacles.add(new Obstacle(resources, screenHeight, screenWidth, this));
    }

    public void update() {
        progress += speed;
        if (progress > interval) {
            progress = 0;
            obstacles.add(new Obstacle(resources, screenHeight, screenWidth, this));
        }
        List<Obstacle> duplicate = new ArrayList<>();
        duplicate.addAll(obstacles);
        for (Obstacle obstacle : duplicate) {
            obstacle.update();
        }
    }

    public void draw(Canvas canvas) {
        for (Obstacle obstacle: obstacles) {
            obstacle.draw(canvas);
        }
    }

    public void obstacleOffScreen(Obstacle obstacle) {
        obstacles.remove(obstacle);
        callback.removeObstacle(obstacle);
    }

    @Override
    public void updatePosition(Obstacle obstacle, ArrayList<Rect> positions) {
        callback.updatePosition(obstacle, positions);
    }
}
