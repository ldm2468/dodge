package com.ldm2468.upnl.enemy;

import com.badlogic.gdx.utils.Array;

public class SpawningEnemy extends CircularEnemy {
    Enemy e;
    int elFrames = 0;
    int spawnPeriod;
    float v;
    Array<Enemy> pool;
    public SpawningEnemy(Enemy e, int spawnPeriod, float v, Array<Enemy> pool) {
        this.e = e;
        this.v = v;
        this.pool = pool;
    }
    @Override
    public void update() {
        elFrames++;
        if(elFrames == spawnPeriod) {

            elFrames = 0;
        }
    }

    @Override
    public OutOfBoundsBehavior oobBehavior() {
        return null;
    }

    @Override
    public void respawn() {

    }
}
