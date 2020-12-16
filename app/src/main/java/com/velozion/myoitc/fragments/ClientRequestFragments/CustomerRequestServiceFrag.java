package com.velozion.myoitc.fragments.ClientRequestFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputLayout;
import com.velozion.myoitc.R;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.interfaces.Listeners;
import com.velozion.myoitc.network.StringMethodRequest;
import com.velozion.myoitc.utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class CustomerRequestServiceFrag extends Fragment implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {

    public Spinner spinner_customerServiceRequested, spinner_customerLocation, spinner_customerstartTime, spinner_CustomerendTime;
    private String[] str_frequency = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private ArrayList<String> arrayList_customerRequested = new ArrayList<>();
    private ArrayList<String> arrayList_customerLocation = new ArrayList<>();
    private String[] str_startTimings = new String[]{"Start Time", "12:00am", "12:30am", "1:00am", "1:30am", "2:00am", "2:30am", "3:00am", "3:30am", "4:00am", "10:00am", "4:30am", "5:00am", "5:30am", "6:00am", "6:30am", "7:00am", "7:30am",
            "8:00am", "8:30am", "9:00am", "9:30am"};
    private String[] str_endTimings = new String[]{"End Time", "12:00am", "12:30am", "1:00am", "1:30am", "2:00am", "2:30am", "3:00am", "3:30am", "4:00am", "10:00am", "4:30am", "5:00am", "5:30am", "6:00am", "6:30am", "7:00am", "7:30am",
            "8:00am", "8:30am", "9:00am", "9:30am"};
    private TextView txt_customer_frequency;
    private ArrayList<CharSequence> selectedItem = new ArrayList<>();
    private AlertDialog.Builder builder;
    private StringBuilder stringBuilder;
    private Context context;
    private MyListener myListener;
    private TextInputLayout txt_customerDuration, customer_hrsPerVisit;

    public CustomerRequestServiceFrag(Context context, MyListener myListener) {
        this.context = context;
        this.myListener = myListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_customer_request, container, false );

        spinner_customerServiceRequested = view.findViewById( R.id.spinner_customerServiceRequested );
        spinner_customerLocation = view.findViewById( R.id.spinner_customerLocation );
        spinner_customerstartTime = view.findViewById( R.id.spinner_customerstartTime );
        spinner_CustomerendTime = view.findViewById( R.id.spinner_CustomerendTime );
        txt_customer_frequency = view.findViewById( R.id.txt_customer_frequency );
        txt_customerDuration = view.findViewById( R.id.txt_customerDuration );
        customer_hrsPerVisit = view.findViewById( R.id.customer_hrsPerVisit );

        getCustomerRequest();

        txt_customer_frequency.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.txt_customer_frequency:
                        showFrequency();
                        break;
                    default:
                        break;
                }
            }
        } );

        return view;
    }

    private void getCustomerRequest() {
        try {
            Map<String, String> mParams = new HashMap<>();
            StringMethodRequest postMethodRequest = new StringMethodRequest( getActivity(), Request.Method.GET, Utils.Spinner_CustomerServiceRequestedApi, mParams, true, this, this, this, 1 );
            MyApplication.getInstance().addToRequestQueue( postMethodRequest );

            StringMethodRequest postMethodRequestLocation = new StringMethodRequest( getActivity(), Request.Method.GET, Utils.Spinner_CustomerServiceLocationedApi, mParams, true, this, this, this, 2 );
            MyApplication.getInstance().addToRequestQueue( postMethodRequestLocation );

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
            if (newJsObj.getBoolean( "status" )) {
                arrayList_customerRequested = new ArrayList<>();
                JSONArray jsonArray = newJsObj.getJSONArray( "details" );
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList_customerRequested.add( jsonArray.getString( i ) );
                    }
                }
            }
        }
        if (msgId == 2) {
            if (newJsObj.getBoolean( "status" )) {
                arrayList_customerLocation = new ArrayList<>();
                JSONArray jsonArray = newJsObj.getJSONArray( "details" );
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList_customerLocation.add( jsonArray.getString( i ) );
                    }
                }
            }
        }
        setSpinner();
    }

    private void setSpinner() {
        ArrayAdapter<String> adapter_customerServiceRequest = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, arrayList_customerRequested );
        adapter_customerServiceRequest.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_customerServiceRequested.setAdapter( adapter_customerServiceRequest );


        ArrayAdapter<String> adapter_customerServiceLocation = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, arrayList_customerLocation );
        adapter_customerServiceLocation.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_customerLocation.setAdapter( adapter_customerServiceLocation );

        ArrayAdapter<String> adapter_customerStartTime = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, str_startTimings );
        adapter_customerStartTime.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_customerstartTime.setAdapter( adapter_customerStartTime );

        ArrayAdapter<String> adapter_customerEndTime = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, str_endTimings );
        adapter_customerEndTime.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_CustomerendTime.setAdapter( adapter_customerEndTime );
    }

    private void showFrequency() {
        boolean[] checkItem = new boolean[str_frequency.length];
        int count = str_frequency.length;

        for (int i = 0; i < count; i++) {

            checkItem[i] = selectedItem.contains( str_frequency[i] );
            DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    if (b)
                        selectedItem.add( str_frequency[i] );
                    else
                        selectedItem.remove( str_frequency[i] );
                    onChangeSelectedItem();
                }
            };
            builder = new AlertDialog.Builder( getActivity() );
            builder.setTitle( "Select Days" );
            builder.setMultiChoiceItems( str_frequency, checkItem, multiChoiceClickListener );
            builder.setPositiveButton( "Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getData();
                    dialogInterface.dismiss();
                }
            } );
        }
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource( android.R.color.holo_orange_light );
    }

    private void onChangeSelectedItem() {
        stringBuilder = new StringBuilder();
        for (CharSequence charSequence : selectedItem)
            stringBuilder.append( charSequence + "," );
    }

    private void getData() {
        if (stringBuilder != null) {
            selectedItem.clear();
        }
        txt_customer_frequency.setText( stringBuilder.toString() );
    }

    public void getCustomerRequestData() {
        String str_requested = spinner_customerServiceRequested.getSelectedItem().toString();
        String str_location = spinner_customerLocation.getSelectedItem().toString();
        String str_duration = txt_customerDuration.getEditText().getText().toString();
        String str_hrsPerVisit = customer_hrsPerVisit.getEditText().getText().toString();
        String str_startTime = spinner_customerstartTime.getSelectedItem().toString();
        String str_endTime = spinner_CustomerendTime.getSelectedItem().toString();
        String str_frequency = txt_customer_frequency.getText().toString();

        if (spinner_customerServiceRequested.getSelectedItem().toString().trim().matches( "Choose Service Type" )) {
            Toasty.info( getActivity(), "Select any Request", Toasty.LENGTH_SHORT ).show();
        } else if (spinner_customerLocation.getSelectedItem().toString().trim().equals( "Select Location" )) {
            Toasty.info( getActivity(), "Enter the Location", Toasty.LENGTH_SHORT ).show();
        } else if (str_duration.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Duration", Toasty.LENGTH_SHORT ).show();
        } else if (str_hrsPerVisit.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Hours Per Visit", Toasty.LENGTH_SHORT ).show();
        } else if (spinner_customerstartTime.getSelectedItem().toString().trim().equals( "Start Time" )) {
            Toasty.info( getActivity(), "Select the Start Time", Toasty.LENGTH_SHORT ).show();
        } else if (spinner_CustomerendTime.getSelectedItem().toString().trim().equals( "End Time" )) {
            Toasty.info( getActivity(), "Select the End Time", Toasty.LENGTH_SHORT ).show();
        } else if (str_frequency.isEmpty()) {
            Toasty.info( getActivity(), "Select the Frequency", Toasty.LENGTH_SHORT ).show();
        } else {
            HashMap<String, String> hashMap_customerRequest = new HashMap<>();
            hashMap_customerRequest.put( "customerrequest", str_requested );
            hashMap_customerRequest.put( "customerLocation", str_location );
            hashMap_customerRequest.put( "customerDuration", str_duration );
            hashMap_customerRequest.put( "customerHrs", str_hrsPerVisit );
            hashMap_customerRequest.put( "customerStartTime", str_startTime );
            hashMap_customerRequest.put( "customerEndTime", str_endTime );
            hashMap_customerRequest.put( "customerFrequency", str_frequency );
            myListener.customerRequestCallBack( true, hashMap_customerRequest );
        }
    }

    public interface MyListener {
        void customerRequestCallBack(boolean isRequestChecking, HashMap<String, String> hashMap_customerRequest);
    }
}
