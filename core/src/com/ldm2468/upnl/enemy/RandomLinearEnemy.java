package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class RandomLinearEnemy extends CircularEnemy {
    float bX, bY, bW, bH; // bounds
    float vMin, vMax;
    float vX, vY;

    public RandomLinearEnemy(Rectangle bounds, float vMin, float vMax, float r, Color c) {
        this.r = r;
        bX = bounds.x;
        bY = bounds.y;
        bW = bounds.width;
        bH = bounds.height;
        this.vMin = vMin;
        this.vMax = vMax;
        this.c = c;
        respawn();
    }

    @Override
    public void update() {
        x += vX;
        y += vY;
    }

    @Override
    public OutOfBoundsBehavior oobBehavior() {
        return OutOfBoundsBehavior.RESPAWN;
    }

    @Override
    public void respawn() {
        float loc = MathUtils.random(bW + bH); // same distribution for x, y axis
        int sign = MathUtils.randomSign();
        x = (loc < bW ? loc : sign * (bW / 2 + r) + bW / 2) + bX;
        y = (loc >= bW ? (loc - bW) : sign * (bH / 2 + r) + bH / 2) + bY;
        float theta = MathUtils.random(MathUtils.PI2);
        float v = MathUtils.random(vMin, vMax);
        vX = MathUtils.cos(theta) * v;
        vY = MathUtils.sin(theta) * v;
        vX *= (loc >= bW && sign * vX < 0 ? 1 : -1);
        vY *= (loc < bW && sign * vY < 0 ? 1 : -1);
    }
}
