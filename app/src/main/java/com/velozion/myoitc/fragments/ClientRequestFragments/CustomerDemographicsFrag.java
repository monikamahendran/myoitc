package com.velozion.myoitc.fragments.ClientRequestFragments;

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

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.velozion.myoitc.Constants;
import com.velozion.myoitc.R;

import java.util.Calendar;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;


public class CustomerDemographicsFrag extends Fragment {
    private Context context;
    private Spinner spinner_customerRequestTo, spinner_customerCountry;

    private ImageView img_customerDob;
    private TextView txt_customerDob;
    private MyListener myListener;
    private TextInputLayout txt_CustomerFirstName, txt_CustomerLastName, txt_CustomerEmail, txt_CustomerPhone, txt_CustomerAddress, txt_CustomerCity, txt_CustomerState, txt_CustomerZipCode;


    public CustomerDemographicsFrag(Context context, MyListener myListener) {
        this.myListener = myListener;
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_customer_demographics, container, false );

        spinner_customerRequestTo = view.findViewById( R.id.spinner_customerRequestTo );
        spinner_customerCountry = view.findViewById( R.id.spinner_customerCountry );
        img_customerDob = view.findViewById( R.id.img_customerDob );
        txt_customerDob = view.findViewById( R.id.txt_customerDob );
        txt_CustomerFirstName = view.findViewById( R.id.txt_CustomerFirstName );
        txt_CustomerLastName = view.findViewById( R.id.txt_CustomerLastName );
        txt_CustomerEmail = view.findViewById( R.id.txt_CustomerEmail );
        txt_CustomerPhone = view.findViewById( R.id.txt_CustomerPhone );
        txt_CustomerAddress = view.findViewById( R.id.txt_CustomerAddress );
        txt_CustomerCity = view.findViewById( R.id.txt_CustomerCity );
        txt_CustomerState = view.findViewById( R.id.txt_CustomerState );
        txt_CustomerZipCode = view.findViewById( R.id.txt_CustomerZipCode );

        final Calendar c = Calendar.getInstance();
        final int date = c.get( Calendar.DATE );
        final int month = c.get( Calendar.MONTH );
        final int year = c.get( Calendar.YEAR );

        img_customerDob.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txt_customerDob.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                    }
                }, year, month, date );
                datePickerDialog.show();
            }
        } );

        txt_customerDob.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        txt_customerDob.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                    }
                }, year, month, date );
                datePickerDialog.show();
            }
        } );

        setAdapter();
        return view;
    }

    public void setAdapter() {
        ArrayAdapter<String> adapter_customerRequestTo = new ArrayAdapter<>( context, R.layout.spinner_item_layout, Constants.REQUEST_TO);
        adapter_customerRequestTo.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_customerRequestTo.setAdapter( adapter_customerRequestTo );

        ArrayAdapter<String> adapter_customerCountry = new ArrayAdapter<>( context, R.layout.spinner_item_layout, Constants.COUNTRY_NAMRES );
        adapter_customerCountry.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        spinner_customerCountry.setAdapter( adapter_customerCountry );
    }

    public void getDemographicData() {

        String str_requestTo = spinner_customerRequestTo.getSelectedItem().toString();
        String str_firstName = txt_CustomerFirstName.getEditText().getText().toString();
        String str_lastName = txt_CustomerLastName.getEditText().getText().toString();
        String str_email = txt_CustomerEmail.getEditText().getText().toString();
        String str_phone = txt_CustomerPhone.getEditText().getText().toString();
        String str_address = txt_CustomerAddress.getEditText().getText().toString();
        String str_dob = txt_customerDob.getText().toString();
        String str_city = txt_CustomerCity.getEditText().getText().toString();
        String str_state = txt_CustomerState.getEditText().getText().toString();
        String str_zipCode = txt_CustomerZipCode.getEditText().getText().toString();
        String str_country = spinner_customerCountry.getSelectedItem().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (spinner_customerRequestTo.getSelectedItem().toString().trim().matches( "Select any Request" )) {
            Toasty.info( context, "Select any Request", Toasty.LENGTH_SHORT ).show();
        } else if (str_firstName.isEmpty()) {
            Toasty.info( context, "Enter the First Name", Toasty.LENGTH_SHORT ).show();
        } else if (str_lastName.isEmpty()) {
            Toasty.info( context, "Enter the Last Name", Toasty.LENGTH_SHORT ).show();
        } else if (str_email.isEmpty()) {
            Toasty.info( context, "Enter the email", Toasty.LENGTH_SHORT ).show();
        } else if (!str_email.matches( emailPattern )) {
            Toasty.info( context, "Enter the Valid Email", Toasty.LENGTH_SHORT ).show();
        } else if (str_phone.isEmpty()) {
            Toasty.info( context, "Enter the Phone Number", Toasty.LENGTH_SHORT ).show();
        } else if (str_phone.length() < 10 || str_phone.length() > 10) {
            Toasty.info( context, "Enter the Valid Phone Number", Toasty.LENGTH_SHORT ).show();
        } else if (str_address.isEmpty()) {
            Toasty.info( context, "Enter the Address", Toasty.LENGTH_SHORT ).show();
        } else if (str_dob.isEmpty()) {
            Toasty.info( context, "Enter the Date of Birth", Toasty.LENGTH_SHORT ).show();
        } else if (str_city.isEmpty()) {
            Toasty.info( context, "Enter the City", Toasty.LENGTH_SHORT ).show();
        } else if (str_state.isEmpty()) {
            Toasty.info( context, "Enter the State", Toasty.LENGTH_SHORT ).show();
        } else if (str_zipCode.isEmpty()) {
            Toasty.info( context, "Enter the Zip code", Toasty.LENGTH_SHORT ).show();
        } else if (str_zipCode.length() < 6 || str_zipCode.length() > 6) {
            Toasty.info( context, "Enter the Valid Zip code", Toasty.LENGTH_SHORT ).show();
        } else if (spinner_customerCountry.getSelectedItem().toString().trim().equals( "Select Country" )) {
            Toasty.info( context, "Select any Country", Toasty.LENGTH_SHORT ).show();
        } else {
            HashMap<String, String> hashMap_requestTo = new HashMap<>();
            hashMap_requestTo.put( "clientRequestTo", str_requestTo );
            hashMap_requestTo.put( "clientFirstName", str_firstName );
            hashMap_requestTo.put( "clientLastName", str_lastName );
            hashMap_requestTo.put( "clientEmail", str_email );
            hashMap_requestTo.put( "clientPhone", str_phone );
            hashMap_requestTo.put( "clientAddress", str_address );
            hashMap_requestTo.put( "clientDob", str_dob );
            hashMap_requestTo.put( "clientCity", str_city );
            hashMap_requestTo.put( "clientSate", str_state );
            hashMap_requestTo.put( "clientZipCode", str_zipCode );
            hashMap_requestTo.put( "clientCountry", str_country );
            myListener.demographicCallBack( true, hashMap_requestTo );
        }


    }

    public interface MyListener {
        void demographicCallBack(boolean isDemoChecking, HashMap<String, String> hashMap_demographic);
    }
}
