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
        game.sr.begin(ShapeRenderer.ShapeType.Filled);
        game.sr.rect(-2, -2, 4, 4);
        game.sr.end();
        if(KB.anyJ(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen());
        }
        if(KB.anyJ(Input.Keys.SPACE)) {
            Gdx.net.openURI("http://ldm2468.com/upnl/leaderboards");
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
