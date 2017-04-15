package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import static com.ldm2468.upnl.Upnl.game;

public abstract class CircularEnemy implements Enemy {
    float x, y, r;
    Color c;

    @Override
    public boolean hit(float x0, float y0, float r) {
        return (x - x0) * (x - x0) + (y - y0) * (y - y0) < (r + this.r) * (r + this.r);
    }

    @Override
    public void draw() {
        game.sr.setColor(c);
        game.sr.circle(x, y, r, 20);
    }

    @Override
    public boolean outOfBounds(Rectangle bounds) {
        return MathUtils.clamp(x, bounds.x - r, bounds.x + bounds.width + r) != x ||
                MathUtils.clamp(y, bounds.y - r, bounds.y + bounds.height + r) != y;
    }
}
