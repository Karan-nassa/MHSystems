package com.mh.systems.sandyLodge.api.volley;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by karan@mh.co.in on 11/26/2015
 * to POST user information on server.
 */
public class RequestJsonString extends Request<String> {

    private Response.Listener<String> jsonObjectListener;
    private Map<String, String> params;
    String jsonparams;
    String mString;


    public RequestJsonString(int method, String url, Map<String, String> params,
                             Response.ErrorListener errorListener, Response.Listener<String> reponseListener) {
        super(method, url, errorListener);
        this.jsonObjectListener = reponseListener;
        this.params = params;
    }

    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new String(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.e("RequestJsonObject ", e.toString());
            return Response.error(new ParseError(e));
        } catch (Exception je) {
            Log.e("RequestJsonObject ", je.toString());
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        jsonObjectListener.onResponse(response);
    }
}
