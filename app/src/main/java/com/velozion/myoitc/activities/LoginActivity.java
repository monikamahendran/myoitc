package com.velozion.myoitc.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.velozion.myoitc.BaseActivity;
import com.velozion.myoitc.CustomRequest;
import com.velozion.myoitc.PreferenceUtil;
import com.velozion.myoitc.R;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.interfaces.Listeners;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {

    private EditText email, password;
    Button login;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_emailid);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login_login);

        animation = AnimationUtils.loadAnimation(this, R.anim.anim_down_to_up);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Email is Empty", Snackbar.LENGTH_LONG).show();
                } else if (password.getText().toString().isEmpty()) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Password is Empty", Snackbar.LENGTH_LONG).show();
                } else {
                    signIn(email.getText().toString(), password.getText().toString());
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void signIn(String username, String pass) {

        Utils.displayCustomDailog(LoginActivity.this);

        Map<String, String> headers = new HashMap<>();
       /* String credentials = PreferenceUtil.getData("username", getActivity()) + ":" + PreferenceUtil.getData("password", getActivity());
        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headers.put("Authorization", auth);*/


        Map<String, String> jsonParams = new HashMap<>();


        Log.d("RespondedData", username+pass);


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        CustomRequest customRequest = new CustomRequest(Request.Method.GET, "http://www.myoitc.com/beta/?option=com_ajax&group=cmajax&plugin=cmmap&type=login&format=json&username="+username+"&password="+pass+"&ignoreMessages=0", jsonParams, headers,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ResponseS", response.toString());
                        try {
                            if (response.getString("success").equalsIgnoreCase("true")) {

                                Utils.dismissCustomDailog();

                                if (response.optJSONArray("data")!=null)//sucess
                                {

                                    if (response.optJSONArray("data").getJSONObject(0).optString("status").equalsIgnoreCase("0"))
                                    {
                                        Toast.makeText(LoginActivity.this, ""+response.optJSONArray("data").getJSONObject(0).optString("message"), Toast.LENGTH_SHORT).show();
                                    }else {
                                        PreferenceUtil.saveUserType("user_type",response.optJSONArray("data").getJSONObject(0).optJSONObject("details").optInt("userType"),getApplicationContext());
                                        PreferenceUtil.saveData("user_id",response.optJSONArray("data").getJSONObject(0).optJSONObject("details").optString("user_id"),getApplicationContext());
                                        PreferenceUtil.saveData("username",response.optJSONArray("data").getJSONObject(0).optJSONObject("details").optString("username"),getApplicationContext());
                                        PreferenceUtil.saveData("email",response.optJSONArray("data").getJSONObject(0).optJSONObject("details").optString("email"),getApplicationContext());
                                        PreferenceUtil.saveData("fullname",response.optJSONArray("data").getJSONObject(0).optJSONObject("details").optString("fullname"),getApplicationContext());
                                        PreferenceUtil.saveData("token",response.optJSONArray("data").getJSONObject(0).optJSONObject("details").optString("token"),getApplicationContext());

                                        PreferenceUtil.saveData("password",password.getText().toString(),getApplicationContext());

                                        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                                        finish();


                                    }



                                } else {

                                    String msg = response.optString("message").toString();
                                    Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();
                                }


                            } else {

                                Utils.dismissCustomDailog();
                                Toast.makeText(getApplicationContext(), "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            Utils.dismissCustomDailog();
                            Toast.makeText(getApplicationContext(), "Json Error:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ResponseE", error.toString());

                        Utils.dismissCustomDailog();
                        Toast.makeText(getApplicationContext(), "Volley Error:\n" + error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        requestQueue.add(customRequest);

/*
        if (username.equalsIgnoreCase("employer") && pass.equalsIgnoreCase("demo")) {
            PreferenceUtil.saveUserType("user_type", 1, getApplicationContext());
        } else if (username.equalsIgnoreCase("employee") && pass.equalsIgnoreCase("demo")) {
            PreferenceUtil.saveUserType("user_type", 2, getApplicationContext());
        } else if (username.equalsIgnoreCase("manager") && pass.equalsIgnoreCase("demo")) {
            PreferenceUtil.saveUserType("user_type", 3, getApplicationContext());
        } else if (username.equalsIgnoreCase("supervisor") && pass.equalsIgnoreCase("demo")) {
            PreferenceUtil.saveUserType("user_type", 4, getApplicationContext());
        }  else if (username.equalsIgnoreCase("provider") && pass.equalsIgnoreCase("demo")) {
            PreferenceUtil.saveUserType("user_type", 5, getApplicationContext());
        }  else if (username.equalsIgnoreCase("client") && pass.equalsIgnoreCase("demo")) {
            PreferenceUtil.saveUserType("user_type", 6, getApplicationContext());
        }else {
            Toast.makeText(getApplicationContext(), "Kindly Check Your Credential", Toast.LENGTH_LONG).show();
            return;
        }

        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
        finish();*/
        /*try {
            Map<String, String> mParams = new HashMap<>();
            mParams.put("username", username);
            mParams.put("password", pass);
            StringMethodRequest postMethodRequest = new StringMethodRequest(this, Request.Method.GET, Utils.LoginAPI, mParams, true, this, this, this, 1);
            MyApplication.getInstance().addToRequestQueue(postMethodRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }

    @Override
    public void onPostCompleted(JSONObject newJsObj, int msgId) throws JSONException {
        if (msgId == 1) {
            Log.d("ResponseS", newJsObj.toString());
            try {
                if (newJsObj.getString("success").equalsIgnoreCase("true")) {
                    Utils.dismissCustomDailog();
                    if (newJsObj.getJSONObject("data") != null) {
                        String msg = newJsObj.getJSONObject("messages").getJSONArray("success").get(0).toString();
                        Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();

                        JSONObject object = newJsObj.getJSONObject("data");
                        PreferenceUtil.saveData("userid", object.getString("user_id"), getApplicationContext());
                        PreferenceUtil.saveData("username", object.getString("username"), getApplicationContext());
                        PreferenceUtil.saveData("password", password.getText().toString(), getApplicationContext());
                        PreferenceUtil.saveData("email", object.getString("email"), getApplicationContext());
                        PreferenceUtil.saveData("fullname", object.getString("fullname"), getApplicationContext());

                        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                        finish();
                    } else {
                        String msg = newJsObj.getJSONObject("messages").getJSONArray("error").get(0).toString();
                        Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Utils.dismissCustomDailog();
                    Toast.makeText(LoginActivity.this, "" + newJsObj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Utils.dismissCustomDailog();
                Toast.makeText(getApplicationContext(), "Json Error:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
