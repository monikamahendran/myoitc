package com.velozion.myoitc.viewModel;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.velozion.myoitc.CustomRequest;
import com.velozion.myoitc.PreferenceUtil;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.bean.CheckInFormClientData;
import com.velozion.myoitc.bean.CheckInFormPayType;
import com.velozion.myoitc.bean.CheckInFormServiceData;
import com.velozion.myoitc.bean.PackageTypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyViewModel extends ViewModel {

    private MutableLiveData<String> Failuremessage = new MutableLiveData<>();
    private MutableLiveData<String> jsonError = new MutableLiveData<>();
    private MutableLiveData<String> volleyError = new MutableLiveData<>();

    private MutableLiveData<ArrayList<CheckInFormClientData>> ClientList;
    private MutableLiveData<ArrayList<CheckInFormServiceData>> ServicesList;
    private MutableLiveData<ArrayList<CheckInFormPayType>> PayTypesList;
    private MutableLiveData<ArrayList<PackageTypeModel>> PackageTypeList;


    //CLIENT LISTING FOR CHECK-IN FORM
    public LiveData<ArrayList<CheckInFormClientData>> getClientsList(final Context context) {

        Utils.displayCustomDailog( context );
        //  Utils.dismissCustomDailog();

        if (ClientList == null) {
            ClientList = new MutableLiveData<>();

            Map<String, String> headers = new HashMap<>();
            String credentials = PreferenceUtil.getData( "username", context ) + ":" + PreferenceUtil.getData( "password", context );
            String auth = "Basic " + Base64.encodeToString( credentials.getBytes(), Base64.NO_WRAP );
            headers.put( "Authorization", auth );

            final ArrayList<CheckInFormClientData> Data = new ArrayList<>();

            Map<String, String> jsonParams = new HashMap<>();
            RequestQueue requestQueue = Volley.newRequestQueue( context );

            Log.d( "RespondedHeader", headers.toString() );

            CustomRequest customRequest = new CustomRequest( Request.Method.GET, Utils.ClientListAPI, jsonParams
                    , headers,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Utils.dismissCustomDailog();

                            Log.d( "ResponseS", response.toString() );
                            // Toast.makeText( context, ""+response.toString(), Toast.LENGTH_SHORT ).show();
                            try {

                                if (response.optString( "success" ).equalsIgnoreCase( "true" )) {

                                    if (response.optJSONArray( "data" ) != null) {
                                        JSONObject outerobject = response.optJSONArray( "data" ).optJSONObject( 0 );

                                        if (outerobject.getJSONArray( "details" ) != null) {
                                            JSONArray jsonArray = outerobject.getJSONArray( "details" );

                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject object = jsonArray.getJSONObject( i );

                                                CheckInFormClientData checkInFormClientData = new CheckInFormClientData();
                                                checkInFormClientData.setId( object.optString( "uid" ) );
                                                checkInFormClientData.setName( object.optString( "name" ) );

                                                checkInFormClientData.setLat( String.valueOf( object.optString( "lat" ) ) );
                                                checkInFormClientData.setLon( String.valueOf( object.optString( "log" ) ) );

                                                Data.add( checkInFormClientData );

                                            }
                                            ClientList.setValue( Data );
                                        } else {
                                            // String msg = response.getJSONObject( "messages" ).getJSONArray( "error" ).get( 0 ).toString();
                                            Toast.makeText( context, "", Toast.LENGTH_SHORT ).show();
                                            ClientList.setValue( null );
                                        }

                                    }

                                } else {

                                    ClientList.setValue( null );
                                    Toast.makeText( context, "No Client Found", Toast.LENGTH_SHORT ).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText( context, "JsonException" + e.getMessage(), Toast.LENGTH_SHORT ).show();
                                jsonError.postValue( e.getMessage() );
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d( "ResponseE", volleyError.toString() );
                            Toast.makeText( context, "VolleyExcel" + error.getMessage(), Toast.LENGTH_SHORT ).show();
                            volleyError.postValue( error.getMessage() );
                        }
                    } );
            customRequest.setRetryPolicy( new DefaultRetryPolicy( 0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
            customRequest.setShouldCache( false );
            requestQueue.add( customRequest );

        }
        return ClientList;
    }

    //SERVICE LISTING FOR CHECK-IN FORM
    public LiveData<ArrayList<CheckInFormServiceData>> getServicesList(final Context context) {

        if (ServicesList == null) {
            ServicesList = new MutableLiveData<>();

            Map<String, String> headers = new HashMap<>();
            String credentials = PreferenceUtil.getData( "username", context ) + ":" + PreferenceUtil.getData( "password", context );
            String auth = "Basic " + Base64.encodeToString( credentials.getBytes(), Base64.NO_WRAP );
            headers.put( "Authorization", auth );

            final ArrayList<CheckInFormServiceData> Data = new ArrayList<>();
            Map<String, String> jsonParams = new HashMap<>();
            RequestQueue requestQueue = Volley.newRequestQueue( context );

            Log.d( "RespondedHeader", headers.toString() );

            CustomRequest customRequest = new CustomRequest( Request.Method.GET, Utils.ServicesListAPI, jsonParams
                    , headers,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d( "ResponseS", response.toString() );
                            try {

                                if (response.optString( "success" ).equalsIgnoreCase( "true" )) {

                                    if (response.optJSONArray( "data" ) != null) {
                                        JSONObject outerobject = response.optJSONArray( "data" ).optJSONObject( 0 );


                                        if (outerobject.getJSONArray( "details" ) != null) {
                                            JSONArray jsonArray = outerobject.getJSONArray( "details" );
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject object = jsonArray.getJSONObject( i );
                                                CheckInFormServiceData checkInFormServiceData = new CheckInFormServiceData();
                                                checkInFormServiceData.setId( object.optString( "id" ) );
                                                checkInFormServiceData.setName( object.optString( "name" ) );

                                                Data.add( checkInFormServiceData );
                                            }
                                            ServicesList.setValue( Data );

                                        } else {
                                            String msg = response.getJSONObject( "messages" ).getJSONArray( "error" ).get( 0 ).toString();
                                            Toast.makeText( context, "" + msg, Toast.LENGTH_SHORT ).show();
                                            ServicesList.setValue( null );
                                        }

                                    }


                                } else {

                                    ServicesList.setValue( null );
                                    Toast.makeText( context, "No Client Found", Toast.LENGTH_SHORT ).show();

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText( context, "JsonException" + e.getMessage(), Toast.LENGTH_SHORT ).show();
                                jsonError.postValue( e.getMessage() );
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d( "ResponseE", volleyError.toString() );
                            Toast.makeText( context, "VolleyExce" + error.getMessage(), Toast.LENGTH_SHORT ).show();
                            volleyError.postValue( error.getMessage() );
                        }
                    } );
            requestQueue.add( customRequest );

        }
        return ServicesList;
    }

    public LiveData<ArrayList<CheckInFormPayType>> getPayTypeList(final Context context) {

        if (PayTypesList == null) {
            PayTypesList = new MutableLiveData<>();

            Map<String, String> headers = new HashMap<>();
            String credentials = PreferenceUtil.getData( "username", context ) + ":" + PreferenceUtil.getData( "password", context );
            String auth = "Basic " + Base64.encodeToString( credentials.getBytes(), Base64.NO_WRAP );
            headers.put( "Authorization", auth );

            final ArrayList<CheckInFormPayType> Data = new ArrayList<>();
            Map<String, String> jsonParams = new HashMap<>();
            RequestQueue requestQueue = Volley.newRequestQueue( context );

            Log.d( "RespondedHeader", headers.toString() );

            CustomRequest customRequest = new CustomRequest( Request.Method.GET, Utils.PayTypeListAPI, jsonParams
                    , headers,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d( "ResponseS", response.toString() );
                            try {

                                if (response.optString( "success" ).equalsIgnoreCase( "true" )) {

                                    if (response.getJSONArray( "data" ) != null) {

                                        JSONArray jsonArray = response.getJSONArray( "data" );

                                        if (jsonArray.length() > 0) {


                                            JSONObject dataobject = jsonArray.optJSONObject( 0 );

                                            JSONArray array = dataobject.optJSONArray( "details" );

                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject object = array.getJSONObject( i );

                                                CheckInFormPayType checkInFormPayType = new CheckInFormPayType();
                                                checkInFormPayType.setId( String.valueOf( object.optInt( "id" ) ) );
                                                checkInFormPayType.setName( object.optString( "name" ) );

                                                Data.add( checkInFormPayType );
                                            }
                                            PayTypesList.setValue( Data );

                                        } else {

                                            PayTypesList.setValue( null );
                                        }


                                    } else {
                                        String msg = response.getJSONObject( "messages" ).getJSONArray( "error" ).get( 0 ).toString();
                                        Toast.makeText( context, "" + msg, Toast.LENGTH_SHORT ).show();
                                        PayTypesList.setValue( null );
                                    }


                                } else {

                                    PayTypesList.setValue( null );
                                    Toast.makeText( context, "No Client Found", Toast.LENGTH_SHORT ).show();

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText( context, "JsonException" + e.getMessage(), Toast.LENGTH_SHORT ).show();
                                jsonError.postValue( e.getMessage() );
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d( "ResponseE", volleyError.toString() );
                            Toast.makeText( context, "VolleyExce" + error.getMessage(), Toast.LENGTH_SHORT ).show();
                            volleyError.postValue( error.getMessage() );
                        }
                    } );
            requestQueue.add( customRequest );

        }
        return PayTypesList;
    }

    public LiveData<ArrayList<PackageTypeModel>> getPackageTypeList(final Context context) {

        if (PackageTypeList == null) {
            PackageTypeList = new MutableLiveData<>();

            Map<String, String> headers = new HashMap<>();
            String credentials = PreferenceUtil.getData( "username", context ) + ":" + PreferenceUtil.getData( "password", context );
            String auth = "Basic " + Base64.encodeToString( credentials.getBytes(), Base64.NO_WRAP );
            headers.put( "Authorization", auth );

            final ArrayList<PackageTypeModel> Data = new ArrayList<>();

            Map<String, String> jsonParams = new HashMap<>();
            RequestQueue requestQueue = Volley.newRequestQueue( context );

            Log.d( "RespondedHeader", headers.toString() );

            CustomRequest customRequest = new CustomRequest( Request.Method.POST, Utils.PackageTypeApi, jsonParams
                    , headers,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d( "ResponseS", response.toString() );
                            try {

                                if (response.optString( "success" ).equalsIgnoreCase( "true" )) {

                                    if (response.optJSONArray( "data" ) != null) {
                                        JSONObject outerobject = response.optJSONArray( "data" ).optJSONObject( 0 );


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

                                        if (outerobject.getJSONArray( "details" ) != null) {

                                            JSONArray jsonArray = outerobject.getJSONArray( "details" );

                                            JSONArray array = jsonArray.optJSONArray( 0 );

                                            for (int i = 0; i < array.length(); i++) {


                                                JSONObject object = array.getJSONObject( i );

                                                PackageTypeModel model = new PackageTypeModel();
                                                model.setId( object.optString( "id" ) );
                                                model.setPackage_name( object.optString( "package_name" ) );
                                                model.setPackage_desc( object.optString( "package_desc" ) );
                                                model.setPackage_type( object.optInt( "package_type" ) );
                                                model.setStatus( object.optInt( "status" ) );
                                                model.setType_name( object.optString( "type_name" ) );

                                                Data.add( model );
                                            }
                                            PackageTypeList.setValue( Data );

                                        } else {
                                            String msg = response.getJSONObject( "messages" ).getJSONArray( "error" ).get( 0 ).toString();
                                            Toast.makeText( context, "" + msg, Toast.LENGTH_SHORT ).show();
                                            PackageTypeList.setValue( null );
                                        }

                                    }


                                } else {

                                    PackageTypeList.setValue( null );
                                    Toast.makeText( context, "No Client Found", Toast.LENGTH_SHORT ).show();

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText( context, "JsonException" + e.getMessage(), Toast.LENGTH_SHORT ).show();
                                jsonError.postValue( e.getMessage() );
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Log.d( "ResponseE", error.toString() );
                            Toast.makeText( context, "VolleyExce" + error.getMessage(), Toast.LENGTH_SHORT ).show();
                            volleyError.postValue( error.getMessage() );
                        }
                    } );
            requestQueue.add( customRequest );

        }
        return PackageTypeList;
    }


}
