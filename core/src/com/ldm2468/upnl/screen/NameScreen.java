package com.ldm2468.upnl.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.ldm2468.upnl.KB;

import static com.ldm2468.upnl.Upnl.game;

public class NameScreen implements Screen {
    String name = "";
    int pointer = 0;

    static class SimpleInput implements InputProcessor {
        char lastTyped = 0;
        boolean typed = false;

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            lastTyped = character;
            typed = true;
            return true;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }

        public char pop() {
            if (!typed) return 0;
            typed = false;
            return lastTyped;
        }
    }

    SimpleInput input = new SimpleInput();

    @Override
    public void show() {
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void render(float delta) {
        if (KB.anyJ(Input.Keys.LEFT)) {
            if (pointer > 0) pointer--;
        }
        if (KB.anyJ(Input.Keys.RIGHT)) {
            if (pointer < name.length()) pointer++;
        }
        if (KB.anyJ(Input.Keys.FORWARD_DEL) && pointer < name.length()) {
            name = name.substring(0, pointer) + name.substring(pointer + 1, name.length());
        }
        if(KB.anyJ(Input.Keys.ENTER) && name.length() > 0) {
            game.preferences.putString("name", name);
            game.preferences.flush();
            game.setScreen(new TitleScreen());
        }
        char c = input.pop();
        if (c != 0) {
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9') {
                name = name.substring(0, pointer) + c + name.substring(pointer, name.length());
                pointer++;
            }
            if(c == '\b' && pointer > 0) {
                name = name.substring(0, pointer - 1) + name.substring(pointer, name.length());
                pointer--;
            }
            if(c == '\n' && name.length() > 0) {
                game.preferences.putString("name", name);
                game.preferences.flush();
                game.setScreen(new TitleScreen());
            }
        }

        String prompt = "Input name ([a-zA-Z0-9]+):";
        String nameStr = name.substring(0, pointer) + "|" + name.substring(pointer, name.length());
        GlyphLayout pl = new GlyphLayout(game.lightFont, prompt), nl = new GlyphLayout(game.lightFont, nameStr);
        game.sbui.begin();
        game.lightFont.draw(game.sbui, pl, Gdx.graphics.getWidth() / 2 - pl.width / 2, Gdx.graphics.getHeight() / 2 + pl.height + 8);
        game.lightFont.draw(game.sbui, nl, Gdx.graphics.getWidth() / 2 - nl.width / 2, Gdx.graphics.getHeight() / 2 - 8);
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
