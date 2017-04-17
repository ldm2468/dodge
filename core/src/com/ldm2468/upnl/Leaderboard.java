package com.ldm2468.upnl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.utils.StringBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Leaderboard {
    public static void post(String name, long score) {
        try {
            Net.HttpRequest request = new Net.HttpRequest("POST");
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", name);
            params.put("score", "" + score);
            byte[] md5 = MessageDigest.getInstance("MD5").digest((name + score + "$alt").getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aMd5 : md5) {
                String hex = Integer.toHexString(0xFF & aMd5);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            params.put("checksum", sb.toString());
            request.setUrl("http://ldm2468.com/upnl-leaderboard/post.php");
            request.setHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setContent(HttpParametersUtils.convertHttpParameters(params));
            Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {

                }

                @Override
                public void failed(Throwable t) {

                }

                @Override
                public void cancelled() {

                }
            });
        } catch (NoSuchAlgorithmException e) {
            // lol no
        }
    }
}
