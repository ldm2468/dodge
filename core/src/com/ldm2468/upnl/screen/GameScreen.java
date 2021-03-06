package com.ldm2468.upnl.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.ldm2468.upnl.KB;
import com.ldm2468.upnl.Utils;
import com.ldm2468.upnl.enemy.*;

import static com.ldm2468.upnl.Upnl.game;

public class GameScreen implements Screen {
    public GameScreen(boolean hell) {
        this.hell = hell;
    }

    public static float x = 0, y = 0;
    static final float PLAYER_R = 0.1f;
    static final float ENEMY_R = 0.05f;

    public static final Color player = Color.WHITE;
    public static final Color hitbox = Color.BLACK;
    public static final Color hellColor = new Color(0.2f, 0.1f, 0.1f, 1);
    public static final Color border = new Color(0x444466FF);
    public static final Color e1 = new Color(0xEEEE22FF);
    public static final Color e2 = new Color(0x22EEEEFF);
    public static final Color e3 = new Color(0xEE22EEFF);
    public static final Color e4 = new Color(0x66FF66FF);
    public static final Color e5 = new Color(0x66AAFFFF);
    public static final Color e6 = new Color(0xFF6666FF);
    public static final Color e7 = new Color(0xCCCCCCFF);

    Array<Enemy> enemies = new Array<Enemy>(false, 200);

    public static final int FPS = 300; // enough for 144hz monitors
    long timeStart = 0;
    long frame = 0;
    boolean hell = true;

    Rectangle scissors = new Rectangle();

    @Override
    public void show() {
        x = y = 0;
        timeStart = TimeUtils.nanoTime();
        if (!hell) {
            for (int i = 0; i < 10; i++) {
                enemies.add(new RandomLinearEnemy(game.bounds, 1f / FPS, 2f / FPS, ENEMY_R, e1));
            }
            enemies.add(new GravityEnemy(MathUtils.randomSign() * game.H, MathUtils.randomSign() * game.W,
                    ENEMY_R, e6, 0.000015f));
        }
    }

