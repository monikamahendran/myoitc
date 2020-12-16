package com.velozion.myoitc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.adapter.PackageTypeAdapter;
import com.velozion.myoitc.bean.PackageTypeModel;
import com.velozion.myoitc.viewModel.MyViewModel;

import java.util.ArrayList;

public class PackageTypeFrag extends Fragment {

    int type;

    View view;
    RecyclerView recycerview;
    LinearLayout nodata_ll;
    MyViewModel viewModel;

    ArrayList<PackageTypeModel> data = new ArrayList<>();

    public PackageTypeFrag() {
        // Required empty public constructor
    }


    public static PackageTypeFrag newInstance(String type) {
        PackageTypeFrag fragment = new PackageTypeFrag();
        Bundle args = new Bundle();
        args.putString( "type", type );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            type = Integer.parseInt( getArguments().getString( "type" ) );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {

            view = inflater.inflate( R.layout.fragment_package_type, container, false );

            viewModel = new ViewModelProvider( getActivity() ).get( MyViewModel.class );

            recycerview = view.findViewById( R.id.package_type_recycerview );
            nodata_ll = view.findViewById( R.id.nodata_ll );

            LoadData();


        }

        return view;
    }

    private void LoadData() {

        viewModel.getPackageTypeList( getActivity() ).observe( getActivity(), new Observer<ArrayList<PackageTypeModel>>() {
            @Override
            public void onChanged(ArrayList<PackageTypeModel> packageTypeModels) {

                if (packageTypeModels != null) {
                    if (packageTypeModels.size() > 0) {
                        data.clear();

                        for (PackageTypeModel model : packageTypeModels) {
                            if (model.getPackage_type() == type) {
                                data.add( model );
                            }
                        }

                        if (data.size() > 0) {

                            PackageTypeAdapter packageTypeAdapter = new PackageTypeAdapter( getActivity(), data );
                            recycerview.setAdapter( packageTypeAdapter );

                            recycerview.setVisibility( View.VISIBLE );
                            nodata_ll.setVisibility( View.GONE );

                        } else {

                            recycerview.setVisibility( View.GONE );
                            nodata_ll.setVisibility( View.VISIBLE );

                        }


                    }


                }
            }
        } );

    }


}
