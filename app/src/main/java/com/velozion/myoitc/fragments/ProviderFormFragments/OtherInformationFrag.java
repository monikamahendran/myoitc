package com.velozion.myoitc.fragments.ProviderFormFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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


public class OtherInformationFrag extends Fragment implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {
    private Spinner spinner_physician, spinner_primary_diagnosis, spinner_secondary_diagnosis, spinner_otherDiagnosis, spinner_maritalStatus;
    private ArrayList<String> arrayList_physician = new ArrayList<>();
    private ArrayList<String> arrayList_primary_diagnosis = new ArrayList<>();
    private ArrayList<String> arrayList_secondary_diagnosis = new ArrayList<>();
    private ArrayList<String> arrayList_Other_diagnosis = new ArrayList<>();
    private String[] str_maritalStatus = new String[]{"Select Marital Status", "Married", "Divorce", "Widowed", "Single", "Separated", "Unknown"};
    private Context context;
    private MyListener myListener;


    public OtherInformationFrag(Context context, MyListener myListener) {
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
        View view = inflater.inflate( R.layout.fragment_other_information, container, false );
        spinner_physician = view.findViewById( R.id.spinner_physician );
        spinner_primary_diagnosis = view.findViewById( R.id.spinner_primary_diagnosis );
        spinner_secondary_diagnosis = view.findViewById( R.id.spinner_secondary_diagnosis );
        spinner_otherDiagnosis = view.findViewById( R.id.spinner_otherDiagnosis );
        spinner_maritalStatus = view.findViewById( R.id.spinner_maritalStatus );

        getSpinnerPhysician();
        return view;
    }

