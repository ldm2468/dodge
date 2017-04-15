package com.ldm2468.upnl;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import com.ldm2468.upnl.enemy.Enemy;
import com.ldm2468.upnl.enemy.RandomLinearEnemy;

import static com.ldm2468.upnl.Upnl.game;

public class GameScreen implements Screen {
    float x = 0, y = 0;
    float playerR = 0.1f;
    float enemyR = 0.05f;
    Color player = Color.WHITE;
    Color e1 = new Color(0xEEEE22FF);
    Color e2 = new Color(0x22EEEEFF);
    Array<Enemy> enemies = new Array<Enemy>(false, 200);
    Rectangle scissors = new Rectangle();

    @Override
    public void show() {
        for (int i = 0; i < 100; i++) {
            enemies.add(new RandomLinearEnemy(game.bounds, 0.02f, 0.06f, enemyR, e1));
        }
    }

    @Override
    public void render(float delta) {
        ScissorStack.calculateScissors(game.view.getCamera(), game.sr.getTransformMatrix(),
                game.bounds, scissors);
        ScissorStack.pushScissors(scissors);
        game.sr.begin(ShapeRenderer.ShapeType.Filled);
        game.sr.setColor(player);
        x = MathUtils.clamp(x + KB.x() / 20, -game.W / 2, game.W / 2);
        y = MathUtils.clamp(y + KB.y() / 20, -game.H / 2, game.H / 2);
        game.sr.circle(x, y, playerR, 20);
        for (int i = 0; i < enemies.size; i++) {
            Enemy e = enemies.get(i);
            e.update();
            e.draw();
            if(e.hit(x, y, playerR / 2)) {
                System.out.println("u ded");
            }
            if (e.outOfBounds(game.bounds)) {
                switch(e.oobBehavior()) {
                    case RESPAWN:
                        e.respawn();
                        break;
                    case DESPAWN:
                        enemies.removeIndex(i);
                        i--;
                }
            }
        }
        game.sr.end();
        ScissorStack.popScissors();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
