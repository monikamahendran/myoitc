package com.velozion.myoitc.fragments.ProviderFormFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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


public class InsuranceInformationFrag extends Fragment implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {
    private Spinner Spinner_medicaid;
    private ArrayList<String> array_medicaid = new ArrayList<>();
    private Context context;
    MyListener listener;
    private TextInputLayout txt_insuranceMedicare, txt_otherInsurance, txt_insurancePolicy, txt_privatePay;


    public InsuranceInformationFrag(Context context, MyListener myListener) {
        this.context = context;
        this.listener = myListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_insurance_information, container, false );
        Spinner_medicaid = view.findViewById( R.id.Spinner_medicaid );
        txt_insuranceMedicare = view.findViewById( R.id.txt_insuranceMedicare );
        txt_otherInsurance = view.findViewById( R.id.txt_otherInsurance );
        txt_insurancePolicy = view.findViewById( R.id.txt_insurancePolicy );
        txt_privatePay = view.findViewById( R.id.txt_privatePay );

        getSpinnerMedicaid();
        return view;
    }

    private void getSpinnerMedicaid() {
        try {
            Map<String, String> mParams = new HashMap<>();
            StringMethodRequest postMethodRequest = new StringMethodRequest( getActivity(), Request.Method.GET, Utils.Spinner_Medicaid_API, mParams, true, this, this, this, 1 );
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
                array_medicaid = new ArrayList<>();
                JSONArray jsonArray = newJsObj.getJSONArray( "details" );
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        array_medicaid.add( jsonArray.getString( i ) );
                    }
                }
                setSpinner();
            }
        }
    }

    public void setSpinner() {

        Spinner_medicaid.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        } );
        ArrayAdapter<String> adapterNames = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, array_medicaid );
        adapterNames.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        Spinner_medicaid.setAdapter( adapterNames );
    }

    public interface MyListener {
        void insuranceCallBack(boolean isChecking, HashMap<String, String> hashMap_insurance);
    }

    public void getInsuranceData() {


        String str_insuranceMedicare = txt_insuranceMedicare.getEditText().getText().toString();
        String str_medicaid = Spinner_medicaid.getSelectedItem().toString();
        String str_otherInsurance = txt_otherInsurance.getEditText().getText().toString();
        String str_insurancePolicy = txt_insurancePolicy.getEditText().getText().toString();
        String str_privatePay = txt_privatePay.getEditText().getText().toString();
        if (str_insuranceMedicare.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Insurance MediCare", Toasty.LENGTH_SHORT ).show();
        } else if (Spinner_medicaid.getSelectedItem().toString().trim().equals( "Select Medicaid" )) {
            Toasty.info( getActivity(), "Select any Medicaid", Toasty.LENGTH_SHORT ).show();
        } else if (str_otherInsurance.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Other Insurance", Toasty.LENGTH_SHORT ).show();
        } else if (str_insurancePolicy.isEmpty()) {
            Toasty.info( getActivity(), "Enter the insurance Policy", Toasty.LENGTH_SHORT ).show();
        } else if (str_privatePay.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Private Pay", Toasty.LENGTH_SHORT ).show();
        } else {
            HashMap<String, String> hashMap_insuranceInfo = new HashMap<>();
            hashMap_insuranceInfo.put( "insuranceMediCare", str_insuranceMedicare );
            hashMap_insuranceInfo.put( "medicaid", str_medicaid );
            hashMap_insuranceInfo.put( "otherInsurance", str_otherInsurance );
            hashMap_insuranceInfo.put( "insurancePolicy", str_insurancePolicy );
            hashMap_insuranceInfo.put( "privatePay", str_privatePay );
            listener.insuranceCallBack( true, hashMap_insuranceInfo );
        }
    }

}


