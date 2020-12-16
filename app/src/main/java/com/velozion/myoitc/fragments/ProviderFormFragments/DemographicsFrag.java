package com.velozion.myoitc.fragments.ProviderFormFragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.velozion.myoitc.R;

import java.util.Calendar;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class DemographicsFrag extends Fragment {
    private Spinner spinner_requestTo, spinner_chooseCountry;
    private TextView txt_startCareDate;
    private String[] str_requestTo = new String[]{"Select Request", "All Employer", "Employer", "Employer Zip Code", "Employer State"};
    private String[] str_chooseCountry = new String[]{"Select Country", "Afghanistan", "Baden", "Cambodia", "Denmark", "Ecuador", "Finland", "Georgia", "Hawaii", "India", "Jordan", "Kazakhstan", "Liberia", "Madagascar", "Netherlands", "Oman", "Poland", "Romania", "Samoa", "Tajikistan", "Ukraine", "Venezuela", "Württemberg", "Yemen", "Zimbabwe"};
    private MyListener myListerner;
    private Context context;
    private TextInputLayout txt_demographicFirstName, txt_demographicLastName, txt_demographic_email, txt_demographicPhone, txt_demographicAddress, txt_demographicCity, txt_demographicState, txt_demographicZipCode;

    public DemographicsFrag(Context context, MyListener myListerner) {
        this.context = context;
        this.myListerner = myListerner;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_demographics, container, false );
        spinner_requestTo = view.findViewById( R.id.spinner_requestTo );
        spinner_chooseCountry = view.findViewById( R.id.spinner_chooseCountry );
        txt_demographicFirstName = view.findViewById( R.id.txt_demographicFirstName );
        txt_demographicLastName = view.findViewById( R.id.txt_demographicLastName );
        txt_demographic_email = view.findViewById( R.id.txt_demographic_email );
        txt_demographicPhone = view.findViewById( R.id.txt_demographicPhone );
        txt_demographicAddress = view.findViewById( R.id.txt_demographicAddress );
        txt_demographicCity = view.findViewById( R.id.txt_demographicCity );
        txt_demographicState = view.findViewById( R.id.txt_demographicState );
        txt_startCareDate = view.findViewById( R.id.txt_demographicStartDate );
        txt_demographicZipCode = view.findViewById( R.id.txt_demographicZipCode );

        ImageView img_dateOfBirth = view.findViewById( R.id.img_demographicStartDate );

        final Calendar c = Calendar.getInstance();
        final int date = c.get( Calendar.DATE );
        final int month = c.get( Calendar.MONTH );
        final int year = c.get( Calendar.YEAR );

        img_dateOfBirth.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txt_startCareDate.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                    }
                }, year, month, date );
                datePickerDialog.show();
            }
        } );

        txt_startCareDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txt_startCareDate.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                    }
                }, year, month, date );
                datePickerDialog.show();
            }
        } );


        setSpinner();
        return view;
    }

    private void setSpinner() {
        ArrayAdapter<String> adapterRequestTo = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, str_requestTo );
        adapterRequestTo.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_requestTo.setAdapter( adapterRequestTo );

        ArrayAdapter<String> adapterCountry = new ArrayAdapter<>( getActivity(), R.layout.spinner_item_layout, str_chooseCountry );
        adapterCountry.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_chooseCountry.setAdapter( adapterCountry );
    }

    public void getdemographicData() {
        String str_requestTo = spinner_requestTo.getSelectedItem().toString();
        String str_firstName = txt_demographicFirstName.getEditText().getText().toString();
        String str_lastName = txt_demographicLastName.getEditText().getText().toString();
        String str_email = txt_demographic_email.getEditText().getText().toString();
        String str_phone = txt_demographicPhone.getEditText().getText().toString();
        String str_address = txt_demographicAddress.getEditText().getText().toString();
        String str_starcDate = txt_startCareDate.getText().toString();
        String str_city = txt_demographicCity.getEditText().getText().toString();
        String str_state = txt_demographicState.getEditText().getText().toString();
        String str_zipcode = txt_demographicZipCode.getEditText().getText().toString();
        String str_country = spinner_chooseCountry.getSelectedItem().toString();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (spinner_requestTo.getSelectedItem().toString().trim().equals( "Select Request" )) {
            Toasty.info( getActivity(), "Select any Request ", Toasty.LENGTH_SHORT ).show();
        } else if (str_firstName.isEmpty()) {
            Toasty.info( getActivity(), "Enter the First Name", Toasty.LENGTH_SHORT ).show();
        } else if (str_lastName.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Last Name", Toasty.LENGTH_SHORT ).show();
        } else if (str_email.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Email", Toasty.LENGTH_SHORT ).show();
        } else if (!str_email.matches( emailPattern )) {
            Toasty.info( getActivity(), "Enter the Valid Email", Toasty.LENGTH_SHORT ).show();
        } else if (str_phone.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Phone Number", Toasty.LENGTH_SHORT ).show();
        } else if (str_phone.length() < 10 || str_phone.length() > 10) {
            Toasty.info( getActivity(), "Enter the Valid Phone Number", Toasty.LENGTH_SHORT ).show();
        } else if (str_address.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Address", Toasty.LENGTH_SHORT ).show();
        } else if (str_starcDate.isEmpty()) {
            Toasty.info( getActivity(), "Select Start Date", Toasty.LENGTH_SHORT ).show();
        } else if (str_city.isEmpty()) {
            Toasty.info( getActivity(), "Enter the City", Toasty.LENGTH_SHORT ).show();
        } else if (str_state.isEmpty()) {
            Toasty.info( getActivity(), "Enter the State", Toasty.LENGTH_SHORT ).show();
        } else if (str_zipcode.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Zip code", Toasty.LENGTH_SHORT ).show();
        } else if (str_zipcode.length() < 6 || str_zipcode.length() > 6) {
            Toasty.info( getActivity(), "Enter the Valid Zip code", Toasty.LENGTH_SHORT ).show();

        } else if (str_country.isEmpty()) {
            Toasty.info( getActivity(), "Enter the Country", Toasty.LENGTH_SHORT ).show();
        } else {
            HashMap<String, String> hashmap_demographic = new HashMap<>();
            hashmap_demographic.put( "demographicRequestTo", str_requestTo );
            hashmap_demographic.put( "demographicFirstName", str_firstName );
            hashmap_demographic.put( "demographicLastName", str_lastName );
            hashmap_demographic.put( "demographicEmail", str_email );
            hashmap_demographic.put( "demographicPhone", str_phone );
            hashmap_demographic.put( "demographicAddress", str_address );
            hashmap_demographic.put( "demographicStartDate", str_starcDate );
            hashmap_demographic.put( "demographicCity", str_city );
            hashmap_demographic.put( "demographicState", str_state );
            hashmap_demographic.put( "demographicZipCode", str_zipcode );
            hashmap_demographic.put( "demographicCountry", str_country );

            myListerner.demographicCallBack( true, hashmap_demographic );
            Toast.makeText( context, "" + hashmap_demographic, Toast.LENGTH_SHORT ).show();
        }


    }

    public interface MyListener {
        void demographicCallBack(boolean isChecking, HashMap<String, String> hashMap_demo);
    }
}
