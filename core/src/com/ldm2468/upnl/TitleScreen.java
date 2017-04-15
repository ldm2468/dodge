package com.ldm2468.upnl;

import static com.ldm2468.upnl.Upnl.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TitleScreen implements Screen {
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.sbui.begin();
        game.sbui.draw(game.title, Gdx.graphics.getWidth() / 2 - game.title.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - game.title.getHeight() / 2);
        game.sbui.end();

        if (KB.anyKeyJ()) { // any key
            game.setScreen(new GameScreen());
        }
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
