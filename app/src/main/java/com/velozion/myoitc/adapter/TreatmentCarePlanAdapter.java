package com.velozion.myoitc.adapter;

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
import com.velozion.myoitc.activities.ProviderReferralServiceActivity;
import com.velozion.myoitc.bean.TreatmentCarePlanbean;
import com.velozion.myoitc.databinding.ItemTreatmentcareplansBinding;

import java.util.ArrayList;

public class TreatmentCarePlanAdapter extends RecyclerView.Adapter<TreatmentCarePlanAdapter.MyViewHolder> implements Filterable {
    Context context;
    ArrayList<TreatmentCarePlanbean> carePlanbeans;
    LayoutInflater inflater;
    ArrayList<TreatmentCarePlanbean> temp;

    public TreatmentCarePlanAdapter(Context context, ArrayList<TreatmentCarePlanbean> carePlanbeans) {
        this.context = context;
        this.carePlanbeans = carePlanbeans;
        this.temp = new ArrayList<>( carePlanbeans );
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from( parent.getContext() );
        }
        ItemTreatmentcareplansBinding itemTreatmentcareplansBinding = DataBindingUtil.inflate( inflater, R.layout.item_treatmentcareplans, parent, false );
        return new MyViewHolder( itemTreatmentcareplansBinding );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setTreatmentCarePlanObj( carePlanbeans.get( position ) );
    }

    @Override
    public int getItemCount() {
        return carePlanbeans.size();
    }

    @Override
    public Filter getFilter() {
        return exFilter;
    }

    public Filter exFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<TreatmentCarePlanbean> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll( temp );
            } else {
                String strdata = charSequence.toString().toLowerCase().trim();
                for (TreatmentCarePlanbean treatmentCarePlanbean : temp) {
                    if (treatmentCarePlanbean.getClient_name().toLowerCase().contains( strdata )) {
                        filteredList.add( treatmentCarePlanbean );
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
            if (carePlanbeans != null) {
                carePlanbeans.clear();
                carePlanbeans.addAll( (ArrayList) filterResults.values );
                notifyDataSetChanged();
            }
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemTreatmentcareplansBinding binding;

        public MyViewHolder(@NonNull ItemTreatmentcareplansBinding itemView) {
            super( itemView.getRoot() );
            this.binding = itemView;
        }
    }
}
