package co.paulfran.paulfranco.flappybird;

import android.graphics.Rect;

import java.util.ArrayList;

import co.paulfran.paulfranco.flappybird.sprites.Obstacle;

public interface GameManagerCallback {

    void updatePosition(Rect birdPosition);
    void updatePosition(Obstacle obstacle, ArrayList<Rect> positions);
    void removeObstacle(Obstacle obstacle);
}
