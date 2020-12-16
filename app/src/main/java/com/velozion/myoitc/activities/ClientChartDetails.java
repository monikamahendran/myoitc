package com.velozion.myoitc.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.tabs.TabLayout;
import com.velozion.myoitc.BaseActivity;
import com.velozion.myoitc.R;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.bean.ClientChartBean;
import com.velozion.myoitc.bean.ClientChartTaskListBean;
import com.velozion.myoitc.databinding.ActivityClientChartDetailsBinding;
import com.velozion.myoitc.fragments.Acitivity_uncompleted;
import com.velozion.myoitc.interfaces.Listeners;
import com.velozion.myoitc.network.StringMethodRequest;
import com.velozion.myoitc.utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientChartDetails extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {

    ClientChartBean clientChartBeans;
    ArrayList<ClientChartTaskListBean> taskListBeans = new ArrayList<>();
    ActivityClientChartDetailsBinding activityClientChartDetailsBinding;

    int pos;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapter adapter;
    TextView clientChart_email;
    String str_clientChartEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setToolbarRequired( true );
        setHomeMenuRequired( false );
        setToolbarTitle( "Client Details" );

        activityClientChartDetailsBinding = DataBindingUtil.setContentView( this, R.layout.activity_client_chart_details );

        tabLayout = findViewById( R.id.tablayout );
        viewPager = findViewById( R.id.viewpager );
        clientChart_email = findViewById( R.id.clientChart_email );


        Bundle data = this.getIntent().getExtras();
        if (data != null) {
            clientChartBeans = data.getParcelable( "client_chart_array_list" );
            /// taskListBeans = data.getParcelableArrayList("client_chart_task_array_list");
            setToolbarTitle( clientChartBeans.getClientName() );
            getClientTaskList();
        }
    }

    private void getClientTaskList() {
        try {
            Map<String, String> mParams = new HashMap<>();

            String url = "http://myoitc.com/beta/?option=com_ajax&group=cmajax&plugin=cmmap&type=clienttask_list&client_id=" + clientChartBeans.getClientId() + "&format=json&ignoreMessages=0";

            StringMethodRequest postMethodRequest = new StringMethodRequest( this, Request.Method.GET, url, mParams, true, this, this, this, 1 );
            MyApplication.getInstance().addToRequestQueue( postMethodRequest );

            Log.d( "RespondedUrl", url );

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

                JSONArray outJson = newJsObj.optJSONArray( "data" );

                Log.d( "ResponseS", newJsObj.toString() );

                if (outJson.length() > 0) {

                    JSONObject outerObject = outJson.optJSONObject( 0 );


                    JSONObject outerJsonObbject = outerObject.optJSONObject( "details" );

                    /*
                    "cliAddress": "millers road",
                "zipcode": "516006",
                "clientId": "36",
                "cliCity": "25824",
                "cliCountry": "",
                "cliMobile": "5495766546",
                "cliMail": "",
                "clientName": "klinton be",
                "cliState": "495",
                "cliImage": "",
                     */

                    if (outerJsonObbject != null) {
                        clientChartBeans.setCliState( outerJsonObbject.optString( "cliState" ) );
                        clientChartBeans.setCliCity( outerJsonObbject.optString( "cliCity" ) );
                        clientChartBeans.setCliCountry( outerJsonObbject.optString( "cliCountry" ) );
                        clientChartBeans.setZipcode( outerJsonObbject.optString( "zipcode" ) );

                        str_clientChartEmail = clientChartBeans.getCliMail();

                        if (str_clientChartEmail.isEmpty()) {
                            clientChart_email.setText( "-" );
                        } else {
                            clientChart_email.setText( str_clientChartEmail );
                        }

                        Utils.ImageLoaderInitialization( this );
                        Utils.LoadImage( clientChartBeans.getCliImage(), activityClientChartDetailsBinding.clntprfimage );

                        JSONArray cliestArray = outerJsonObbject.optJSONArray( "clientTasks" );
                        ArrayList<ClientChartTaskListBean> clientChartTaskListBeans = new ArrayList<>();

                        if (cliestArray != null) {
                            for (int i = 0; i < cliestArray.length(); i++) {
                                JSONObject object1 = cliestArray.getJSONObject( i );
                                clientChartTaskListBeans.add( new ClientChartTaskListBean( object1.optString( "taskName" ),
                                        object1.optString( "scheduleDate" ),
                                        object1.optString( "assignedTo" ),
                                        object1.optString( "status" ) ) );

                            }
                            taskListBeans.clear();
                            taskListBeans.addAll( clientChartTaskListBeans );
                        }

                        if (taskListBeans.size() > 0) {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext(), RecyclerView.VERTICAL, false );
                            //   activityClientChartDetailsBinding.recyclerclientcharprofile.setLayoutManager(linearLayoutManager);
                            activityClientChartDetailsBinding.setClientChartBean( clientChartBeans );

                           /* Bundle bundle = new Bundle();
                            bundle.putParcelable( "clientDetailsArrayList", clientChartBeans );
                            Acitivity_uncompleted fragobj = new Acitivity_uncompleted();
                            fragobj.setArguments( bundle );*/

                            adapter = new TabAdapter( getSupportFragmentManager(), taskListBeans );


                            viewPager.setAdapter( adapter );
                            tabLayout.setupWithViewPager( viewPager );

                        } else {
                            Toast.makeText( getApplicationContext(), "No data found", Toast.LENGTH_SHORT ).show();
                        }

                    }

                }

            }

        }
    }

    class TabAdapter extends FragmentPagerAdapter {
        CharSequence tabname[] = new CharSequence[]{"Pending", "Completed"};
        ArrayList<ClientChartTaskListBean> data;

        public TabAdapter(FragmentManager fm, ArrayList<ClientChartTaskListBean> temp) {
            super( fm );
            data = new ArrayList<>();
            this.data.addAll( temp );
            Log.d( "ResponseS", "" + temp.size() );
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabname[position];
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Acitivity_uncompleted activity_uncompleted = new Acitivity_uncompleted();
                    Bundle bundle = new Bundle();
                    bundle.putString( "type", "1" );
                    bundle.putParcelableArrayList( "data", data );
                    activity_uncompleted.setArguments( bundle );
                    Log.d( "ResponseSent", "" + data.size() );
                    return activity_uncompleted;

                case 1:
                    Acitivity_uncompleted activity_completed = new Acitivity_uncompleted();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString( "type", "2" );
                    bundle2.putParcelableArrayList( "data", data );
                    activity_completed.setArguments( bundle2 );
                    Log.d( "ResponseSent", "" + data.size() );
                    return activity_completed;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabname.length;
        }

    }

}
