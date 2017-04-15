package com.ldm2468.upnl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpParametersUtils;

import java.util.HashMap;
import java.util.Map;

public class Leaderboard {
    public static void post(String name, long score) {
        Net.HttpRequest request = new Net.HttpRequest("POST");
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("score", "" + score);
        request.setUrl("http://ldm2468.com/upnl-leaderboard/post.php");
        request.setContent(HttpParametersUtils.convertHttpParameters(params));
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println(httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("fuck");
            }

            @Override
            public void cancelled() {

            }
        });
    }
}
