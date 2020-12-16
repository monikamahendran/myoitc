package com.velozion.myoitc.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.velozion.myoitc.R;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.adapter.HistoryBindingAdapter;
import com.velozion.myoitc.bean.CheckInHistoryBean;
import com.velozion.myoitc.interfaces.Listeners;
import com.velozion.myoitc.network.StringMethodRequest;
import com.velozion.myoitc.utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class HistoryFrag extends Fragment implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;
    private RecyclerView recyclerView;
    private LinearLayout nodata;
    private ArrayList<CheckInHistoryBean> checkInHistoryBeanArrayList;

    HistoryBindingAdapter historyBindingAdapter;

    //  SwipeRefreshLayout swipe_refresh;


    public HistoryFrag() {
    }

    // TODO: Rename and change types and number of parameters
    public static HistoryFrag newInstance(String param1, String param2) {
        HistoryFrag fragment = new HistoryFrag();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString( ARG_PARAM1 );
            String mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {

            checkInHistoryBeanArrayList = new ArrayList<>();
            view = inflater.inflate( R.layout.fragment_history_, container, false );

            recyclerView = view.findViewById( R.id.history_recylerview );
            nodata = view.findViewById( R.id.nodata_ll );
            // swipe_refresh = view.findViewById( R.id.swipe_refresh );
            //  swipe_refresh.setRefreshing( false );

            LinearLayoutManager manager = new LinearLayoutManager( getActivity() );
            recyclerView.setLayoutManager( manager );

            requestServiceList();
            setHasOptionsMenu( true );

          /*  swipe_refresh.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
//                    recyclerView.setAdapter( historyBindingAdapter );
                    setAdapter( checkInHistoryBeanArrayList );
                    swipe_refresh.setRefreshing( false );

                }
            } );*/
        }
        return view;
    }


    private void requestServiceList() {
        try {
            Map<String, String> mParams = new HashMap<>();
            StringMethodRequest postMethodRequest = new StringMethodRequest( getActivity(), Request.Method.POST, Utils.HistoryAPI, mParams, true, this, this, this, 1 );
            MyApplication.getInstance().addToRequestQueue( postMethodRequest );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void TriggerUI(boolean showui) {
        if (showui) {
            recyclerView.setVisibility( View.VISIBLE );
            nodata.setVisibility( View.GONE );
        } else {//show error
            recyclerView.setVisibility( View.GONE );
            nodata.setVisibility( View.VISIBLE );
        }
    }

    private void setAdapter(ArrayList<CheckInHistoryBean> checkInHistoryBeans) {
        if (checkInHistoryBeans.size() > 0) {
            historyBindingAdapter = new HistoryBindingAdapter( getActivity(), checkInHistoryBeans );
            recyclerView.setAdapter( historyBindingAdapter );
            Collections.reverse( checkInHistoryBeans );
            //  historyBindingAdapter.notifyItemInserted( 0 );
            TriggerUI( true );
        } else {
            TriggerUI( false );
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
            if (newJsObj.optJSONArray( "data" ) != null) {

                JSONArray outerArray = newJsObj.getJSONArray( "data" );

                if (outerArray.length() > 0) {
                    JSONObject outerObject = outerArray.optJSONObject( 0 );

                    JSONArray jsonArray = outerObject.getJSONArray( "details" );
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject( i );
                        checkInHistoryBeanArrayList.add( new CheckInHistoryBean( jsonObject.getString( "clientName" ),
                                jsonObject.getString( "service_type" ),
                                jsonObject.getString( "payloadName" ),
                                jsonObject.getString( "lunchTime" ),
                                jsonObject.getString( "clientInitial" ),
                                jsonObject.getJSONObject( "checkin" ).getString( "checkinTime" ),
                                jsonObject.getJSONObject( "checkin" ).getString( "checkinLat" ),
                                jsonObject.getJSONObject( "checkin" ).getString( "checkinLong" ),
                                jsonObject.getJSONObject( "checkin" ).getString( "checkinAddress" ),
                                jsonObject.getJSONObject( "checkout" ).getString( "checkoutTime" ).replace( "null", "-" ),
                                jsonObject.getJSONObject( "checkout" ).getString( "checkoutLat" ).replace( "null", "0" ),
                                jsonObject.getJSONObject( "checkout" ).getString( "checkoutLong" ).replace( "null", "0" ),
                                jsonObject.getJSONObject( "checkout" ).getString( "checkoutAddress" ).replace( "null", "No data found" ),
                                jsonObject.getJSONObject( "checkout" ).getString( "clientSignature" ) ) );
                    }
                    setAdapter( checkInHistoryBeanArrayList );
                }
            }


        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu( menu, inflater );
        inflater.inflate( R.menu.search_menu, menu );
        MenuItem menuItem = menu.findItem( R.id.search_bar );
        final SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                historyBindingAdapter.filter( s );
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                historyBindingAdapter.filter( s );
                searchView.setQueryHint( getResources().getString( R.string.search ) );
                int searchSrcTextId = getResources().getIdentifier( "android:id/search_src_text", null, null );
                EditText searchEditText = searchView.findViewById( searchSrcTextId );
                searchEditText.setTextColor( Color.WHITE );
                searchEditText.setHintTextColor( Color.LTGRAY );
                return true;
            }
        } );
    }


}
