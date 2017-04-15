package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import static com.ldm2468.upnl.Upnl.game;

/**
 * Circular enemy.
 */
public class CircularEnemy implements Enemy {
    float x, y, r;
    Color c;

    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param r radius
     * @param c color
     */
    public CircularEnemy(float x, float y, float r, Color c) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.c = c;
    }

    @Override
    public boolean hit(float x0, float y0, float r) {
        return (x - x0) * (x - x0) + (y - y0) * (y - y0) < (r + this.r) * (r + this.r);
    }

    @Override
    public void draw() {
        game.sr.setColor(c);
        game.sr.circle(x, y, r, 16);
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    public float r() {
        return r;
    }

    public Color color() {
        return c;
    }

    @Override
    public void update() {

    }

    @Override
    public OutOfBoundsBehavior oobBehavior() {
        return OutOfBoundsBehavior.NONE;
    }

    @Override
    public void respawn() {

    }

    @Override
    public boolean outOfBounds(Rectangle bounds) {
        return MathUtils.clamp(x, bounds.x - r, bounds.x + bounds.width + r) != x ||
                MathUtils.clamp(y, bounds.y - r, bounds.y + bounds.height + r) != y;
    }
}
