package com.velozion.myoitc.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.velozion.myoitc.BaseActivity;
import com.velozion.myoitc.R;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.adapter.EmployeeListAdapter;
import com.velozion.myoitc.bean.EmployeeListBean;
import com.velozion.myoitc.interfaces.Listeners;
import com.velozion.myoitc.network.StringMethodRequest;
import com.velozion.myoitc.utilities.MyApplication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeListActivity extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {

    RecyclerView rcv_employe_list;
    LinearLayout nodata_ll;
    ArrayList<EmployeeListBean> employeeListBeans;
    private int from = 0;
    EmployeeListAdapter employeeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarRequired(true);
        setHomeMenuRequired(false);
        setToolbarTitle("Employee List");
        setContentView(R.layout.activity_employee_list);
        rcv_employe_list = findViewById(R.id.rcv_employe_list);
        nodata_ll=findViewById(R.id.nodata_ll);

        from = getIntent().getIntExtra("from", 0);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rcv_employe_list.setLayoutManager(linearLayoutManager);

        getEmployeeList();
    }


    private void setAdapter() {
        employeeListAdapter = new EmployeeListAdapter(EmployeeListActivity.this, employeeListBeans, from);
        rcv_employe_list.setAdapter(employeeListAdapter);
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
            if (newJsObj.getBoolean("success")) {
                employeeListBeans = new ArrayList<>();

                JSONArray outJson=newJsObj.optJSONArray("data");

                Log.d("ResponseS",newJsObj.toString());

                if (outJson.length()>0)
                {

                    JSONObject outerObject=outJson.getJSONObject(0);

                    JSONArray jsonArray = outerObject.getJSONArray("details");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        EmployeeListBean employeeListBean = new EmployeeListBean();
                        employeeListBean.setEmp_id(jsonObject.optString("user_id"));
                        employeeListBean.setEmp_name(jsonObject.optString("name"));
                        employeeListBean.setEmp_designation(jsonObject.optString("depname"));
                        employeeListBean.setEmp_comp(jsonObject.optString("companyname"));
                        employeeListBean.setTtype(jsonObject.optString("ttype_value"));
                        employeeListBean.setSubdate(jsonObject.optString("subdate"));
                        employeeListBean.setStatus(jsonObject.optString("status"));
                        employeeListBean.setEmp_work_hrs(jsonObject.optString("total_hours"));

                       /* JSONArray jsonElements = jsonObject.getJSONArray("months");
                        for (int j = 0; j < jsonElements.length(); j++) {
                            JSONObject jsonObject1 = jsonElements.getJSONObject(j);

                            employeeListBean.setEmp_total(jsonObject1.getString("workingDays"));
                            employeeListBean.setEmp_work_hrs(jsonObject1.getString("workingHrs"));
                            JSONArray jsonleavecount = jsonObject1.getJSONArray("holidays");
                            String leaveocunt = String.valueOf(jsonleavecount.length());
                            employeeListBean.setEmp_leave(leaveocunt);
                        }*/
                        employeeListBeans.add(employeeListBean);
                    }

                    if (employeeListBeans.size()>0)
                    {

                        setAdapter();

                        rcv_employe_list.setVisibility(View.VISIBLE);
                        nodata_ll.setVisibility(View.GONE);

                    }else {

                        rcv_employe_list.setVisibility(View.GONE);
                        nodata_ll.setVisibility(View.VISIBLE);

                    }



                }



            }
        }
    }

    private void getEmployeeList() {
        try {
            Map<String, String> mParams = new HashMap<>();

            StringMethodRequest postMethodRequest = new StringMethodRequest(this, Request.Method.POST, Utils.EmployeeListAPI, mParams, true, this, this, this, 1);
            MyApplication.getInstance().addToRequestQueue(postMethodRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.search_menu, menu );
        MenuItem menuItem = menu.findItem( R.id.search_bar );
        final SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                employeeListAdapter.filter( s );
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                employeeListAdapter.filter( s );
                searchView.setQueryHint( getResources().getString( R.string.search ) );
                int searchSrcTextId = getResources().getIdentifier( "android:id/search_src_text", null, null );
                EditText searchEditText = searchView.findViewById( searchSrcTextId );
                searchEditText.setTextColor( Color.WHITE );
                searchEditText.setHintTextColor( Color.LTGRAY );
                return true;
            }
        } );
        return true;
    }

}
