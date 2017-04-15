package com.ldm2468.upnl.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.ldm2468.upnl.KB;
import com.ldm2468.upnl.Leaderboard;
import com.ldm2468.upnl.Utils;

import static com.ldm2468.upnl.Upnl.game;

public class GameOverScreen implements Screen {
    long score;
    float anim = 0;
    static final float ANIM_END = 0.5f;
    boolean posted = false;

    public GameOverScreen(long score) {
        this.score = score;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        int width = Gdx.graphics.getWidth(), height = Gdx.graphics.getHeight();
        GlyphLayout timeLayout = new GlyphLayout(game.boldFont, Utils.formatNanoTime(score));
        GlyphLayout prompt1 = new GlyphLayout(game.smallFont, "Press any key to restart");
        GlyphLayout prompt2 = new GlyphLayout(game.smallFont, "Press ESC to return to title");
        GlyphLayout prompt3 = new GlyphLayout(game.smallFont, "Press SPACE for leaderboard");
        float x = width / 2 - timeLayout.width / 2,
                y = height / 2 + timeLayout.height + 24,
                y1 = height / 2 + 16,
                y2 = height / 2 - prompt1.height + 8,
                y3 = height / 2 - prompt1.height - prompt2.height;
        if (anim < ANIM_END) { // animation
            float a = Interpolation.pow3Out.apply(anim / ANIM_END);
            x = Interpolation.linear.apply(width - timeLayout.width - 16, x, a);
            y = Interpolation.linear.apply(height - 16, y, a);
            y1 = Interpolation.linear.apply(0, y1, a);
            y2 = Interpolation.linear.apply(-prompt1.height + 8, y2, a);
            y3 = Interpolation.linear.apply(-prompt1.height - prompt2.height, y3, a);
            anim += Gdx.graphics.getDeltaTime();
        } else if (!posted) {
            Leaderboard.post(game.preferences.getString("name", "error"), score / 100000);
            posted = true;
        } else if (KB.anyJ(Input.Keys.ESCAPE)) {
            game.setScreen(new TitleScreen());
        } else if (KB.anyJ(Input.Keys.SPACE)) {
            Gdx.net.openURI("http://ldm2468.com/upnl-leaderboard");
        } else if (KB.anyKeyJ()) {
            game.setScreen(new GameScreen());
        }
        game.sbui.begin();
        game.boldFont.draw(game.sbui, timeLayout, x, y);
        game.smallFont.draw(game.sbui, prompt1, width / 2 - prompt1.width / 2, y1);
        game.smallFont.draw(game.sbui, prompt2, width / 2 - prompt1.width / 2, y2);
        game.smallFont.draw(game.sbui, prompt3, width / 2 - prompt1.width / 2, y3);
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
