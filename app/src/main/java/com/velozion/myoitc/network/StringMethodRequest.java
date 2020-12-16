package com.velozion.myoitc.network;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.velozion.myoitc.AppLog;
import com.velozion.myoitc.PreferenceUtil;
import com.velozion.myoitc.ProgressHUD;
import com.velozion.myoitc.interfaces.Listeners;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StringMethodRequest extends StringRequest {

    private Map<String, String> stringRequest;
    private int msgId = 0;
    private Response.Listener<String> listener;
    private Listeners.POSTMethodListener methodListener;
    private boolean isHideLoading = false;
    private Activity getActivityContext;
    //ProgressDialog mProgressDialog;
    private ProgressHUD mProgressHUD;

    Map<String, String> headers = new HashMap<>();


    public StringMethodRequest(Activity getActivityContext, int method, String url,
                               Map<String, String> stringRequest, boolean isHideLoading,
                               Response.Listener<String> listener, Listeners.POSTMethodListener methodListener,
                               Response.ErrorListener errorListener, int msgId) {
        super(method, url, listener, errorListener);
        this.getActivityContext = getActivityContext;
        this.msgId = msgId;
        this.listener = listener;
        this.isHideLoading = isHideLoading;
        this.stringRequest = stringRequest;
        this.methodListener = methodListener;
        showProgressDialogBox();

        String credentials = PreferenceUtil.getData("username", getActivityContext) + ":" + PreferenceUtil.getData("password", getActivityContext);
        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headers.put("Authorization", auth);

        Log.d("RespondedHeader",headers.toString());
    }

    private void showProgressDialogBox() {
        // TODO Auto-generated method stub
        if (isHideLoading) {
            mProgressHUD = ProgressHUD.show(getActivityContext, "", true, true, null);
        }
    }

    @Override
    protected void deliverResponse(String response) {
        JSONObject newJsObj = null;
        try {
            newJsObj = new JSONObject(response);

            AppLog.v("response:", "" + newJsObj.toString());

            if (mProgressHUD != null && mProgressHUD.isShowing()) {
                mProgressHUD.dismiss();
            }
            listener.onResponse(response);

            try {
                methodListener.onPostCompleted(newJsObj, msgId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public Map<String, String> getHeaders() throws AuthFailureError {
////        Log.e("HEADERVALUE", "" + CommonUtils.getHeaders(getActivityContext).toString());
////        return CommonUtils.getHeaders(getActivityContext);
//    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data);
            AppLog.e("Response", jsonString);
            return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            // AppLog.v("UnsupportedEncodingException", "" + e);
            if (mProgressHUD.isShowing()) {
                mProgressHUD.dismiss();
            }
            return Response.error(new ParseError(e));
        }
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return stringRequest;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        // TODO Auto-generated method stub
        if (mProgressHUD != null && mProgressHUD.isShowing()) {
            mProgressHUD.dismiss();
        }

        if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError || volleyError instanceof NetworkError) {
            getActivityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    CommonUtils.showToast(getActivityContext, "Network Error. Please check your internet connection.");
                }
            });
        } else if (volleyError.networkResponse.statusCode == 400) {

        } else if (volleyError.networkResponse.statusCode == 504 || volleyError instanceof ServerError) {

        }/*else if (volleyError.networkResponse.statusCode == 401){
            PreferenceManager.getInstance(getActivityContext).clearUserDetails();
            Intent intent=new Intent(getActivityContext,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivityContext.startActivity(intent);
        }*/
        return super.parseNetworkError(volleyError);
    }
}
