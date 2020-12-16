package com.velozion.myoitc.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.velozion.myoitc.BaseActivity;
import com.velozion.myoitc.R;
import com.velozion.myoitc.adapter.ChoosePackageDetailsAdapter;
import com.velozion.myoitc.bean.ChoosePackageBeans;
import com.velozion.myoitc.interfaces.Listeners;
import com.velozion.myoitc.network.StringMethodRequest;
import com.velozion.myoitc.utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChoosePackagesDetailsActivity extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener{

    protected ArrayList<ChoosePackageBeans> packageBeanArrayList;
    int position = 0;
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setToolbarRequired( true );
        setHomeMenuRequired( false );
        setToolbarTitle( "Choose Packages Details" );
        setContentView( R.layout.choose_packages_details_activity );

         mPager = findViewById( R.id.pager );

        Bundle data = this.getIntent().getExtras();
        if (data != null) {
            packageBeanArrayList = data.getParcelableArrayList( "packageBeanList" );
            position = data.getInt( "position" );
        }



        getPackageDetails();


    }


    private void getPackageDetails() {
        try {
            Map<String, String> mParams = new HashMap<>();

            StringMethodRequest postMethodRequest = new StringMethodRequest(this, Request.Method.GET, "http://myoitc.com/beta/?option=com_ajax&group=cmajax&plugin=cmmap&type=packages_details&package_id="+packageBeanArrayList.get(position).getPacId()+"&format=json&ignoreMessages=0", mParams, true, this, this, this, 1);
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
    public void onPostCompleted(JSONObject response, int msgId) throws JSONException {
        if (msgId == 1) {

            Log.d("ResponseS",response.toString());

            if (response.optString("success").equalsIgnoreCase("true")) {

                if (response.optJSONArray("data") != null) {
                    JSONObject outerobject = response.optJSONArray("data").optJSONObject(0);


                                            /*
                                            {
"id": "1",
"package_name": "Myoitc Cares Agency",
"package_desc": "adssdadasd",
"package_type": "1",
"status": "1",
"type_name": "Program Type"
}
                                             */

                    if (outerobject.getJSONArray("details") != null) {

                        JSONArray jsonArray = outerobject.getJSONArray("details");


                        ArrayList<String> permissionArray = new ArrayList<>();

                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        String dash=jsonObject.optString("pack_dashboard");

                        String[] keys=dash.split(",");


                        for (int j=0;j<keys.length;j++)
                        {

                            JSONObject innerobj = jsonArray.getJSONObject(1);

                            JSONObject jsonObject22=innerobj.optJSONObject(keys[j]);
                            permissionArray.add(jsonObject22.optString("title"));

                        }

                        packageBeanArrayList.get(position).setPermissionArrayList(permissionArray);

                        setAdapter();


                    } else {
                        String msg = response.getJSONObject("messages").getJSONArray("error").get(0).toString();
                        Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();

                    }

                }

            }

            /*if (newJsObj.getBoolean("status")) {
                ArrayList<ChoosePackageBeans> choosePackageBeanArrayList = new ArrayList<>();
                JSONArray jsonArray = newJsObj.getJSONArray("details");
                for (int i = 0; i < jsonArray.length(); i++) {
                    ArrayList<String> permissionArray = new ArrayList<>();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONArray getPremissonArray = jsonObject.getJSONArray("permissions");

                    for (int j = 0; j < getPremissonArray.length(); j++) {
                        JSONObject permissionObj = getPremissonArray.getJSONObject(j);
                        permissionArray.add(permissionObj.getString("name"));
                    }
                    choosePackageBeanArrayList.add(new ChoosePackageBeans(jsonObject.getString("pacId"),
                            jsonObject.getString("pacName"),
                            jsonObject.getString("myoticCareMgmt"),
                            jsonObject.getString("clientsUpTo"),
                            jsonObject.getString("expireInDays"),
                            jsonObject.getString("jobsAllow"),
                            jsonObject.getString("price"),
                            jsonObject.getString("colorScheme"), permissionArray
                    ));
                }
                setAdapter(choosePackageBeanArrayList);
            }*/
        }
    }

    void setAdapter()
    {

        mPager.setAdapter( new ChoosePackageDetailsAdapter( this, packageBeanArrayList ) );
        mPager.setCurrentItem( position );

    }
}
