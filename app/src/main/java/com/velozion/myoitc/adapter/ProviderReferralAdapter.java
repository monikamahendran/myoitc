package com.velozion.myoitc.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.activities.ServiceAcknowledgementActivity;
import com.velozion.myoitc.bean.ProviderReferralBean;
import com.velozion.myoitc.databinding.ItemProviderReferralBinding;

import java.util.ArrayList;

public class ProviderReferralAdapter extends RecyclerView.Adapter<ProviderReferralAdapter.MyViewHolder> implements Filterable {
    Context context;
    private ArrayList<ProviderReferralBean> referralBeans;
    private LayoutInflater inflater;
    ArrayList<ProviderReferralBean> temp;

    public ProviderReferralAdapter(Context context, ArrayList<ProviderReferralBean> referralBeans) {
        this.context = context;
        this.referralBeans = referralBeans;
        this.temp=new ArrayList<>( referralBeans );
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from( parent.getContext() );
        }
        ItemProviderReferralBinding itemProviderReferralBinding = DataBindingUtil.inflate( inflater, R.layout.item_provider_referral, parent, false );
        return new MyViewHolder( itemProviderReferralBinding );
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setProviderReferralobj( referralBeans.get( position ) );
        if (referralBeans.get( position ).getStatus().equals( "Pending" )) {
            holder.binding.txtProviderStatus.setTextColor( context.getResources().getColor( R.color.red ) );
            holder.binding.imgProviderStatus.setColorFilter( context.getResources().getColor( R.color.red ) );
        }
    }

    @Override
    public int getItemCount() {
        return referralBeans.size();
    }

    @Override
    public Filter getFilter() {
        return exFilter;
    }

    public Filter exFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ProviderReferralBean> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll( temp );
            } else {
                String strdata = charSequence.toString().toLowerCase().trim();
                for (ProviderReferralBean providerReferralBean : temp) {
                    if (providerReferralBean.getClient_name().toLowerCase().contains( strdata )) {
                        filteredList.add( providerReferralBean );
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
            if (referralBeans != null) {
                referralBeans.clear();
                referralBeans.addAll( (ArrayList) filterResults.values );
                notifyDataSetChanged();
            }
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemProviderReferralBinding binding;

        public MyViewHolder(@NonNull ItemProviderReferralBinding itemView) {
            super( itemView.getRoot() );
            this.binding = itemView;
        }
    }
}
