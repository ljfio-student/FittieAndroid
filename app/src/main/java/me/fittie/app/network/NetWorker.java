package me.fittie.app.network;

/**
 * Created by luke on 27/04/2017.
 * https://developer.android.com/training/volley/requestqueue.html
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NetWorker {
    private static NetWorker instance;
    private RequestQueue requestQueue;
    private static Context context;
    private Map<String, String> defaultHeaders;

    private NetWorker(Context context) {
        NetWorker.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized NetWorker getInstance(Context context) {
        if (instance == null) {
            instance = new NetWorker(context);
        }

        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public Map<String, String> getDefaultHeaders() {
        if (defaultHeaders == null) {
            defaultHeaders = new HashMap<String, String>();
        }

        return defaultHeaders;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}

