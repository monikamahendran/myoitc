package com.velozion.myoitc.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.adapter.unCompleted_adapter;
import com.velozion.myoitc.bean.ClientChartTaskListBean;

import java.util.ArrayList;

public class Acitivity_uncompleted extends Fragment {

    private View view;
    private ArrayList<ClientChartTaskListBean> taskListBeans = new ArrayList<>();
    private ArrayList<ClientChartTaskListBean> filtered = new ArrayList<>();
    private RecyclerView rcv_uncompleted;
    LinearLayout no_data_found;
    String type;

    TextView txt_noDataFound;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            type = getArguments().getString( "type" );
            taskListBeans.addAll( getArguments().getParcelableArrayList( "data" ) );
            Log.d( "ResponseRecieved", "" + taskListBeans.size() );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate( R.layout.fragment_acitivity_uncompleted, container, false );

            rcv_uncompleted = view.findViewById( R.id.rcv_uncompleted );
            no_data_found = view.findViewById( R.id.nodata_ll );

            no_data_found.setVisibility( View.VISIBLE );
            txt_noDataFound = view.findViewById( R.id.txt_noDataFound );
            txt_noDataFound.setVisibility( View.VISIBLE );
            txt_noDataFound.setText( "No Task Found" );

            for (ClientChartTaskListBean bean : taskListBeans) {
                if (type.equalsIgnoreCase( "1" )) {
                    if (bean.getStatus().equalsIgnoreCase( "Pending" ) || bean.getStatus().equalsIgnoreCase( "Correction" )) {
                        filtered.add( bean );
                        no_data_found.setVisibility( View.GONE );
                    }
                } else if (bean.getStatus().equalsIgnoreCase( "Completed" ) || bean.getStatus().equalsIgnoreCase( "Submitted" )) {
                    filtered.add( bean );
                    no_data_found.setVisibility( View.GONE );
                }
            }
            unCompleted_adapter unCompleted_adapter = new unCompleted_adapter( getActivity(), filtered );
            rcv_uncompleted.setAdapter( unCompleted_adapter );
        }
        return view;
    }
}

