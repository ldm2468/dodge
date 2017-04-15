package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.graphics.Color;

public class LinearCircularEnemy extends CircularEnemy {
    float vx, vy;

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
