package com.ldm2468.upnl;

import static com.badlogic.gdx.Input.Keys.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KB {
    public static boolean any(int... keys) {
        for (int k : keys) {
            if (Gdx.input.isKeyPressed(k)) {
                return true;
            }
        }
        return false;
    }

    public static boolean anyJ(int... keys) {
        for (int k : keys) {
            if (Gdx.input.isKeyJustPressed(k)) {
                return true;
            }
        }
        return false;
    }

    public static boolean anyNot(int... keys) {
        for (int k : keys) {
            if (Gdx.input.isKeyPressed(k)) {
                return false;
            }
        }
        return Gdx.input.isKeyPressed(Input.Keys.ANY_KEY);
    }

    public static float x() {
        return (any(RIGHT, D) ? 1 : 0) - (any(LEFT, A) ? 1 : 0);
    }

    public static float y() {
        return (any(UP, W) ? 1 : 0) - (any(DOWN, S) ? 1 : 0);
    }

    private static boolean lastPressed = true;
}
