package me.fittie.app.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Request.Method;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by Luke on 28/04/2017.
 * Built this to speed up making requests or something
 */

public class GsonRequestBuilder<TReq, TRes> {
    private int method;

    private Class<TReq> reqClass;
    private Class<TRes> resClass;

    private Map<String, String> params;
    private Map<String, String> headers;

    private Listener<TRes> listener;
    private ErrorListener errorListener;

    private String url;

    private TReq requestObject;

    public GsonRequestBuilder(Class<TReq> requestClass, Class<TRes> responseClass) {
        this.reqClass = requestClass;
        this.resClass = responseClass;
    }

    public static <UReq, URes> GsonRequestBuilder<UReq, URes> get(Class<URes> resClass) {
        GsonRequestBuilder<UReq, URes> getRequest = new GsonRequestBuilder<>(null, resClass);
        getRequest.setMethod(Method.GET);
        return getRequest;
    }

    public static <UReq, URes> GsonRequestBuilder<UReq, URes> post(Class<UReq> reqClass, Class<URes> resClass) {
        GsonRequestBuilder<UReq, URes> getRequest = new GsonRequestBuilder<>(reqClass, resClass);
        getRequest.setMethod(Method.POST);
        return getRequest;
    }

    public static <UReq, URes> GsonRequestBuilder<UReq, URes> put(Class<UReq> reqClass, Class<URes> resClass) {
        GsonRequestBuilder<UReq, URes> getRequest = new GsonRequestBuilder<>(reqClass, resClass);
        getRequest.setMethod(Method.PUT);
        return getRequest;
    }

    public static <UReq, URes> GsonRequestBuilder<UReq, URes> delete(Class<URes> resClass) {
        GsonRequestBuilder<UReq, URes> deleteRequest = new GsonRequestBuilder<>(null, resClass);
        deleteRequest.setMethod(Method.DELETE);
        return deleteRequest;
    }

    private GsonRequestBuilder<TReq, TRes> setMethod(int method) {
        this.method = method;
        return this;
    }

    public GsonRequestBuilder<TReq, TRes> setUrl(String url) {
        this.url = url;
        return this;
    }

    public GsonRequestBuilder<TReq, TRes> setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public GsonRequestBuilder<TReq, TRes> setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public GsonRequestBuilder<TReq, TRes> setRequestObject(TReq requestObject) {
        this.requestObject = requestObject;
        return this;
    }

    public GsonRequestBuilder<TReq, TRes> setListener(Listener<TRes> listener) {
        this.listener = listener;
        return this;
    }

    public GsonRequestBuilder<TReq, TRes> setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public Request<TRes> build() {
        if (method == Method.GET) {
            return new GsonGetRequest<TRes>(url, resClass, headers, params, listener, errorListener);
        } else if(method == Method.POST || method == Method.PUT) {
            boolean isPost = method == Method.POST;
            return new GsonPostOrPutRequest<TReq, TRes>(isPost, url, reqClass, resClass, headers, requestObject, listener, errorListener);
        } else if(method == Method.DELETE) {
            return new GsonDeleteRequest<TRes>(url, resClass, headers, listener, errorListener);
        } else {
            return null;
        }
    }
}

