package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Spawns enemies while moving
 */
public class SpawningEnemy implements Enemy {
    LinearCircularEnemy e;
    int elFrames = 0;
    int t = 0;
    int spawnNo = 1;
    int spawnPeriod;
    Array<Enemy> pool;
    SpawningPool spawner;
    ThetaLogic theta;
    SpeedLogic v;
    boolean relSpeed;

    public interface ThetaLogic {
        float theta(int t);
    }

    public interface SpeedLogic {
        float v(int t);
    }

    /**
     *
     * @param e The enemy that spawns the other enemies
     * @param spawnPeriod The number of frames between spawning
     * @param pool The enemy pool to spawn enemies in
     * @param spawningPool The spawning pool to spawn enemies from
     * @param theta The callback for theta
     * @param v The callback for v
     * @param spawnNo How many to spawn every time
     * @param relSpeed Whether to spawn with relative speed
     */
    public SpawningEnemy(LinearCircularEnemy e, int spawnPeriod, Array<Enemy> pool, SpawningPool spawningPool, ThetaLogic theta, SpeedLogic v, int spawnNo, boolean relSpeed) {
        this.e = e;
        this.spawnPeriod = spawnPeriod;
        this.pool = pool;
        this.spawner = spawningPool;
        this.theta = theta;
        this.v = v;
        this.spawnNo = spawnNo;
        this.relSpeed = relSpeed;
    }

    @Override
    public void update() {
        e.update();
        elFrames++;
        if (elFrames == spawnPeriod) {
            float v = this.v.v(t);
            float theta = this.theta.theta(t);
            for (int i = 0; i < spawnNo; i++) {
                float vx = v * MathUtils.cos(theta + i * MathUtils.PI2 / spawnNo),
                        vy = v * MathUtils.sin(theta + i * MathUtils.PI2 / spawnNo);
                if (relSpeed) {
                    vx += e.vx;
                    vy += e.vy;
                }
                pool.add(spawner.gen(e.x, e.y, vx, vy));
            }
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
