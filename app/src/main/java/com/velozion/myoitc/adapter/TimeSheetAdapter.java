package com.velozion.myoitc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.bean.TimeSheetBean;
import com.velozion.myoitc.databinding.ItemTimeSheetAdapterBinding;

import java.util.ArrayList;

public class TimeSheetAdapter extends RecyclerView.Adapter<TimeSheetAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<TimeSheetBean> timeSheetBeans;
    private LayoutInflater layoutInflater;
    private ArrayList<TimeSheetBean> itemCopy = new ArrayList<>();


    public TimeSheetAdapter(Context applicationContext, ArrayList<TimeSheetBean> timeSheetBeans) {
        this.context = applicationContext;
        this.timeSheetBeans = timeSheetBeans;
        itemCopy.addAll( timeSheetBeans );
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from( parent.getContext() );
        }

        ItemTimeSheetAdapterBinding itemClientChartAdapterBinding = DataBindingUtil.inflate( layoutInflater, R.layout.item_time_sheet_adapter, parent, false );
        return new TimeSheetAdapter.MyViewHolder( itemClientChartAdapterBinding );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.adapterBinding.setTimeSheetObj( timeSheetBeans.get( position ) );

        if (timeSheetBeans.get(position).getVerifiedBy().equals("") || timeSheetBeans.get(position).getVerifiedBy().equalsIgnoreCase("null")
        ) {
            holder.adapterBinding.verifiedLl.setVisibility(View.GONE);
        }else {

            holder.adapterBinding.verifiedLl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return timeSheetBeans.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ItemTimeSheetAdapterBinding adapterBinding;

        MyViewHolder(@NonNull ItemTimeSheetAdapterBinding itemView) {
            super( itemView.getRoot() );
            adapterBinding = itemView;
        }
    }

    public void filter(String text) {
        timeSheetBeans.clear();
        if (text.isEmpty()) {
            timeSheetBeans.addAll( itemCopy );
        } else {
            for (TimeSheetBean item : itemCopy) {
                if (item.getClientDesignee().contains( text ) || item.getServiceType().contains( text )) {
                    timeSheetBeans.add( item );
                }
            }
        }
        notifyDataSetChanged();
    }
}
