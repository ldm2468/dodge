package com.ldm2468.upnl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.*;
import com.ldm2468.upnl.screen.NameScreen;
import com.ldm2468.upnl.screen.TitleScreen;

public class Upnl extends Game {
    public final float W = 16, H = 9;
    public Rectangle bounds;
    public static Upnl game;
    public Viewport ui, view;
    public SpriteBatch sbui;
    public ShapeRenderer sr;
    public BitmapFont boldFont, lightFont, smallFont;
    public Texture title;
    public Preferences preferences;

    @Override
    public void create() {
        bounds = new Rectangle(-W / 2, -H / 2, W, H);
        game = this;
        view = new ExtendViewport(W, H);
        ui = new ScreenViewport();
        sbui = new SpriteBatch();
        sr = new ShapeRenderer();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        boldFont = new BitmapFont(Gdx.files.internal("lato-1.fnt"));
        lightFont = new BitmapFont(Gdx.files.internal("lato-2.fnt"));
        smallFont = new BitmapFont(Gdx.files.internal("lato-s.fnt"));
        title = new Texture("title.png");
        preferences = Gdx.app.getPreferences("dodge-data");
        setScreen(preferences.contains("name") ? new TitleScreen() : new NameScreen());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.13f, 1);
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
