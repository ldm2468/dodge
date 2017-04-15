package com.ldm2468.upnl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.*;

public class Upnl extends Game {
    public final float W = 16, H = 9;
    public Rectangle bounds;
    public static Upnl game;
    Viewport ui, view;
    public SpriteBatch sbui;
    public ShapeRenderer sr;

    @Override
    public void create() {
        bounds = new Rectangle(-W / 2, -H / 2, W, H);
        game = this;
        view = new ExtendViewport(W, H);
        ui = new ScreenViewport();
        sbui = new SpriteBatch();
        sr = new ShapeRenderer();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        setScreen(new TitleScreen());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        ui.update(width, height, true);
        view.update(width, height);
        sbui.setProjectionMatrix(ui.getCamera().combined);
        sr.setProjectionMatrix(view.getCamera().combined);
    }

    @Override
    public void dispose() {
    }
}
