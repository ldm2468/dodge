package com.ldm2468.upnl.screen;

import static com.ldm2468.upnl.Upnl.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.ldm2468.upnl.KB;

public class TitleScreen implements Screen {
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.sbui.begin();
        game.sbui.draw(game.title, Gdx.graphics.getWidth() / 2 - game.title.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - game.title.getHeight() / 2 + 90);
        GlyphLayout namePrompt = new GlyphLayout(game.smallFont, "Press ESC to change name (currently " + game.preferences.getString("name", "error") + ")");
        GlyphLayout scorePrompt = new GlyphLayout(game.smallFont, "Press SPACE for online leaderboard");
        game.smallFont.draw(game.sbui, namePrompt, Gdx.graphics.getWidth() / 2 - namePrompt.width / 2,
                Gdx.graphics.getHeight() / 2 - game.title.getHeight() / 2 + 90);
        game.smallFont.draw(game.sbui, scorePrompt, Gdx.graphics.getWidth() / 2 - namePrompt.width / 2,
                Gdx.graphics.getHeight() / 2 - game.title.getHeight() / 2 + 84 - namePrompt.height);
        game.sbui.end();

        if (KB.anyJ(Input.Keys.ESCAPE)) {
            game.setScreen(new NameScreen());
        } else if(KB.anyJ(Input.Keys.SPACE)) {
            Gdx.net.openURI("http://ldm2468.com/upnl-leaderboard");
        } else if (KB.anyKeyJ()) { // any key
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
