package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.math.Rectangle;

public interface Enemy {
    /**
     * Behavior when out of bounds.
     * <br>DESPAWN: remove the enemy
     * <br>RESPAWN: respawn the enemy
     * <br>NONE: do nothing
     */
    enum OutOfBoundsBehavior {
        DESPAWN, RESPAWN, NONE
    }

    /**
     * Call this method every frame.
     */
    void update();
    float x();
    float y();

    /**
     * Whether the enemy hits a circle at (x, y) with radius r
     * @param x x coordinate
     * @param y y coordinate
     * @param r radius
     * @return whether the enemy hits the circle
     */
    boolean hit(float x, float y, float r);

    /**
     * Draw the enemy.
     */
    void draw();

    /**
     * Whether the enemy is outside the rectangle completely
     * @param bounds The rectangle
     * @return whether the enemy is inside
     */
    boolean outOfBounds(Rectangle bounds);

    OutOfBoundsBehavior oobBehavior();

    /**
     * Respawn the enemy. Used to minimize object creation/deletion
     */
    void respawn();
}