    @Override
    public void render(float delta) {
        /* cheating
        if (KB.any(Input.Keys.R)) {
            timeStart -= 1E8 * 5;
            frame += FPS / 2;
        }
        if (KB.any(Input.Keys.E)) {
            timeStart += 1E8 * 5;
            frame -= FPS / 2;
        }
        */
        // logic
        long targetFrame = (long) ((TimeUtils.nanoTime() - timeStart) * FPS * MathUtils.nanoToSec);
        for (; frame < targetFrame; frame++) { // variable frame rate
            if (!hell && frame % (4 * FPS) == 0) { // yellow spawner
                enemies.add(new RandomLinearEnemy(game.bounds,
                        (1f + frame / FPS / 80f) / FPS, (2f + frame / FPS / 60f) / FPS,
                        ENEMY_R, e1));
            }

            if (frame == (FPS * (hell ? 0 : 4))) { // magenta spawner
                enemies.add(new SpawningEnemy(
                        new RandomLinearEnemy(game.bounds, 0.4f / FPS, 0.5f / FPS, ENEMY_R, e3),
                        hell ? 100 : 800, enemies, new SpawningPool(ENEMY_R, e3),
                        new SpawningEnemy.ThetaLogic() {
                            @Override
                            public float theta(int t) {
                                return (t % 2) * MathUtils.PI / 24;
                            }
                        },
                        new SpawningEnemy.SpeedLogic() {
                            @Override
                            public float v(int t) {
                                return 0.8f / FPS * (hell ? MathUtils.sin(t / 20f) : 1);
                            }
                        }, 24, false));
            }

            if (frame == (FPS * (hell ? 60 : 20))) { // green spawner
                if (hell) {
                    enemies.clear();
                }
                enemies.add(new SpawningEnemy(
                        new RandomLinearEnemy(game.bounds, 0.4f / FPS, 0.6f / FPS, ENEMY_R, e4),
                        hell ? 3 : 30, enemies, new SpawningPool(ENEMY_R, e4),
                        new SpawningEnemy.ThetaLogic() {
                            @Override
                            public float theta(int t) {
                                float theta = t / 20f;
                                theta += (t % 3) * MathUtils.PI * 2 / 3;
                                return theta % MathUtils.PI2;
                            }
                        },
                        new SpawningEnemy.SpeedLogic() {
                            @Override
                            public float v(int t) {
                                return 0.8f / FPS;
                            }
                        }, 1, true));
            }

            if (frame == (FPS * (hell ? 120 : 40))) { // cyan spawner
                if (hell) {
                    enemies.clear();
                }
                for (int i = 0; i < (hell ? 2 : 1); i++) {
                    enemies.add(new SpawningEnemy(
                            new RandomLinearEnemy(game.bounds, 0.5f / FPS, 0.6f / FPS, ENEMY_R, e2),
                            hell ? 5 : 32, enemies, new SpawningPool(ENEMY_R, e2),
                            new SpawningEnemy.ThetaLogic() {
                                @Override
                                public float theta(int t) {
                                    return (t / 90f + (MathUtils.cos(t / 20f) + 1) * MathUtils.PI) % MathUtils.PI2;
                                }
                            },
                            new SpawningEnemy.SpeedLogic() {
                                @Override
                                public float v(int t) {
                                    return 1.6f / FPS;
                                }
                            }, 1, false));
                }
            }

            if (frame >= (FPS * (hell ? 180 : 60)) && (((!hell && frame % (FPS * 15) == 0) || enemies.size == 0) || hell && frame <= FPS * 180)) {
                if (hell && frame <= FPS * 180) {
                    enemies.clear();
                }
                enemies.add(new SpawningEnemy(
                        new RandomLinearEnemy(game.bounds, 0.5f / FPS, 0.5f / FPS, ENEMY_R, e5, true),
                        (hell ? 5 : 16), enemies, new SpawningPool(ENEMY_R, e5),
                        new SpawningEnemy.ThetaLogic() {
                            @Override
                            public float theta(int t) {
                                return ((t + 600) * (hell ? 0.1f : 1) * (t + 600) / 1000f) % MathUtils.PI2;
                            }
                        },
                        new SpawningEnemy.SpeedLogic() {
                            @Override
                            public float v(int t) {
                                return 2.2f / FPS;
                            }
                        }, hell ? 3 : 1, false));
            }

            if (frame == FPS * (hell ? 300 : 90)) {
                if (hell) {
                    enemies.clear();
                }
                for (int i = 0; i < (hell ? 2 : 1); i++) {
                    enemies.add(new SpawningEnemy(
                            new RandomLinearEnemy(game.bounds, 0.5f / FPS, 0.5f / FPS, ENEMY_R, e7),
                            hell ? 12 : 48, enemies, new SpawningPool(ENEMY_R, e7),
                            new SpawningEnemy.ThetaLogic() {
                                @Override
                                public float theta(int t) {
                                    return ((t / (hell ? 12 : 3)) % 4) * MathUtils.PI / (hell ? 48 : 24);
                                }
                            },
                            new SpawningEnemy.SpeedLogic() {
                                @Override
                                public float v(int t) {
                                    return (hell ? 2 : 0.9f) / FPS;
                                }
                            }, hell ? 24 : 12, true));
                }
            }
            // movement
            x = MathUtils.clamp(x + KB.x() * 2.4f / FPS, -game.W / 2, game.W / 2);
            y = MathUtils.clamp(y + KB.y() * 2.4f / FPS, -game.H / 2, game.H / 2);

            for (int i = 0; i < enemies.size; i++) { // enemy updates
                Enemy e = enemies.get(i);
                e.update();

                if (e.hit(x, y, 0)) { // death
                    game.setScreen(new GameOverScreen(TimeUtils.nanoTime() - timeStart, hell));
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

        game.sr.begin(ShapeRenderer.ShapeType.Filled);
        if (hell) {
            game.sr.setColor(hellColor);
            game.sr.rect(-game.W * 10, -game.H * 10, game.W * 20, game.H * 20);
        }
        game.sr.setColor(border);
        game.sr.rect(game.bounds.x - 0.2f, game.bounds.y - 0.2f, game.bounds.width + 0.4f, game.bounds.height + 0.4f);
        game.sr.end();

        ScissorStack.calculateScissors(game.view.getCamera(), game.sr.getTransformMatrix(),
                game.bounds, scissors);
        ScissorStack.pushScissors(scissors);

        game.sr.begin(ShapeRenderer.ShapeType.Filled);

        game.sr.setColor(0.1f, 0.1f, 0.13f, 1);
        game.sr.rect(game.bounds.x, game.bounds.y, game.bounds.width, game.bounds.height);
        if (hell) {
            game.sr.setColor(hellColor);
            game.sr.rect(-game.W * 10, -game.H * 10, game.W * 20, game.H * 20);
        }

        game.sr.setColor(player);
        game.sr.circle(x, y, PLAYER_R, 20);
        game.sr.setColor(hitbox);
        game.sr.circle(x, y, PLAYER_R / 4, 10);

        for (Enemy e : enemies) {
            e.draw();
        }

        game.sr.end();

        ScissorStack.popScissors();

        game.sbui.begin(); // time
        GlyphLayout timeLayout = new GlyphLayout(game.boldFont, Utils.formatNanoTime(TimeUtils.nanoTime() - timeStart));
        game.boldFont.draw(game.sbui, timeLayout,
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
