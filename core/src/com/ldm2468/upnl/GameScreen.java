package com.ldm2468.upnl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.StringBuilder;
import com.ldm2468.upnl.enemy.CircularEnemy;
import com.ldm2468.upnl.enemy.Enemy;
import com.ldm2468.upnl.enemy.RandomLinearEnemy;
import com.ldm2468.upnl.enemy.SpawningEnemy;

import static com.ldm2468.upnl.Upnl.game;

public class GameScreen implements Screen {
    float x = 0, y = 0;
    static final float PLAYER_R = 0.1f;
    static final float ENEMY_R = 0.05f;
    Color player = Color.WHITE;
    Color hitbox = Color.BLACK;
    Color bg = new Color(0x151534FF);
    Color e1 = new Color(0xEEEE22FF);
    Color e2 = new Color(0x22EEEEFF);
    Color e3 = new Color(0xEE22EEFF);
    Color e4 = new Color(0x77EE77FF);
    Color e5 = new Color(0x7777EEFF);
    Array<Enemy> enemies = new Array<Enemy>(false, 200);
    int fps = 300; // enough for 144hz monitors
    long timeStart = 0;
    long frame = 0;
    Rectangle scissors = new Rectangle();

    @Override
    public void show() {
        timeStart = TimeUtils.nanoTime();
        for (int i = 0; i < 20; i++) {
            enemies.add(new RandomLinearEnemy(game.bounds, 1f / fps, 3f / fps, ENEMY_R, e1));
        }
    }

    @Override
    public void render(float delta) {
        // logic
        long targetFrame = (long) ((TimeUtils.nanoTime() - timeStart) * fps * MathUtils.nanoToSec);
        for (; frame < targetFrame; frame++) { // variable frame rate
            if (frame % fps == 0) { // yellow spawner
                enemies.add(new RandomLinearEnemy(game.bounds,
                        (1f + frame / fps / 50f) / fps, (3f + frame / fps / 30f) / fps, ENEMY_R, e1));
            }

            if (frame == (fps * 5)) { // magenta spawner
                enemies.add(new SpawningEnemy(new RandomLinearEnemy(game.bounds, 0.5f / fps, 0.5f / fps, ENEMY_R, e2),
                        32, enemies, new CircularEnemy(0, 0, ENEMY_R, e3),
                        new SpawningEnemy.ThetaLogic() {
                            @Override
                            public float theta(int t) {
                                float theta = t / 10f;
                                theta += (t % 2) * MathUtils.PI;
                                return theta % MathUtils.PI2;
                            }
                        },
                        new SpawningEnemy.SpeedLogic() {
                            @Override
                            public float v(int t) {
                                return 1f / fps;
                            }
                        }));
            }

            if (frame == (fps * 15)) { // green spawner
                enemies.add(new SpawningEnemy(new RandomLinearEnemy(game.bounds, 1f / fps, 1f / fps, ENEMY_R, e2),
                        24, enemies, new CircularEnemy(0, 0, ENEMY_R, e4),
                        new SpawningEnemy.ThetaLogic() {
                            @Override
                            public float theta(int t) {
                                float theta = t / 12f;
                                theta += (t % 4) * MathUtils.PI / 2;
                                return theta % MathUtils.PI2;
                            }
                        },
                        new SpawningEnemy.SpeedLogic() {
                            @Override
                            public float v(int t) {
                                return 1.5f / fps;
                            }
                        }));
            }

            if (frame == (fps * 30)) { // cyan spawner
                enemies.add(new SpawningEnemy(new RandomLinearEnemy(game.bounds, 0.5f / fps, 0.5f / fps, ENEMY_R, e2),
                        14, enemies, new CircularEnemy(0, 0, ENEMY_R, e2),
                        new SpawningEnemy.ThetaLogic() {
                            @Override
                            public float theta(int t) {
                                return (t / 100f + (MathUtils.cos(t / 30f) + 1) * MathUtils.PI) % MathUtils.PI2;
                            }
                        },
                        new SpawningEnemy.SpeedLogic() {
                            @Override
                            public float v(int t) {
                                return 2f / fps;
                            }
                        }));
            }

            if (frame >= (fps * 60) && frame % (fps * 5) == 0) {
                enemies.add(new SpawningEnemy(new RandomLinearEnemy(game.bounds, 1f / fps, 1f / fps, ENEMY_R, e2, true),
                        20, enemies, new CircularEnemy(0, 0, ENEMY_R, e5),
                        new SpawningEnemy.ThetaLogic() {
                            @Override
                            public float theta(int t) {
                                return (t * t / 1000f) % MathUtils.PI2;
                            }
                        },
                        new SpawningEnemy.SpeedLogic() {
                            @Override
                            public float v(int t) {
                                return 2.4f / fps;
                            }
                        }));
            }
            // movement
            x = MathUtils.clamp(x + KB.x() * 2.4f / fps, -game.W / 2, game.W / 2);
            y = MathUtils.clamp(y + KB.y() * 2.4f / fps, -game.H / 2, game.H / 2);
            for (int i = 0; i < enemies.size; i++) { // enemy updates
                Enemy e = enemies.get(i);
                e.update();
                if (e.hit(x, y, PLAYER_R / 4)) {
                    game.setScreen(new GameOverScreen(TimeUtils.nanoTime() - timeStart));
                }
                if (e.outOfBounds(game.bounds)) {
                    switch (e.oobBehavior()) {
                        case RESPAWN:
                            e.respawn();
                            break;
                        case DESPAWN:
                            enemies.removeIndex(i);
                            i--;
                    }
                }
            }
        }


        ScissorStack.calculateScissors(game.view.getCamera(), game.sr.getTransformMatrix(),
                game.bounds, scissors);
        ScissorStack.pushScissors(scissors);
        game.sr.begin(ShapeRenderer.ShapeType.Filled);
        game.sr.setColor(bg);
        game.sr.rect(game.bounds.x, game.bounds.y, game.bounds.width, game.bounds.height);
        game.sr.setColor(player);
        game.sr.circle(x, y, PLAYER_R, 20);
        game.sr.setColor(hitbox);
        game.sr.circle(x, y, PLAYER_R / 4, 10);
        for (Enemy e : enemies) {
            e.draw();
        }
        game.sr.end();
        ScissorStack.popScissors();
        game.sbui.begin();
        GlyphLayout timeLayout = new GlyphLayout(game.timeFont, Utils.formatNanoTime(TimeUtils.nanoTime() - timeStart));
        game.timeFont.draw(game.sbui, timeLayout,
                Gdx.graphics.getWidth() - timeLayout.width - 16, Gdx.graphics.getHeight() - 16);
        game.sbui.end();
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
