package com.velozion.myoitc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.bean.ClientChartTaskListBean;
import com.velozion.myoitc.databinding.ItemClientChartDetailsBinding;

import java.util.ArrayList;

public class ClientChartDetailsAdapter extends RecyclerView.Adapter<ClientChartDetailsAdapter.MyViewHolder> {
    private ArrayList<ClientChartTaskListBean> clientChartProfileBeans;
    private LayoutInflater layoutInflater;

    public ClientChartDetailsAdapter(Context context, ArrayList<ClientChartTaskListBean> clientChartProfileBeans) {
        this.clientChartProfileBeans = clientChartProfileBeans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from( parent.getContext() );
        }

        ItemClientChartDetailsBinding itemClientChartAdapterBinding = DataBindingUtil.inflate( layoutInflater, R.layout.item_client_chart_details, parent, false );
        return new ClientChartDetailsAdapter.MyViewHolder( itemClientChartAdapterBinding );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.databBinding.setClientChartTaskBean( clientChartProfileBeans.get( position ) );
    }

    @Override
    public int getItemCount() {
        return clientChartProfileBeans.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemClientChartDetailsBinding databBinding;

        public MyViewHolder(@NonNull ItemClientChartDetailsBinding itemView) {
            super( itemView.getRoot() );
            databBinding = itemView;
        }
    }
}
