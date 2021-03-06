package br.com.examples.caique.examplesproject.controller.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class WebRequestQueue {
    private static WebRequestQueue webRequestQueue;
    private RequestQueue volleyRequestQueue;

    private WebRequestQueue(Context context) {
        volleyRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static WebRequestQueue getInstance(Context context) {
        if (webRequestQueue == null) {
            synchronized (WebRequestQueue.class) {
                if (webRequestQueue == null) {
                    webRequestQueue = new WebRequestQueue(context);
                }
            }
        }
        return webRequestQueue;
    }

    public void addToQueue(Request request, String tag) {
        request.setTag(tag);
        volleyRequestQueue.add(request);
    }

    public void cancelAll(String tag) {
        volleyRequestQueue.cancelAll(tag);
    }
}