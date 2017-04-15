package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class SpawningPool {
    float r;
    Color c;
    Array<LinearCircularEnemy> pool;

    public SpawningPool(float r, Color c) {
        this.r = r;
        this.c = c;
        pool = new Array<LinearCircularEnemy>(false, 100);
    }

    public Enemy gen(float x, float y, float vx, float vy) {
        if(pool.size > 0) {
            LinearCircularEnemy e = pool.pop();
            e.x = x;
            e.y = y;
            e.vx = vx;
            e.vy = vy;
            return e;
        } else {
            return new LinearCircularEnemy(x, y, vx, vy, r, c) {
                @Override
                public boolean outOfBounds(Rectangle bounds) {
                    boolean out = super.outOfBounds(bounds);
                    if(out) pool.add(this);
                    return out;
                }
            };
        }
    }
}
