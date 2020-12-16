package com.velozion.myoitc;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JAGADISH on 8/16/2018.
 */

public class CustomRequest extends Request<JSONObject>
{

    private Response.Listener<JSONObject> listener;
    private Map<String,String> params;
    private Map<String,String> headers;


    public CustomRequest(String url, Map<String,String> params,
                         Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super( Method.POST, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        this.headers=new HashMap<>();
    }

    public CustomRequest(int method, String url, Map<String, String> params,
                         Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        this.headers=new HashMap<>();
    }

    public CustomRequest(int method, String url, Map<String, String> params, Map<String, String> headers,
                         Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        this.headers=headers;
    }

    @Override
    protected Map<String, String> getParams()
    {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try
        {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JSONException je)
        {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }
}
