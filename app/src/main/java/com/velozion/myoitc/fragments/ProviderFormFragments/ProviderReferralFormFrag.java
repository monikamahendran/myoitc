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

public class ProviderReferralFormFrag extends Fragment implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {
    private Spinner spinner_referral_source;
    private ArrayList<String> arrayList_referral_source = new ArrayList<>();
    public TextInputLayout txt_physician, txt_givingReferral, txt_fax, txt_phoneNumber;
    private Context context;
    MyListener ml;

    public ProviderReferralFormFrag(Context context, MyListener m11) {
        this.context = context;
        this.ml = m11;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_provider_referral_form, container, false );

        spinner_referral_source = view.findViewById( R.id.spinner_referral_source );
        txt_physician = view.findViewById( R.id.txt_physicians );
        txt_givingReferral = view.findViewById( R.id.txt_givingReferral );
        txt_fax = view.findViewById( R.id.txt_fax );
        txt_phoneNumber = view.findViewById( R.id.txt_phoneNumber );

        if (spinner_referral_source.getSelectedItemPosition() == 0) {
            spinner_referral_source = null;
        }
        getReferralSource();
        return view;
    }

    public void getProfiderFormData() {
        String str_Physician = txt_physician.getEditText().getText().toString().trim();
        String str_givingReferral = txt_givingReferral.getEditText().getText().toString().trim();
        String str_referralSource = spinner_referral_source.getSelectedItem().toString().trim();
        String str_phoneNumber = txt_phoneNumber.getEditText().getText().toString().trim();
        String str_fax = txt_fax.getEditText().getText().toString().trim();

        if (str_Physician.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Physician", Toasty.LENGTH_SHORT ).show();
        } else if (str_givingReferral.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Person giving Referral", Toasty.LENGTH_SHORT ).show();
        } else if (spinner_referral_source.getSelectedItem().toString().trim().equals( "Select Referral Source" )) {
            Toasty.info( getActivity(), "Select any Referral Source", Toasty.LENGTH_SHORT ).show();
        } else if (str_fax.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Fax", Toasty.LENGTH_SHORT ).show();
        } else if (str_phoneNumber.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Phone Number", Toasty.LENGTH_SHORT ).show();
        } else if (str_phoneNumber.length() < 10 || str_phoneNumber.length() > 10) {
            Toasty.info( getActivity(), "Enter the Valid Phone Number", Toasty.LENGTH_SHORT ).show();
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put( "physician", str_Physician );
            map.put( "givingReferral", str_givingReferral );
            map.put( "ReferralSource", str_referralSource );
            map.put( "fax", str_fax );
            map.put( "phoneNumber", str_phoneNumber );
            ml.callback( true, map );
        }
    }

    private void getReferralSource() {
        try {
            Map<String, String> mParams = new HashMap<>();
            StringMethodRequest postMethodRequest = new StringMethodRequest( getActivity(), Request.Method.GET, Utils.Spinner_Referral_source, mParams, true, this, this, this, 1 );
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
                arrayList_referral_source = new ArrayList<>();
                JSONArray jsonArray = newJsObj.getJSONArray( "details" );
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList_referral_source.add( jsonArray.getString( i ) );
                    }
                }
                setSpinner();
            }
        }
    }

    private void setSpinner() {
        ArrayAdapter<String> adapter_referral_source = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, arrayList_referral_source );
        adapter_referral_source.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_referral_source.setAdapter( adapter_referral_source );
    }

    public interface MyListener {
        void callback(boolean isChecking, HashMap<String, String> stringHashMap);
    }
}
