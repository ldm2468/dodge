package com.ldm2468.upnl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.TimeUtils;

import static com.ldm2468.upnl.Upnl.game;

public class GameOverScreen implements Screen {
    long score;
    float animStart = 0;
    float animEnd = 0.5f;

    public GameOverScreen(long score) {
        this.score = score;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        GlyphLayout timeLayout = new GlyphLayout(game.timeFont, Utils.formatNanoTime(score));
        float x = Gdx.graphics.getWidth() / 2 - timeLayout.width / 2,
                y = Gdx.graphics.getHeight() / 2 + timeLayout.height / 2;
        if (animStart < animEnd) {
            float a = Interpolation.pow3Out.apply(animStart / animEnd);
            x = Interpolation.linear.apply(Gdx.graphics.getWidth() - timeLayout.width - 16, x, a);
            y = Interpolation.linear.apply(Gdx.graphics.getHeight() - 16, y, a);
            animStart += Gdx.graphics.getDeltaTime();
        } else {
            if (KB.anyKeyJ()) {
                game.setScreen(new GameScreen());
            }
        }
        game.sbui.begin();
        game.timeFont.draw(game.sbui, timeLayout, x, y);
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
