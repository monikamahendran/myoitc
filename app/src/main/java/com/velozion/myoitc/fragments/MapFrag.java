package com.velozion.myoitc.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.velozion.myoitc.CustomRequest;
import com.velozion.myoitc.PreferenceUtil;
import com.velozion.myoitc.R;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.bean.CheckInFormClientData;
import com.velozion.myoitc.bean.CheckInFormPayType;
import com.velozion.myoitc.bean.CheckInFormServiceData;
import com.velozion.myoitc.viewModel.MyViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import pl.droidsonroids.gif.GifImageView;


public class MapFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;
    private GoogleMap mMap;

    private GoogleApiClient googleApiClient;
    private final static int REQUEST_LOCATION = 199;
    private final static int REQUEST_ENABLE_GPS = 300;

    private LocationManager manager;

    private double latitude, longitude;

    private TextView chekin, checkout;

    private double currentLat = 13.021040, currentLong = 77.679190;

    GifImageView progressbar_gif;

    Bitmap signatureBitmap;
    private String encodedImage, initial, locationAddres, longittu, lattitu;

    private Context context;
    private LinearLayout info_ll;
    private TextView c_date, c_time, c_client, c_paycode, c_initial, c_service, c_lunch;

    private ArrayList<CheckInFormClientData> checkInFormClientDataArrayList = new ArrayList<>();
    private ArrayList<CheckInFormServiceData> servicesDataArrayList = new ArrayList<>();
    private ArrayList<CheckInFormPayType> paytypeDataArrayList = new ArrayList<>();

    private MyViewModel viewModel;


    private ArrayList<String> clients = new ArrayList<>();
    private ArrayList<String> services = new ArrayList<>();
    private ArrayList<String> payloads = new ArrayList<>();
    private ArrayList<String> lunch_timings = new ArrayList<>();

    private TextInputEditText client_initial;

    private int selected_client_id = -1, selected_service_id = -1, selected_payload_id = -1;
    private String selected_client_name, selected_service_name, selected_payload_name = "", selected_lunch_time = "", selected_latitude, selected_longitude;


    public MapFrag() {
        // Required empty public constructor
    }

    public static MapFrag newInstance(String param1, String param2) {
        MapFrag fragment = new MapFrag();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    public static MapFrag newInstance() {

        return new MapFrag();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate( R.layout.fragment_map_, container, false );


            viewModel = ViewModelProviders.of( this ).get( MyViewModel.class );

            SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById( R.id.map );

            chekin = view.findViewById( R.id.checkin );
            checkout = view.findViewById( R.id.checkout );
            info_ll = view.findViewById( R.id.info_ll );

            //   progressbarSample = view.findViewById( R.id.progressbarSample );

            progressbar_gif = view.findViewById( R.id.progressbar_gif );


            c_date = view.findViewById( R.id.c_date );
            c_time = view.findViewById( R.id.c_time );
            c_lunch = view.findViewById( R.id.c_lunch_time );
            c_client = view.findViewById( R.id.c_client );
            c_initial = view.findViewById( R.id.c_initial );
            c_paycode = view.findViewById( R.id.c_payocde );
            c_service = view.findViewById( R.id.c_service );


            supportMapFragment.getMapAsync( new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap googleMap) {

                    mMap = googleMap;
                    mMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );
                    //mMap.getUiSettings().setZoomControlsEnabled(true);
                    //mMap.getUiSettings().setZoomGesturesEnabled(true);
                    mMap.getUiSettings().setCompassEnabled( false );
                    mMap.getUiSettings().setScrollGesturesEnabled( true );
                    mMap.getUiSettings().setAllGesturesEnabled( false );

                    CheckGpsConnection();

                }
            } );

            progressbar_gif.setVisibility( View.VISIBLE );
            LoadData();
            displayCheckout();
            chekin.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkInFormClientDataArrayList.size() > 0) {
                        showChekinDialog();
                    } else {
                        Toast.makeText( context, "Client Not Found", Toast.LENGTH_SHORT ).show();
                    }
                }
            } );

            checkout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showCheckOutDialog();

                }
            } );
        }
        return view;
    }

    private void displayCheckout() {
        initial = PreferenceUtil.getData( "service_name", context );
        if (initial.length() > 0) {
            chekin.setVisibility( View.GONE );
            checkout.setVisibility( View.VISIBLE );
        }
    }

    private void LoadData() {

        viewModel.getClientsList( getActivity() ).observe( getActivity(), new Observer<ArrayList<CheckInFormClientData>>() {
            @Override
            public void onChanged(ArrayList<CheckInFormClientData> checkInFormClientData) {
                checkInFormClientDataArrayList.clear();
                clients.clear();

                if (checkInFormClientData != null) {
                    progressbar_gif.setVisibility( View.INVISIBLE );
                    if (checkInFormClientData.size() > 0) {
                        checkInFormClientDataArrayList.addAll( checkInFormClientData );
                        //clients.add("Select Client");
                        for (CheckInFormClientData client : checkInFormClientData) {
                            clients.add( client.getName() );
                        }
                    }
                }

            }
        } );

        viewModel.getServicesList( getContext() ).observe( getActivity(), new Observer<ArrayList<CheckInFormServiceData>>() {
            @Override
            public void onChanged(ArrayList<CheckInFormServiceData> checkInFormServiceData) {

                servicesDataArrayList.clear();
                services.clear();

                if (checkInFormServiceData != null) {

                    if (checkInFormServiceData.size() > 0) {

                        servicesDataArrayList.addAll( checkInFormServiceData );

                        services.add( "Select Service" );

                        for (CheckInFormServiceData checkInFormServiceData1 : checkInFormServiceData) {

                            services.add( checkInFormServiceData1.getName() );

                        }


                    }
                }
            }
        } );

        /*payloads.add("Select Payload");
        payloads.add("Regular");
        payloads.add("Non-Regular");*/


        viewModel.getPayTypeList( getActivity() ).observe( getActivity(), new Observer<ArrayList<CheckInFormPayType>>() {
            @Override
            public void onChanged(ArrayList<CheckInFormPayType> checkInFormPayTypes) {

                paytypeDataArrayList.clear();
                payloads.clear();

                if (checkInFormPayTypes != null) {
                    if (checkInFormPayTypes.size() > 0) {

                        paytypeDataArrayList.addAll( checkInFormPayTypes );

                        payloads.add( "Select Payload" );

                        for (CheckInFormPayType client : checkInFormPayTypes) {
                            payloads.add( client.getName() );

                        }

                    }


                }

            }
        } );


        lunch_timings.add( "Select Lunch Time" );
        lunch_timings.add( "30 mins" );
        lunch_timings.add( "1 hr" );
        lunch_timings.add( "1 30 mins" );
        lunch_timings.add( "2 hrs" );


    }


    private void showChekinDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setCancelable( true );

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = layoutInflater.inflate( R.layout.dailog_checkin, null );


        Spinner client_spinner = view.findViewById( R.id.client_spinner );
        Spinner lunch_spinner = view.findViewById( R.id.lunch_spinner );
        Spinner paycode_spinner = view.findViewById( R.id.paycode_spinner );
        Spinner services_spinner = view.findViewById( R.id.services_spinner );
        client_initial = view.findViewById( R.id.client_initial );

        ArrayAdapter<String> client_adapter = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, clients );
        client_spinner.setAdapter( client_adapter );

        ArrayAdapter<String> payload_adapter = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, payloads );
        paycode_spinner.setAdapter( payload_adapter );

        ArrayAdapter<String> service_adapter = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, services );
        services_spinner.setAdapter( service_adapter );

        ArrayAdapter<String> lunch_adapter = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, lunch_timings );
        lunch_spinner.setAdapter( lunch_adapter );

        TextView cancel = view.findViewById( R.id.cancel );
        TextView submit = view.findViewById( R.id.submit );


        builder.setView( view );

        final AlertDialog alertDialog = builder.create();

        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_client_id == -1) {
                    Toast.makeText( getActivity(), "Select Client", Toast.LENGTH_SHORT ).show();
                } else if (selected_service_id == -1) {
                    Toast.makeText( getActivity(), "Select Services", Toast.LENGTH_SHORT ).show();
                } else if (selected_latitude.equalsIgnoreCase( Double.toString( currentLat ) ) && selected_longitude.equalsIgnoreCase( Double.toString( currentLong ) )) {
                    alertDialog.dismiss();

                    PreferenceUtil.saveData( "service_name", selected_service_name, context );
                    PreferenceUtil.saveData( "lattitu", selected_latitude, context );
                    PreferenceUtil.saveData( "longittu", selected_longitude, context );

                    CheckIn( Utils.getTodayDate( getActivity() ), Utils.getCurrentTiming( getActivity() ), String.valueOf( selected_client_id ), selected_lunch_time, String.valueOf( selected_payload_id ), String.valueOf( selected_service_id ), client_initial.getText().toString(), locationAddres, Double.toString( currentLat ), Double.toString( currentLong ), "1" );

                    chekin.setVisibility( View.GONE );
                    checkout.setVisibility( View.VISIBLE );

                } else {

                    float[] distance = new float[1];

                    Location.distanceBetween( Double.parseDouble( String.valueOf( currentLat ) ), Double.parseDouble( String.valueOf( currentLong ) ), Double.parseDouble( selected_latitude ), Double.parseDouble( selected_longitude ), distance );

                    double radiusInMeters = 2.0 * 1000.0;

                    if (distance[0] > radiusInMeters) {
                       /* Toast.makeText( context,
                                "Outside, distance from center: Location not matched" + distance[0] + " radius: " + radiusInMeters,
                                Toast.LENGTH_LONG ).show();*/
                        Toasty.error( context, "Location not matched", Toasty.LENGTH_SHORT ).show();
                    } else {
                        CheckIn( Utils.getTodayDate( getActivity() ), Utils.getCurrentTiming( getActivity() ), String.valueOf( selected_client_id ), selected_lunch_time, String.valueOf( selected_payload_id ), String.valueOf( selected_service_id ), client_initial.getText().toString(), locationAddres, Double.toString( currentLat ), Double.toString( currentLong ), "1" );

                        alertDialog.dismiss();

                        PreferenceUtil.saveData( "service_name", selected_service_name, context );
                        PreferenceUtil.saveData( "lattitu", selected_latitude, context );
                        PreferenceUtil.saveData( "longittu", selected_longitude, context );

                        chekin.setVisibility( View.GONE );
                        checkout.setVisibility( View.VISIBLE );

                      /*  Toast.makeText( context,
                                "Inside, distance from center: " + distance[0] + " radius: " + radiusInMeters,
                                Toast.LENGTH_LONG ).show();*/
                    }

                }
            }
        } );

        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();

            }
        } );


        client_spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    ///int temp = (position - 1);
                    selected_client_id = Integer.parseInt( checkInFormClientDataArrayList.get( position ).getId() );
                    selected_client_name = checkInFormClientDataArrayList.get( position ).getName();
                    selected_latitude = checkInFormClientDataArrayList.get( position ).getLat();
                    selected_longitude = checkInFormClientDataArrayList.get( position ).getLon();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        } );


        services_spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    int temp = (position - 1);

                    selected_service_id = Integer.parseInt( servicesDataArrayList.get( temp ).getId() );

                    selected_service_name = servicesDataArrayList.get( temp ).getName();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        paycode_spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {


                    int temp = (position - 1);

                    selected_payload_id = Integer.parseInt( paytypeDataArrayList.get( temp ).getId() );

                    selected_payload_name = paytypeDataArrayList.get( temp ).getName();


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        lunch_spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    selected_lunch_time = lunch_timings.get( position );

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        alertDialog.show();
    }

    private void showSignatureDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setCancelable( true );

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = layoutInflater.inflate( R.layout.activity_signatureview, null );

        SignaturePad signView;

        Button btnclr = view.findViewById( R.id.clrSignature );
        Button btnSave = view.findViewById( R.id.saveSignature );

        // ImageView imageView = view.findViewById( R.id.decodeImage );

        signView = view.findViewById( R.id.signView );

        builder.setView( view );

        final AlertDialog alertDialog = builder.create();

        btnSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data1 = PreferenceUtil.getData( "service_name", context );

                if (data1 != null) {
                    PreferenceUtil.removeData( "service_name", context );
                }

                signatureBitmap = signView.getSignatureBitmap();

                int width = signatureBitmap.getWidth();

                int height = signatureBitmap.getHeight();

                float scaleWidth = ((float) 200) / width;

                float scaleHeight = ((float) 200) / height;

                Matrix matrix = new Matrix();

                matrix.postScale( scaleWidth, scaleHeight );
                Bitmap resizedBitmap = Bitmap.createBitmap( signatureBitmap, 0, 0, width, height, matrix, false );

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resizedBitmap.compress( Bitmap.CompressFormat.PNG, 1, baos );

                byte[] imageBytes = baos.toByteArray();

                encodedImage = Base64.encodeToString( imageBytes, Base64.NO_WRAP );

                logLargeString( encodedImage );
             /*   try {
                    str = new String( imageBytes, "UTF-8" );
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                byte[] decodedString = Base64.decode( encodedImage, Base64.DEFAULT );
                Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length );


                 imageView.setImageBitmap( decodedByte );

                 Log.d( "decoded", str );*/

                if (signView.isEmpty()) {
                    Toasty.info( getActivity(), "Please sign to checkout", Toasty.LENGTH_SHORT ).show();
                } else {

                    String data = PreferenceUtil.getData( "service_name", context );

                    if (data != null) {
                        //  PreferenceUtil.removeData( "service_name", context );
                        PreferenceUtil.removeData( "service_name", context );
                    }

                    CheckOut( Utils.getCurrentTiming( getActivity() ), locationAddres, Double.toString( currentLat ), Double.toString( currentLong ), "2", PreferenceUtil.getData( "checkin_id", context ), encodedImage );
                    Log.d( "CheckoutURL", Utils.CheckinAPI + getActivity() + locationAddres + currentLat + currentLong + 2 + PreferenceUtil.getData( "checkin_id", context ) + encodedImage );
                    alertDialog.dismiss();
                }
            }
        } );

        btnclr.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                signView.clear();
            }
        } );
        alertDialog.show();
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    private void CheckIn(String date, String time_in, String clientid, String lunch_time, String payload_id, String service_id, String client_initial, final String name, String latitude, String longitude, String type) {

        Map<String, String> headers = new HashMap<>();
        String credentials = PreferenceUtil.getData( "username", getActivity() ) + ":" + PreferenceUtil.getData( "password", getActivity() );
        String auth = "Basic " + Base64.encodeToString( credentials.getBytes(), Base64.NO_WRAP );
        headers.put( "Authorization", auth );

        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put( "location", name );
        jsonParams.put( "lat", latitude );
        jsonParams.put( "long", longitude );
        jsonParams.put( "log_type", type );
        jsonParams.put( "date", date );
        jsonParams.put( "time_in", time_in );
        jsonParams.put( "client_id", clientid );
        jsonParams.put( "payload_id", payload_id );
        jsonParams.put( "service_id", service_id );
        jsonParams.put( "client_initial", client_initial );
        jsonParams.put( "lunch_time", lunch_time );


        Log.d( "RespondedData", jsonParams.toString() + " headers: \n" + headers );

        RequestQueue requestQueue = Volley.newRequestQueue( context );

        CustomRequest customRequest = new CustomRequest( Request.Method.POST, Utils.CheckinAPI, jsonParams, headers,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d( "ResponseS", response.toString() );
                        try {
                            if (response.getString( "success" ).equalsIgnoreCase( "true" )) {

                                if (!response.getString( "data" ).equals( "null" ))//sucess
                                {
                                    String in_linkId = response.getString( "data" );

                                    //Toast.makeText( getActivity(), "inlinkid" + in_linkId, Toast.LENGTH_SHORT ).show();
                                    String msg = response.getJSONObject( "messages" ).getJSONArray( "success" ).get( 0 ).toString();
                                    Toast.makeText( getActivity(), "" + msg, Toast.LENGTH_SHORT ).show();

                                    PreferenceUtil.saveData( "checkin_id", response.getString( "data" ), context );

                                    chekin.setVisibility( View.GONE );
                                    checkout.setVisibility( View.VISIBLE );

                                    c_date.setText( "" + date );
                                    c_time.setText( "" + time_in );
                                    c_lunch.setText( "" + lunch_time );
                                    c_client.setText( "" + selected_client_name );
                                    c_paycode.setText( "" + selected_payload_name );
                                    c_service.setText( "" + selected_service_name );
                                    c_initial.setText( "" + client_initial );

                                    info_ll.setVisibility( View.VISIBLE );

                                } else {

                                    String msg = response.getJSONObject( "messages" ).getJSONArray( "error" ).get( 0 ).toString();
                                    Toast.makeText( getActivity(), "" + msg, Toast.LENGTH_SHORT ).show();
                                }
                            } else {

                                Utils.dismissCustomDailog();
                                Toast.makeText( getActivity(), "" + response.getString( "message" ), Toast.LENGTH_SHORT ).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            Utils.dismissCustomDailog();
                            Toast.makeText( getActivity(), "Json Error:\n" + e.getMessage(), Toast.LENGTH_LONG ).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d( "ResponseE", error.toString() );

                        Utils.dismissCustomDailog();
                        Toast.makeText( getActivity(), "Volley Error:\n" + error.getMessage(), Toast.LENGTH_LONG ).show();

                    }
                } );
        requestQueue.add( customRequest );

    }

    private void CheckOut(String checkout_time, String name, String latitude, String longitude, String type, String link, String signature) {

        Utils.displayCustomDailog( getActivity() );

        Map<String, String> headers = new HashMap<>();
        String credentials = PreferenceUtil.getData( "username", getActivity() ) + ":" + PreferenceUtil.getData( "password", getActivity() );
        String auth = "Basic " + Base64.encodeToString( credentials.getBytes(), Base64.NO_WRAP );
        headers.put( "Authorization", auth );

        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put( "location", name );
        jsonParams.put( "lat", latitude );
        jsonParams.put( "long", longitude );
        jsonParams.put( "log_type", type );
        jsonParams.put( "in_link", link );
        jsonParams.put( "check_out", checkout_time );
        jsonParams.put( "signature", signature );

        Log.d( "RespondedData", jsonParams.toString() );

        RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );

        CustomRequest customRequest = new CustomRequest( Request.Method.POST, Utils.CheckinAPI, jsonParams, headers,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d( "ResponseS", response.toString() );
                        try {
                            if (response.getString( "success" ).equalsIgnoreCase( "true" )) {

                                Utils.dismissCustomDailog();

                                if (!response.getString( "data" ).equals( "null" ))//sucess
                                {
                                    String msg = response.getJSONObject( "messages" ).getJSONArray( "success" ).get( 0 ).toString();
                                    Toast.makeText( getActivity(), "" + msg, Toast.LENGTH_SHORT ).show();

                                    chekin.setVisibility( View.VISIBLE );
                                    checkout.setVisibility( View.GONE );

                                    info_ll.setVisibility( View.GONE );
                                    c_date.setText( "" );
                                    c_time.setText( "" );
                                    c_client.setText( "" );
                                    c_paycode.setText( "" );
                                    c_service.setText( "" );
                                    c_initial.setText( "" );

                                } else {

                                    String msg = response.getJSONObject( "messages" ).getJSONArray( "error" ).get( 0 ).toString();
                                    Toast.makeText( getActivity(), "" + msg, Toast.LENGTH_SHORT ).show();
                                }

                            } else {

                                Utils.dismissCustomDailog();
                                Toast.makeText( getActivity(), "" + response.getString( "message" ), Toast.LENGTH_SHORT ).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            Utils.dismissCustomDailog();
                            Toast.makeText( getActivity(), "Json Error:\n" + e.getMessage(), Toast.LENGTH_LONG ).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d( "ResponseE", error.toString() );
                        Utils.dismissCustomDailog();
                        Toast.makeText( getActivity(), "Volley Error:\n" + error.getMessage(), Toast.LENGTH_LONG ).show();

                    }
                } );
        requestQueue.add( customRequest );

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void CheckGpsConnection() {

        manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );

        //  Utils.displayCustomDailog( context );

        if (ActivityCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {

            if (!hasGPSDevice( context )) {
                Toast.makeText( getActivity(), "Gps not Supported", Toast.LENGTH_SHORT ).show();
            }

            if (!manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) && hasGPSDevice( context )) {

                enableLoc();
                //EnableLocation();
            } else {
                //enabled

                getLatandLon();
            }


        } else {

            requestPermissions( new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100 );
        }


    }


    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService( Context.LOCATION_SERVICE );
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains( LocationManager.GPS_PROVIDER );
    }


    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder( context )
                    .addApi( LocationServices.API )
                    .addConnectionCallbacks( new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                            Log.d( "ResponseLoc", "connected" );
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                            Log.d( "ResponseLoc", "connectedSuspended" );
                        }
                    } )
                    .addOnConnectionFailedListener( new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                            Log.d( "ResponseLocationError", "Location error " + connectionResult.getErrorCode() );
                        }
                    } ).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
            locationRequest.setInterval( 30 * 1000 );
            locationRequest.setFastestInterval( 5 * 1000 );
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest( locationRequest );

            builder.setAlwaysShow( true );

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings( googleApiClient, builder.build() );


            result.setResultCallback( new ResultCallback<LocationSettingsResult>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onResult(@NonNull LocationSettingsResult result) {

                    final Status status = result.getStatus();

                    switch (status.getStatusCode()) {

                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location
                            // requests here.

                            Log.d( "ResponseLoc", "responsesucces" );

                            getLatandLon();


                            break;

                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.d( "ResponseLoc", "response_resolutionrequired" );
                            try {

                                // status.startResolutionForResult(getActivity(), REQUEST_LOCATION);
                                startIntentSenderForResult( status.getResolution().getIntentSender(), REQUEST_LOCATION, null, 0, 0, 0, null );

                            } catch (IntentSender.SendIntentException e) {
                                Utils.dismissCustomDailog();
                                // Ignore the error.
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.

                            Log.d( "ResponseLoc", "responsecheckunavailable" );
                            Utils.dismissCustomDailog();
                            break;
                    }
                }
            } );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK:

                        getLatandLon();

                        break;
                    case Activity.RESULT_CANCELED:

                        Utils.dismissCustomDailog();

                        Intent intent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        startActivityForResult( intent, REQUEST_ENABLE_GPS );

                        break;
                    default:
                        Utils.dismissCustomDailog();
                        Log.d( "Response", "default activity result" );
                        break;
                }
                break;

            case REQUEST_ENABLE_GPS:

                getLatandLon();

                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLatandLon() {


        if (ActivityCompat.checkSelfPermission( getActivity(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = manager.getLastKnownLocation( LocationManager.GPS_PROVIDER );


        if (location != null) {

            Log.d( "LocationResponse", "LastKnown:" + location.getLatitude() + "," + location.getLongitude() );

            getLocationDetails( location );


        } else {

            final Looper looper = null;

            final LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    Log.d( "LocationResponse", "NewResponse:" + location.getLatitude() + "," + location.getLongitude() );
                    getLocationDetails( location );
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.d( "Status Changed", String.valueOf( status ) );
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.d( "Provider Enabled", provider );
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.d( "Provider Disabled", provider );
                }
            };


            Criteria criteria = new Criteria();
            criteria.setAccuracy( Criteria.ACCURACY_COARSE );
            criteria.setPowerRequirement( Criteria.POWER_LOW );
            criteria.setAltitudeRequired( false );
            criteria.setBearingRequired( false );
            criteria.setSpeedRequired( false );
            criteria.setCostAllowed( true );
            criteria.setHorizontalAccuracy( Criteria.ACCURACY_HIGH );
            criteria.setVerticalAccuracy( Criteria.ACCURACY_HIGH );

            manager.requestSingleUpdate( criteria, locationListener, looper );

          /*  LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    Log.d("LocationResponse","Event:"+location.getLatitude()+","+location.getLongitude());


                    if (locationfound == 0) {


                        getLocationDetails(location);

                        locationfound = 1;

                    }


                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };

// Register the listener with the Location Manager to receive location updates
            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);*/

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            CheckGpsConnection();

        } else {
            Snackbar.make( getActivity().getWindow().getDecorView().getRootView(), "Permission Denied", Snackbar.LENGTH_LONG ).show();
        }
    }

    private void getLocationDetails(Location location) {

        // Utils.displayCustomDailog(getActivity());

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Geocoder gcd = new Geocoder( context, Locale.getDefault() );
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation( location.getLatitude(), location.getLongitude(), 1 );

            if (addresses.size() > 0) {

               /* String s = "Address Line: "
                        + addresses.get(0).getAddressLine(0) + "\n"
                        + addresses.get(0).getFeatureName() + "\n"
                        + "Locality: "
                        + addresses.get(0).getLocality() + "\n"
                        + addresses.get(0).getPremises() + "\n"
                        + "Admin Area: "
                        + addresses.get(0).getAdminArea() + "\n"
                        + "Country code: "
                        + addresses.get(0).getCountryCode() + "\n"
                        + "Country name: "
                        + addresses.get(0).getCountryName() + "\n"
                        + "Phone: " + addresses.get(0).getPhone()
                        + "\n" + "Postbox: "
                        + addresses.get(0).getPostalCode() + "\n"
                        + "SubLocality: "
                        + addresses.get(0).getSubLocality() + "\n"
                        + "SubAdminArea: "
                        + addresses.get(0).getSubAdminArea() + "\n"
                        + "SubThoroughfare: "
                        + addresses.get(0).getSubThoroughfare()
                        + "\n" + "Thoroughfare: "
                        + addresses.get(0).getThoroughfare() + "\n"
                        + "URL: " + addresses.get(0).getUrl();*/


                Utils.dismissCustomDailog();

                locationAddres = addresses.get( 0 ).getAddressLine( 0 );
                Log.d( "ResponseClientLoc", locationAddres );

                if (mMap != null) {
                    mMap.clear();

                    LatLng curentloc = new LatLng( location.getLatitude(), location.getLongitude() );

                    //  Toast.makeText( context, "" + latitude + longitude, Toast.LENGTH_SHORT ).show();
                    mMap.setMyLocationEnabled( true );
                    mMap.addMarker( new MarkerOptions().position( curentloc ).title( "Current location" ).snippet( locationAddres ) );
                    mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( curentloc, 15 ) );
                    // mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(curentloc, 10, 30, 0)));
                    mMap.animateCamera( CameraUpdateFactory.zoomIn() );
                    // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                    mMap.animateCamera( CameraUpdateFactory.zoomTo( 15 ), 2000, null );

                }

            }

            Utils.dismissCustomDailog();

        } catch (IOException e) {

            Utils.dismissCustomDailog();
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged( hidden );
//        frag_alive = hidden;
    }

    @Override
    public void onAttach(@NonNull Context ctx) {
        super.onAttach( context );
        this.context = ctx;
    }


    void EnableLocation() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest( new LocationRequest().setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY ) );
        builder.setAlwaysShow( true );

        LocationServices.getSettingsClient( context )
                .checkLocationSettings( builder.build() )
                .addOnSuccessListener( new OnSuccessListener<LocationSettingsResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        //Success Perform Task Here
                        Log.e( "Response", "Sucess" );
                        getLatandLon();
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        Log.e( "Response", "Failed Status code:" + statusCode );
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:


                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult( getActivity(), REQUEST_LOCATION );
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.e( "Response", "Unable to execute request." );
                                    Utils.dismissCustomDailog();
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Utils.dismissCustomDailog();
                                Log.e( "Response", "Location settings are inadequate, and cannot be fixed here. Fix in Settings." );

                        }
                    }
                } )
                .addOnCanceledListener( new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.e( "Response", "checkLocationSettings -> onCanceled" );
                        Utils.dismissCustomDailog();
                    }
                } );
    }

    private void logLargeString(String content) {
        if (content.length() > 3000) {
            Log.d( "mi", content.substring( 0, 3000 ) );
            logLargeString( content.substring( 3000 ) );

        } else {
            Log.d( "mi", content );
        }
    }

    private void showCheckOutDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( getActivity() );
        // Setting Alert Dialog Title
        alertDialogBuilder.setMessage( "Are you sure,You want to Checkout?");
        alertDialogBuilder.setCancelable( false );

        alertDialogBuilder.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                longittu = PreferenceUtil.getData( "longittu", context );
                lattitu = PreferenceUtil.getData( "lattitu", context );

                if (lattitu.equalsIgnoreCase( Double.toString( currentLat ) ) && longittu.equalsIgnoreCase( Double.toString( currentLong ) )) {
                    //CheckOut( Utils.getCurrentTiming( getActivity() ), locationAddres, Double.toString( latitude ), Double.toString( longitude ), "2", PreferenceUtil.getData( "checkin_id", context ) );
                    showSignatureDialog();
                } else {

                    float[] distance = new float[1];

                    Location.distanceBetween( Double.parseDouble( String.valueOf( currentLat ) ), Double.parseDouble( String.valueOf( currentLong ) ), Double.parseDouble( lattitu ), Double.parseDouble( longittu ), distance );

                    double radiusInMeters = 2.0 * 1000.0;

                    if (distance[0] > radiusInMeters) {
                        Toasty.error( context, "Outside Location", Toasty.LENGTH_SHORT ).show();
                    } else {
                        // CheckOut( Utils.getCurrentTiming( getActivity() ), locationAddres, Double.toString( latitude ), Double.toString( longitude ), "2", PreferenceUtil.getData( "checkin_id", context ) );
                        showSignatureDialog();
                    }
                }
            }
        } );

        alertDialogBuilder.setNegativeButton( "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        } );
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}






