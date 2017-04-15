package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class RandomLinearEnemy extends LinearCircularEnemy {
    float bX, bY, bW, bH; // bounds
    float vMin, vMax;
    boolean despawn = false;

    public RandomLinearEnemy(Rectangle bounds, float vMin, float vMax, float r, Color c) {
        this(bounds, vMin, vMax, r, c, false);
    }

    public RandomLinearEnemy(Rectangle bounds, float vMin, float vMax, float r, Color c, boolean despawn) {
        super(0, 0, 0, 0, r, c);
        bX = bounds.x;
        bY = bounds.y;
        bW = bounds.width;
        bH = bounds.height;
        this.vMin = vMin;
        this.vMax = vMax;
        despawn = true;
        respawn();
    }

    @Override
    public OutOfBoundsBehavior oobBehavior() {
        return despawn ? OutOfBoundsBehavior.DESPAWN: OutOfBoundsBehavior.RESPAWN;
    }

    @Override
    public void respawn() {
        float loc = MathUtils.random(bW + bH); // same distribution for x, y axis
        int sign = MathUtils.randomSign();
        x = (loc < bW ? loc : sign * (bW / 2 + r) + bW / 2) + bX;
        y = (loc >= bW ? (loc - bW) : sign * (bH / 2 + r) + bH / 2) + bY;
        float theta = MathUtils.random(MathUtils.PI2);
        float v = MathUtils.random(vMin, vMax);
        vx = MathUtils.cos(theta) * v;
        vy = MathUtils.sin(theta) * v;
        vx *= (loc >= bW && sign * vx < 0 ? 1 : -1);
        vy *= (loc < bW && sign * vy < 0 ? 1 : -1);
    }
}
