package com.velozion.myoitc.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.velozion.myoitc.adapter.EmployeFolderAdapter;
import com.velozion.myoitc.bean.EmployeeFolderBean;
import com.velozion.myoitc.interfaces.Listeners;
import com.velozion.myoitc.network.StringMethodRequest;
import com.velozion.myoitc.utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeFolderActivity extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {

    RecyclerView recyclerView;
    ArrayList<EmployeeFolderBean> employeeFolderBeans = new ArrayList<>();
    EmployeFolderAdapter employeFolderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setToolbarRequired( true );
        setToolbarTitle( "Employee Profile" );
        setContentView( R.layout.activity_employee_folder );

        recyclerView = findViewById( R.id.recycleremployerfolder );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext(), RecyclerView.VERTICAL, false );
        recyclerView.setLayoutManager( linearLayoutManager );

        getEmployeeList();
    }


    private void setAdapter() {
        employeFolderAdapter = new EmployeFolderAdapter( getApplicationContext(), employeeFolderBeans );
        recyclerView.setAdapter( employeFolderAdapter );
    }

    private void getEmployeeList() {
        try {
            Map<String, String> mParams = new HashMap<>();

            StringMethodRequest postMethodRequest = new StringMethodRequest( this, Request.Method.GET, Utils.EmployeeFolderAPI, mParams, true, this, this, this, 1 );
            MyApplication.getInstance().addToRequestQueue( postMethodRequest );
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

            Log.d("ResponseS",newJsObj.toString());

            if (newJsObj.optString( "success" ).equalsIgnoreCase("true")) {
                employeeFolderBeans = new ArrayList<>();

                JSONArray  outerjsonarray=newJsObj.optJSONArray("data");

                JSONObject outerobject=outerjsonarray.optJSONObject(0);

                JSONArray jsonArray = outerobject.getJSONArray( "details" );

                Log.d("ResponseSINN",""+jsonArray.length());

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject( i );

                    Log.d("ResponseSinnerObject",jsonObject.toString());

                    /*
                    {"id":"496",
                    "name":"V Employer",
                    "username":"vemployer",
                    "email":"test@velozion.com",
                    "password":"$2y$10$PPX0hprRAA216s4d2HaLaOwGV98OLEuJLMdQtAzWGqiXh1aZBFKzW",
                    "block":"0",
                    "sendEmail":"0",
                    "registerDate":"2018-10-24 12:56:23",
                    "lastvisitDate":"2020-06-08 13:45:17",
                    "activation":"","params":"{}",
                    "lastResetTime":"0000-00-00 00:00:00",
                    "resetCount":"0","otpKey":"","otep":"","requireReset":"0",
                    "image_file":"",
                    "sync":"0",
                    "address":"vemployer sdf",
                    "street":"#5\/1, 2nd ",
                    "lastname":"R",
                    "phoneno":"23434343434",
                    "state":"3254",
                    "city":"Bangalore",
                    "zipcode":"560016",
                    "vehicle":"",
                    "groupname":"Administrator",
                    "roletitle":"Employer",
                    "asemployee":"1"
                    ,"asjobid":"0",
                    "employeecompanyname":"Myoitc",
                    "companyname":"Myoitc",
                    "role":"1",
                    "employeejobtitle":null}
                     */


                    employeeFolderBeans.add( new EmployeeFolderBean( jsonObject.optString( "id" ),
                            jsonObject.optString( "name" ),
                            jsonObject.optString( "username" ),
                            jsonObject.getString( "companyname" ).replace("null", "-"),
                            jsonObject.optString( "dept" ),//pending
                            jsonObject.optString( "groupname" ),
                            jsonObject.optString( "roletitle" ).replace("null", "-"),
                            jsonObject.getString( "employeejobtitle" ).replace("null", "-"),//pending
                            jsonObject.optString( "image_file") ) );
                }
                setAdapter();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.search_menu, menu );
        MenuItem menuItem = menu.findItem( R.id.search_bar );
        final SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                employeFolderAdapter.filter( s );
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                employeFolderAdapter.filter( s );
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
