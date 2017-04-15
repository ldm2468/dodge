package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.math.Rectangle;

public interface Enemy {
    enum OutOfBoundsBehavior {
        DESPAWN, RESPAWN, NONE
    }
    void update();
    boolean hit(float x, float y, float r);
    void draw();
    boolean outOfBounds(Rectangle bounds);
    OutOfBoundsBehavior oobBehavior();
    void respawn();
}
