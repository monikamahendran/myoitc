package com.velozion.myoitc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.bean.RequestServicesBean;
import com.velozion.myoitc.databinding.ItemRequestListAdapterBinding;

import java.util.ArrayList;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.MyViewHolder> {
    private ArrayList<RequestServicesBean> requestServicesBeans;
    private LayoutInflater layoutInflater;

    public RequestListAdapter(Context context, ArrayList<RequestServicesBean> requestServices) {
        this.requestServicesBeans = requestServices;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemRequestListAdapterBinding itemClientChartAdapterBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_request_list_adapter, parent, false);
        return new RequestListAdapter.MyViewHolder(itemClientChartAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dataBinding.setRequestObj(requestServicesBeans.get(position));
    }


    @Override
    public int getItemCount() {
        return requestServicesBeans.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ItemRequestListAdapterBinding dataBinding;

        public MyViewHolder(@NonNull ItemRequestListAdapterBinding itemView) {
            super(itemView.getRoot());
            dataBinding = itemView;
        }
    }
}
