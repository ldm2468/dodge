package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.graphics.Color;

/**
 * A circular enemy that always moves at the same speed
 */
public class LinearCircularEnemy extends CircularEnemy {
    public float vx, vy;

    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param vx x coordinate of speed
     * @param vy y coordinate of speed
     * @param r radius
     * @param c color
     */
    public LinearCircularEnemy(float x, float y, float vx, float vy, float r, Color c) {
        super(x, y, r, c);
        this.vx = vx;
        this.vy = vy;
    }

    @Override
    public void update() {
        x += vx;
        y += vy;
    }

    @Override
    public OutOfBoundsBehavior oobBehavior() {
        return OutOfBoundsBehavior.DESPAWN;
    }

    @Override
    public void respawn() {
    }
}
