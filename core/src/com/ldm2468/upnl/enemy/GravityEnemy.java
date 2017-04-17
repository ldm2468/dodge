package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.ldm2468.upnl.screen.GameScreen;

public class GravityEnemy extends CircularEnemy {
    float vx = 0, vy = 0;
    static final float MAX_SPEED = 1.5f;
    float g;

    /**
     * @param x x coordinate
     * @param y y coordinate
     * @param r radius
     * @param c color
     * @param g gravitational constant
     */
    public GravityEnemy(float x, float y, float r, Color c, float g) {
        super(x, y, r, c);
        this.g = g;
    }

    @Override
    public void update() {
        double dx = GameScreen.x - x, dy = GameScreen.y - y;
        double d2 = dx * dx + dy * dy;
        if (d2 == 0) d2 = 1;
        double d = (float) Math.sqrt(d2);
        vx *= 0.995;
        vy *= 0.995;
        vx += (dx / d) * g;
        vy += (dy / d) * g;
        double speed = Math.sqrt(vx * vx + vy * vy);
        double newSpeed = MathUtils.clamp(speed, -MAX_SPEED / GameScreen.FPS, MAX_SPEED / GameScreen.FPS);
        vx *= newSpeed / speed;
        vy *= newSpeed / speed;
        x += vx;
        y += vy;
    }
}
