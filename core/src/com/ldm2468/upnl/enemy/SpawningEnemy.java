package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class SpawningEnemy implements Enemy {
    Enemy e;
    int elFrames = 0;
    int t = 0;
    int spawnPeriod;
    Array<Enemy> pool;
    CircularEnemy template;
    ThetaLogic theta;
    SpeedLogic v;
    public interface ThetaLogic {
        float theta(int t);
    }
    public interface SpeedLogic {
        float v(int t);
    }

    public SpawningEnemy(Enemy e, int spawnPeriod, Array<Enemy> pool, CircularEnemy template, ThetaLogic theta, SpeedLogic v) {
        this.e = e;
        this.spawnPeriod = spawnPeriod;
        this.pool = pool;
        this.template = template;
        this.theta = theta;
        this.v = v;
    }

    @Override
    public void update() {
        e.update();
        elFrames++;
        if (elFrames == spawnPeriod) {
            float v = this.v.v(t);
            float theta = this.theta.theta(t);
            pool.add(new LinearCircularEnemy(e.x(), e.y(), v * MathUtils.cos(theta), v * MathUtils.sin(theta),
                    template.r(), template.color()));
            t++;
            elFrames = 0;
        }
    }

    @Override
    public boolean hit(float x, float y, float r) {
        return e.hit(x, y, r);
    }

    @Override
    public void draw() {
        e.draw();
    }

    @Override
    public float x() {
        return e.x();
    }

    @Override
    public float y() {
        return e.y();
    }

    @Override
    public boolean outOfBounds(Rectangle bounds) {
        return e.outOfBounds(bounds);
    }

    @Override
    public OutOfBoundsBehavior oobBehavior() {
        return e.oobBehavior();
    }

    @Override
    public void respawn() {
        e.respawn();
    }
}
