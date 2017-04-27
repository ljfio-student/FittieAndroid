package me.fittie.app.network;

/**
 * Created by luke on 27/04/2017.
 * https://developer.android.com/training/volley/requestqueue.html
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Networker {
    private static Networker instance;
    private RequestQueue requestQueue;
    private static Context context;

    private Networker(Context context) {
        Networker.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized Networker getInstance(Context context) {
        if (instance == null) {
            instance = new Networker(context);
        }

        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}