    private void getSpinnerPhysician() {
        try {
            Map<String, String> mParams = new HashMap<>();
            StringMethodRequest postMethodRequest = new StringMethodRequest( getActivity(), Request.Method.GET, Utils.Spinner_Physician_API, mParams, true, this, this, this, 1 );
            MyApplication.getInstance().addToRequestQueue( postMethodRequest );

            StringMethodRequest postMethodPrimaryDiagnosis = new StringMethodRequest( getActivity(), Request.Method.GET, Utils.Spinner_Primary_Diagnosis_API, mParams, true, this, this, this, 2 );
            MyApplication.getInstance().addToRequestQueue( postMethodPrimaryDiagnosis );

            StringMethodRequest postMethodSecondaryDiagnosis = new StringMethodRequest( getActivity(), Request.Method.GET, Utils.Spinner_Secondary_Diagnosis_API, mParams, true, this, this, this, 3 );
            MyApplication.getInstance().addToRequestQueue( postMethodSecondaryDiagnosis );

            StringMethodRequest postMethodOtherDiagnosis = new StringMethodRequest( getActivity(), Request.Method.GET, Utils.Spinner_other_Diagnosis_API, mParams, true, this, this, this, 4 );
            MyApplication.getInstance().addToRequestQueue( postMethodOtherDiagnosis );

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
                arrayList_physician = new ArrayList<>();
                JSONArray jsonArray = newJsObj.getJSONArray( "details" );
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList_physician.add( jsonArray.getString( i ) );
                    }
                }
            }
        }
        if (msgId == 2) {
            if (newJsObj.getBoolean( "status" )) {
                arrayList_primary_diagnosis = new ArrayList<>();
                JSONArray jsonArray = newJsObj.getJSONArray( "details" );
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList_primary_diagnosis.add( jsonArray.getString( i ) );
                    }
                }

            }
        }

        if (msgId == 3) {
            if (newJsObj.getBoolean( "status" )) {
                arrayList_secondary_diagnosis = new ArrayList<>();
                JSONArray jsonArray = newJsObj.getJSONArray( "details" );
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList_secondary_diagnosis.add( jsonArray.getString( i ) );
                    }
                }

            }
        }

        if (msgId == 4) {
            if (newJsObj.getBoolean( "status" )) {
                arrayList_Other_diagnosis = new ArrayList<>();
                JSONArray jsonArray = newJsObj.getJSONArray( "details" );
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList_Other_diagnosis.add( jsonArray.getString( i ) );
                    }
                }

            }
        }
        setSpinner();
    }

    public void setSpinner() {
        ArrayAdapter<String> adapterPhysician = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, arrayList_physician );
        adapterPhysician.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );

        ArrayAdapter<String> adapterPrimaryDiagnosis = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, arrayList_primary_diagnosis );
        adapterPrimaryDiagnosis.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );


        ArrayAdapter<String> adapterSecondaryDiagnosis = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, arrayList_secondary_diagnosis );
        adapterSecondaryDiagnosis.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );

        ArrayAdapter<String> adapterOtherDiagnosis = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, arrayList_Other_diagnosis );
        adapterOtherDiagnosis.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );

        ArrayAdapter<String> adapterMaritalStatus = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, str_maritalStatus );
        adapterMaritalStatus.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );

        spinner_physician.setAdapter( adapterPhysician );
        spinner_primary_diagnosis.setAdapter( adapterPrimaryDiagnosis );
        spinner_secondary_diagnosis.setAdapter( adapterSecondaryDiagnosis );
        spinner_otherDiagnosis.setAdapter( adapterOtherDiagnosis );
        spinner_maritalStatus.setAdapter( adapterMaritalStatus );


    }

    public void getOtherInformationData() {
        String str_physicican = spinner_physician.getSelectedItem().toString();
        String str_primaryDiagnosis = spinner_primary_diagnosis.getSelectedItem().toString();
        String str_secondaryDiagnosis = spinner_secondary_diagnosis.getSelectedItem().toString();
        String str_otherDiagnosis = spinner_otherDiagnosis.getSelectedItem().toString();
        String str_maritalStatus = spinner_maritalStatus.getSelectedItem().toString();

        if (spinner_physician.getSelectedItem().toString().trim().equals( "Select Physician" )) {
            Toasty.info( getActivity(), "Select any Physician", Toasty.LENGTH_SHORT ).show();
        } else if (spinner_primary_diagnosis.getSelectedItem().toString().trim().equals( "Select Primary Diagnosis" )) {
            Toasty.info( getActivity(), "Select any Primary Diagnosis", Toasty.LENGTH_SHORT ).show();
        } else if (spinner_secondary_diagnosis.getSelectedItem().toString().trim().equals( "Select Secondary Diagnosis" )) {
            Toasty.info( getActivity(), "Select any Seconday Diagnosis", Toasty.LENGTH_SHORT ).show();
        } else if (spinner_otherDiagnosis.getSelectedItem().toString().trim().equals( "Select Other Diagnosis" )) {
            Toasty.info( getActivity(), "Select any Other Diagnosis", Toasty.LENGTH_SHORT ).show();
        } else if (spinner_maritalStatus.getSelectedItem().toString().trim().equals( "Select Marital Status" )) {
            Toasty.info( getActivity(), "Select any Marital Status", Toasty.LENGTH_SHORT ).show();
        } else {
            HashMap<String, String> hashMap_otherInformation = new HashMap<>();
            hashMap_otherInformation.put( "otherInfoPhysician", str_physicican );
            hashMap_otherInformation.put( "otherInfoPrimaryDiagnosis", str_primaryDiagnosis );
            hashMap_otherInformation.put( "otherInfoSecondaryDiagnosis", str_secondaryDiagnosis );
            hashMap_otherInformation.put( "otherDiagnosis", str_otherDiagnosis );
            hashMap_otherInformation.put( "otherInfoMaritalStatus", str_maritalStatus );
            myListener.otherInformationCallBack( true, hashMap_otherInformation );
        }
    }

    public interface MyListener {
        void otherInformationCallBack(boolean isCheckingOtherInfo, HashMap<String, String> hashMap_otherInfo);
    }
}
