package com.velozion.myoitc.fragments.ClientRequestFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.velozion.myoitc.R;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class CustomerOtherInformationFrag extends Fragment {
    private Spinner spinner_customerInquiring, spinner_customerHearAbout;
    private String[] str_inquiring = {"For Whom You are Inquiring", "Self", "Parent", "Relative", "Client", "Other"};
    private String[] str_hearAbout = {"How did you hear about this?", "VNHSC", "Skilled Nursing Facility", "ALSA", "Hospital", "Other"};
    private Context context;
    private MyListener myListener;
    private TextInputLayout txt_customerSecurity, txt_customerComments;
    private CheckBox customer_checkBox;

    public CustomerOtherInformationFrag(Context context, MyListener myListener) {
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
        View view = inflater.inflate( R.layout.fragment_customer_other_information, container, false );
        spinner_customerInquiring = view.findViewById( R.id.spinner_customerInquiring );
        spinner_customerHearAbout = view.findViewById( R.id.spinner_customerHearAbout );
        txt_customerSecurity = view.findViewById( R.id.txt_customerSecurity );
        txt_customerComments = view.findViewById( R.id.txt_customerComments );
        customer_checkBox = view.findViewById( R.id.customer_checkBox );

        setAdapter();
        return view;
    }

    private void setAdapter() {
        ArrayAdapter<String> adapter_inquiring = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, str_inquiring );
        adapter_inquiring.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_customerInquiring.setAdapter( adapter_inquiring );

        ArrayAdapter<String> adapter_hearAbout = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, str_hearAbout );
        adapter_hearAbout.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_customerHearAbout.setAdapter( adapter_hearAbout );
    }

    public void getOtherInfoData() {

        String str_security = txt_customerSecurity.getEditText().getText().toString();
        String str_inquiring = spinner_customerInquiring.getSelectedItem().toString();
        String str_hearAbout = spinner_customerHearAbout.getSelectedItem().toString();
        String str_comments = txt_customerComments.getEditText().getText().toString();

        if (str_security.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Social Security", Toasty.LENGTH_SHORT ).show();
        } else if (spinner_customerInquiring.getSelectedItem().toString().trim().equals( "For Whom You are Inquiring" )) {
            Toasty.info( getActivity(), "Select Inquiring", Toasty.LENGTH_SHORT ).show();

        } else if (spinner_customerHearAbout.getSelectedItem().toString().trim().equals( "How did you hear about this?" )) {
            Toasty.info( getActivity(), "Select How did u hear about this?", Toasty.LENGTH_SHORT ).show();

        } else if (str_comments.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Comments", Toasty.LENGTH_SHORT ).show();
        } else if (!customer_checkBox.isChecked()) {
            Toasty.info( getActivity(), "Please Select Review and Acknowledge", Toasty.LENGTH_SHORT ).show();
        } else {
            HashMap<String, String> hashMap_otherInfo = new HashMap<>();
            hashMap_otherInfo.put( "customerSecuirty", str_security );
            hashMap_otherInfo.put( "customerInquiring", str_inquiring );
            hashMap_otherInfo.put( "customerHearAbout", str_hearAbout );
            hashMap_otherInfo.put( "customerComments", str_comments );
            myListener.otherInfoCallBack( true, hashMap_otherInfo );
        }
    }
    public interface MyListener {
        void otherInfoCallBack(boolean otherInfoChecking, HashMap<String, String> hashMap_otherInfo);
    }
}
