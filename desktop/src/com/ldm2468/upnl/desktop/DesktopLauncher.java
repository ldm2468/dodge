package com.ldm2468.upnl.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ldm2468.upnl.Upnl;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 2);
        config.setWindowSizeLimits(1280, 720, -1, -1);
        new Lwjgl3Application(new Upnl(), config);
    }
}
