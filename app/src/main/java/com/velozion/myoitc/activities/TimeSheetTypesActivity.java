package com.velozion.myoitc.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.velozion.myoitc.BaseActivity;
import com.velozion.myoitc.R;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.adapter.TimeSheetAdapter;
import com.velozion.myoitc.adapter.TimeSheetTypeAdapter;
import com.velozion.myoitc.bean.TimeSheetBean;
import com.velozion.myoitc.bean.TimeSheetTypeModel;
import com.velozion.myoitc.interfaces.Listeners;
import com.velozion.myoitc.network.StringMethodRequest;
import com.velozion.myoitc.utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeSheetTypesActivity extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener  {

    RecyclerView rcv_time_sheet_types;
    LinearLayout nodata_ll;
    ArrayList<TimeSheetTypeModel> timeSheetBeanArrayList = new ArrayList<>();
    TimeSheetTypeAdapter timeSheetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarRequired( true );
        setToolbarTitle( "TimeSheet Types");
        setHomeMenuRequired( false );
        setContentView( R.layout.activity_time_sheet_types );


        rcv_time_sheet_types = findViewById( R.id.rcv_time_sheet_types );
        nodata_ll=findViewById(R.id.nodata_ll);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext(), RecyclerView.VERTICAL, false );
        rcv_time_sheet_types.setLayoutManager( linearLayoutManager );

        getTimeSheetValueTypes();
    }

    private void setAdapter() {
        timeSheetAdapter = new TimeSheetTypeAdapter( TimeSheetTypesActivity.this, timeSheetBeanArrayList );
        rcv_time_sheet_types.setAdapter( timeSheetAdapter );
    }

    private void getTimeSheetValueTypes() {
        try {
            Map<String, String> mParams = new HashMap<>();


            StringMethodRequest postMethodRequest = new StringMethodRequest( this, Request.Method.GET, Utils.TimeSheetAPITypes, mParams, true, this, this, this, 1 );
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
            if (newJsObj.getBoolean( "success" )) {
                timeSheetBeanArrayList = new ArrayList<>();

                JSONArray outJson=newJsObj.optJSONArray("data");

                Log.d("ResponseS",newJsObj.toString());

                if (outJson.length()>0) {

                    JSONObject outerObject = outJson.optJSONObject(0);

                    JSONArray jsonArray = outerObject.optJSONArray( "details" );

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject( i );

                        TimeSheetTypeModel model=new TimeSheetTypeModel();
                        model.setId(jsonObject.optString("id"));
                        //model.setId(String.valueOf((i+1)));
                        model.setName(jsonObject.optString("name"));
                        timeSheetBeanArrayList.add(model);
                    }



                    if (timeSheetBeanArrayList.size()>0)
                    {
                        setAdapter();

                        rcv_time_sheet_types.setVisibility(View.VISIBLE);
                        nodata_ll.setVisibility(View.GONE);

                    }else {

                        rcv_time_sheet_types.setVisibility(View.GONE);
                        nodata_ll.setVisibility(View.VISIBLE);

                    }



                }


            }
        }
    }
}
