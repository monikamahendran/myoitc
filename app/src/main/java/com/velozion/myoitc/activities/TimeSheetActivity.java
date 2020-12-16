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
import com.velozion.myoitc.adapter.TimeSheetAdapter;
import com.velozion.myoitc.bean.TimeSheetBean;
import com.velozion.myoitc.interfaces.Listeners;
import com.velozion.myoitc.network.StringMethodRequest;
import com.velozion.myoitc.utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeSheetActivity extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {

    RecyclerView rcv_time_sheet;
    LinearLayout nodata_ll;
    ArrayList<TimeSheetBean> timeSheetBeanArrayList = new ArrayList<>();
    TimeSheetAdapter timeSheetAdapter;

    int type;
    String emp_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setToolbarRequired( true );
        setToolbarTitle( getString( R.string.time_sheet ) );
        setHomeMenuRequired( false );
        setContentView( R.layout.activity_time_sheet );

        type=getIntent().getIntExtra("type",1);
        emp_id=getIntent().getStringExtra("id");

        rcv_time_sheet = findViewById( R.id.rcv_time_sheet );
        nodata_ll=findViewById(R.id.nodata_ll);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext(), RecyclerView.VERTICAL, false );
        rcv_time_sheet.setLayoutManager( linearLayoutManager );

        getTimeSheetValue();
    }


    private void setAdapter() {
        timeSheetAdapter = new TimeSheetAdapter( TimeSheetActivity.this, timeSheetBeanArrayList );
        rcv_time_sheet.setAdapter( timeSheetAdapter );
    }

    private void getTimeSheetValue() {

        if (type==1)//employer
        {
            try {
                Map<String, String> mParams = new HashMap<>();

                String URl="http://www.myoitc.com/beta/?option=com_ajax&group=cmajax&plugin=cmmap&type=timesheet_details&empid="+emp_id+"&ttype=3&format=json&ignoreMessages=0";
                Log.d("ResponseUrl",URl);

                StringMethodRequest postMethodRequest = new StringMethodRequest( this, Request.Method.GET, URl, mParams, true, this, this, this, 1 );
                MyApplication.getInstance().addToRequestQueue( postMethodRequest );
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else  if (type==2)//emploee
        {
            try {
                Map<String, String> mParams = new HashMap<>();

                String URl="http://www.myoitc.com/beta/?option=com_ajax&group=cmajax&plugin=cmmap&type=timesheet_details&ttype="+emp_id+"&format=json&ignoreMessages=0";
                Log.d("ResponseUrl",URl);

                StringMethodRequest postMethodRequest = new StringMethodRequest( this, Request.Method.GET, URl, mParams, true, this, this, this, 1 );
                MyApplication.getInstance().addToRequestQueue( postMethodRequest );

            } catch (Exception e) {
                e.printStackTrace();
            }
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
            if (newJsObj.getBoolean( "success" )) {
                timeSheetBeanArrayList = new ArrayList<>();

                JSONArray outJson=newJsObj.optJSONArray("data");

                Log.d("ResponseS",newJsObj.toString());

                if (outJson.length()>0) {

                    JSONObject outerObject = outJson.optJSONObject(0);

                    JSONArray jsonArray = outerObject.optJSONArray( "details" );

                    if (jsonArray!=null)
                    {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject( i );
                            timeSheetBeanArrayList.add( new TimeSheetBean( jsonObject.optString( "date" ),
                                    jsonObject.optString( "day" ),
                                    jsonObject.optString( "timeIn" ),
                                    jsonObject.optString( "timeOut" ),
                                    jsonObject.optString( "paycode" ),
                                    jsonObject.optString( "serviceType" ),
                                    jsonObject.optString( "clientInitial" ),
                                    jsonObject.optString( "clientDesignee" ),
                                    jsonObject.optString( "totalHours" ),
                                    jsonObject.optString( "verifiedBy" ),
                                    jsonObject.optString( "verifiedOn" ),
                                    jsonObject.optString( "status" ),
                                    jsonObject.optString( "employee" ) ) );
                        }
                    }



                    if (timeSheetBeanArrayList.size()>0)
                    {
                        setAdapter();

                        rcv_time_sheet.setVisibility(View.VISIBLE);
                        nodata_ll.setVisibility(View.GONE);

                    }else {

                        rcv_time_sheet.setVisibility(View.GONE);
                        nodata_ll.setVisibility(View.VISIBLE);

                    }



                }


            }
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
                timeSheetAdapter.filter( s );
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                timeSheetAdapter.filter( s );
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
