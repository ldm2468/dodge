package com.ldm2468.upnl;

import com.badlogic.gdx.utils.StringBuilder;

public class Utils {
    public static String formatNanoTime(long time) {
        long elTime = time / 1000000;
        long milli = elTime % 1000;
        elTime /= 1000;
        long sec = elTime % 60;
        elTime /= 60;
        long min = elTime % 60, hr = elTime / 60;
        StringBuilder str = new StringBuilder(9);
        if (hr > 0) {
            str.append(hr);
            str.append(":");
        }
        if (min > 0) {
            str.append(min, hr > 0 ? 2 : 1, '0');
            str.append(":");
        }
        str.append(sec, min > 0 || hr > 0 ? 2 : 1, '0');
        str.append('.');
        str.append(milli, 3, '0');
        return str.toString();
    }
}
