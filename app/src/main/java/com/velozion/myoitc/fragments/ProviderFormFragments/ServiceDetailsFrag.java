package com.velozion.myoitc.fragments.ProviderFormFragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ServiceDetailsFrag extends Fragment implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {
    private Spinner spinner_service_requested, spinner_location, spinner_startTime, spinner_endTime;
    private TextView txt_service_date;
    private ArrayList<String> arrayList_service_request = new ArrayList<>();
    private String[] str_serviceLotion = new String[]{"Select any Location", "Home", "Assisted Living Facility", "SN Facility", "Unspecified/Other Location"};
    private String[] str_startTimings = new String[]{"Start Time", "12:00am", "12:30am", "1:00am", "1:30am", "2:00am", "2:30am", "3:00am", "3:30am", "4:00am", "10:00am", "4:30am", "5:00am", "5:30am", "6:00am", "6:30am", "7:00am", "7:30am",
            "8:00am", "8:30am", "9:00am", "9:30am"};
    private String[] str_endTimings = new String[]{"End Time", "12:00am", "12:30am", "1:00am", "1:30am", "2:00am", "2:30am", "3:00am", "3:30am", "4:00am", "10:00am", "4:30am", "5:00am", "5:30am", "6:00am", "6:30am", "7:00am", "7:30am",
            "8:00am", "8:30am", "9:00am", "9:30am"};
    private String[] str_frequency = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private ArrayList<CharSequence> selectedItem = new ArrayList<>();
    private AlertDialog.Builder builder;
    private StringBuilder stringBuilder;
    private TextView txt_service_frequency;
    private Context context;
    private MyListener myListener;
    private TextInputLayout txt_serviceDetailDuration, txt_serviceDetailHrs;

    public ServiceDetailsFrag(Context context, MyListener myListener) {
        this.context = context;
        this.myListener = myListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_service_details, container, false );
        spinner_service_requested = view.findViewById( R.id.spinner_service_requested );
        spinner_location = view.findViewById( R.id.spinner_location );
        ImageView img_service_date = view.findViewById( R.id.img_service_date );
        txt_service_date = view.findViewById( R.id.txt_service_date );

        spinner_startTime = view.findViewById( R.id.spinner_startTime );
        spinner_endTime = view.findViewById( R.id.spinner_endTime );
        txt_service_frequency = view.findViewById( R.id.txt_service_frequency );
        txt_serviceDetailDuration = view.findViewById( R.id.txt_serviceDetailDuration );
        txt_serviceDetailHrs = view.findViewById( R.id.txt_serviceDetailHrs );
        txt_service_frequency.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.txt_service_frequency:
                        showFrequency();
                        break;
                    default:
                        break;
                }
            }
        } );

        final Calendar c = Calendar.getInstance();
        final int date = c.get( Calendar.DATE );
        final int month = c.get( Calendar.MONTH );
        final int year = c.get( Calendar.YEAR );

        img_service_date.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        txt_service_date.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                    }
                }, year, month, date );
                datePickerDialog.show();
            }
        } );

        txt_service_date.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        txt_service_date.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                    }
                }, year, month, date );
                datePickerDialog.show();
            }
        } );

        getServiceRequested();
        return view;
    }


    private void getServiceRequested() {
        try {
            Map<String, String> mParams = new HashMap<>();
            StringMethodRequest postMethodRequest = new StringMethodRequest( getActivity(), Request.Method.GET, Utils.Spinner_Service_requestedAPI, mParams, true, this, this, this, 1 );
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
            if (newJsObj.getBoolean( "status" )) {
                arrayList_service_request = new ArrayList<>();
                JSONArray jsonArray = newJsObj.getJSONArray( "details" );
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList_service_request.add( jsonArray.getString( i ) );
                    }
                }
                setSpinner();
            }
        }
    }


    private void setSpinner() {
        ArrayAdapter<String> adapter_service_requested = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, arrayList_service_request );
        adapter_service_requested.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_service_requested.setAdapter( adapter_service_requested );

        ArrayAdapter<String> adapter_service_location = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, str_serviceLotion );
        adapter_service_location.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_location.setAdapter( adapter_service_location );

        ArrayAdapter<String> adapter_service_starttimings = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, str_startTimings );
        adapter_service_starttimings.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_startTime.setAdapter( adapter_service_starttimings );

        ArrayAdapter<String> adapter_service_endtimings = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, str_endTimings );
        adapter_service_endtimings.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_endTime.setAdapter( adapter_service_endtimings );

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
        txt_service_frequency.setText( stringBuilder.toString() );
        // Snackbar.make( getActivity().findViewById( android.R.id.content ), "" + stringBuilder.toString(), Snackbar.LENGTH_SHORT ).show();
    }

    public void getServiceDetailsData() {
        String str_serviceRequest = spinner_service_requested.getSelectedItem().toString();
        String str_serviceLocation = spinner_location.getSelectedItem().toString();


        String str_startDate = txt_service_date.getText().toString();
        String str_duration = txt_serviceDetailDuration.getEditText().getText().toString();
        String str_hrs = txt_serviceDetailHrs.getEditText().getText().toString();

        String str_serviceStartTime = spinner_startTime.getSelectedItem().toString();
        String str_serviceEndTime = spinner_endTime.getSelectedItem().toString();
        String str_frequency = txt_service_frequency.getText().toString();

        if (spinner_service_requested.getSelectedItem().toString().trim().equals( "Choose Service Type" )) {
            Toasty.info( getActivity(), "Select any Service Request", Toasty.LENGTH_SHORT ).show();
        } else if (spinner_location.getSelectedItem().toString().trim().equals( "Select any Location" )) {
            Toasty.info( getActivity(), "Select any Service Location", Toasty.LENGTH_SHORT ).show();
        } else if (str_startDate.isEmpty()) {
            Toasty.info( getActivity(), "Select Start Date", Toasty.LENGTH_SHORT ).show();
        } else if (str_duration.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Duration", Toasty.LENGTH_SHORT ).show();
        } else if (str_hrs.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Hours Per Visit", Toasty.LENGTH_SHORT ).show();
        } else if (str_serviceStartTime.isEmpty()) {
            Toasty.info( getActivity(), "Select Service Start Time", Toasty.LENGTH_SHORT ).show();
        } else if (str_serviceEndTime.isEmpty()) {
            Toasty.info( getActivity(), "Select Service End Time", Toasty.LENGTH_SHORT ).show();
        } else if (str_frequency.isEmpty()) {
            Toasty.info( getActivity(), "Select any Frequency Days", Toasty.LENGTH_SHORT ).show();
        } else {
            HashMap<String, String> hashMap_serviceDetail = new HashMap<>();
            hashMap_serviceDetail.put( "serviceRequest", str_serviceRequest );
            hashMap_serviceDetail.put( "serviceLocation", str_serviceLocation );
            hashMap_serviceDetail.put( "serviceStartTime", str_serviceStartTime );
            hashMap_serviceDetail.put( "serviceEndTime", str_serviceEndTime );
            myListener.callBackServiceDetail( true, hashMap_serviceDetail );
        }
    }

    public interface MyListener {
        void callBackServiceDetail(boolean isCheckingServiceDetail, HashMap<String, String> hashMap_serviceDetail);
    }

}

