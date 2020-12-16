package com.velozion.myoitc.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.velozion.myoitc.BaseActivity;
import com.velozion.myoitc.R;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.adapter.ServiceAcknowledgementAdapter;
import com.velozion.myoitc.bean.ClientAcknowledgementBean;
import com.velozion.myoitc.interfaces.Listeners;
import com.velozion.myoitc.network.StringMethodRequest;
import com.velozion.myoitc.utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceAcknowledgementActivity extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {

    RecyclerView rcv_serviceAcknowledgement;
    ArrayList<ClientAcknowledgementBean> acknowledgementBeans;
    ServiceAcknowledgementAdapter acknowledgementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarRequired(true);
        setHomeMenuRequired(false);
        setToolbarTitle("Request Service");
        setContentView(R.layout.activity_service_acknowledgement);
        rcv_serviceAcknowledgement = findViewById(R.id.rcv_serviceAcknowledgement);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rcv_serviceAcknowledgement.setLayoutManager(linearLayoutManager);

        getServiceList();

    }


    public void getServiceList() {
        try {
            Map<String, String> mParams = new HashMap<>();
            StringMethodRequest postMethodRequest = new StringMethodRequest(this, Request.Method.GET, Utils.ClientServiceAcknowledgementAPI, mParams, true, this, this, this, 1);
            MyApplication.getInstance().addToRequestQueue(postMethodRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            if (newJsObj.getBoolean("status")) {
                acknowledgementBeans = new ArrayList<>();
                JSONArray jsonArray = newJsObj.getJSONArray("details");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    acknowledgementBeans.add(new ClientAcknowledgementBean(jsonObject.getString("clientName"),
                            jsonObject.getString("requestType"),
                            jsonObject.getString("serviceType"),
                            jsonObject.getString("serviceStartDate"),
                            jsonObject.getString("ServiceEndDate"),
                            jsonObject.getString("totalHrs"),
                            jsonObject.getString("admissionStatus"),
                            jsonObject.getString("paymentStatus"),
                            jsonObject.getString("totalCost")));
                }
                setAdapter();
            }
        }
    }

    public void setAdapter() {
        acknowledgementAdapter = new ServiceAcknowledgementAdapter(ServiceAcknowledgementActivity.this, acknowledgementBeans);
        rcv_serviceAcknowledgement.setAdapter(acknowledgementAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        final SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                acknowledgementAdapter.getFilter().filter(s);
                searchView.setQueryHint(getResources().getString(R.string.search));
                int searchSrcTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
                EditText searchEditText = searchView.findViewById(searchSrcTextId);
                searchEditText.setTextColor(Color.WHITE);
                searchEditText.setHintTextColor(Color.LTGRAY);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search_bar:
                if (item.getItemId() == R.id.search_bar) {
                    return true;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
