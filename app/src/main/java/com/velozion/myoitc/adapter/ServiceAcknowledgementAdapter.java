package com.velozion.myoitc.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.bean.ClientAcknowledgementBean;
import com.velozion.myoitc.databinding.ItemClientacknowledgementBinding;

import java.util.ArrayList;

public class ServiceAcknowledgementAdapter extends RecyclerView.Adapter<ServiceAcknowledgementAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private ArrayList<ClientAcknowledgementBean> acknowledgementBeans;
    private LayoutInflater inflater;
    private ArrayList<ClientAcknowledgementBean> temp;

    public ServiceAcknowledgementAdapter(Context context, ArrayList<ClientAcknowledgementBean> acknowledgementBeans) {
        this.context = context;
        this.acknowledgementBeans = acknowledgementBeans;
        this.temp = new ArrayList<>( acknowledgementBeans );
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from( parent.getContext() );
        }
        ItemClientacknowledgementBinding itemClientacknowledgementBinding = DataBindingUtil.inflate( inflater, R.layout.item_clientacknowledgement, parent, false );
        return new MyViewHolder( itemClientacknowledgementBinding );
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dataBinding.setClientacknowledgementObj( acknowledgementBeans.get( position ) );
        if (acknowledgementBeans.get( position ).getPayment_status().equals( "Pending" )) {
            holder.dataBinding.txtclientknowlestatus.setTextColor( context.getResources().getColor( R.color.red ) );
        }
       /* if (acknowledgementBeans.get( position ).getService_startDate().equals( "" )) {
            holder.dataBinding.txtclientknowlestartdate.setText( "-" );
        }*/
    }

    @Override
    public int getItemCount() {
        return acknowledgementBeans.size();
    }

    @Override
    public Filter getFilter() {
        return exFilter;
    }

    public Filter exFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ClientAcknowledgementBean> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll( temp );
            } else {
                String strdata = charSequence.toString().toLowerCase().trim();
                for (ClientAcknowledgementBean clientAcknowledgementBean : temp) {
                    if (clientAcknowledgementBean.getClient_name().toLowerCase().contains( strdata )) {
                        filteredList.add( clientAcknowledgementBean );
                    } else if (filteredList == null) {
                        Toast.makeText( context, "No Data", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (acknowledgementBeans != null) {
                acknowledgementBeans.clear();
                acknowledgementBeans.addAll( (ArrayList) filterResults.values );
                notifyDataSetChanged();
            }
        }
    };


    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemClientacknowledgementBinding dataBinding;


        MyViewHolder(ItemClientacknowledgementBinding itemView) {
            super( itemView.getRoot() );
            dataBinding = itemView;

        }
    }
}
