package com.velozion.myoitc.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.velozion.myoitc.R;

import java.util.Calendar;

public class Discharge_TransferSummaryForm extends AppCompatActivity {

    TextView txt_dischargeDate, txt_transferDate;
    ImageView img_dischargeDate, img_transferDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_discharge__transfer_summary_form );

        txt_dischargeDate = findViewById( R.id.txt_dischargeDate );
        img_dischargeDate = findViewById( R.id.img_dischargeDate );
        txt_transferDate = findViewById( R.id.txt_transferDate );
        img_transferDate = findViewById( R.id.img_transferDate );

        final Calendar c = Calendar.getInstance();
        final int date = c.get( Calendar.DATE );
        final int month = c.get( Calendar.MONTH );
        final int year = c.get( Calendar.YEAR );

        img_dischargeDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog( Discharge_TransferSummaryForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txt_dischargeDate.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                    }
                }, year, month, date );
                datePickerDialog.show();
            }
        } );

        txt_dischargeDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( Discharge_TransferSummaryForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txt_dischargeDate.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                    }
                }, year, month, date );
                datePickerDialog.show();
            }
        } );

        img_transferDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog( Discharge_TransferSummaryForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txt_transferDate.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                    }
                }, year, month, date );
                datePickerDialog.show();
            }
        } );

        txt_transferDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( Discharge_TransferSummaryForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txt_transferDate.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                    }
                }, year, month, date );
                datePickerDialog.show();
            }
        } );
    }
}
