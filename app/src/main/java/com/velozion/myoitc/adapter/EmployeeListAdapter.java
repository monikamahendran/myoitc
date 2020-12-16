package com.velozion.myoitc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.activities.AbsentTrackerActivity;
import com.velozion.myoitc.activities.TimeSheetActivity;
import com.velozion.myoitc.bean.EmployeeListBean;
import com.velozion.myoitc.databinding.ItemAbsentEmployeeBinding;
import com.velozion.myoitc.databinding.ItemEmployeeListAdapterBinding;

import java.util.ArrayList;

public class EmployeeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<EmployeeListBean> employeeListBeans;
    private ArrayList<EmployeeListBean> itemCopy = new ArrayList<>();
    private int from;
    private LayoutInflater layoutInflater;


    public EmployeeListAdapter(Context context, ArrayList<EmployeeListBean> employeeListBeans, int from) {
        this.context = context;
        this.employeeListBeans = employeeListBeans;
        itemCopy.addAll( employeeListBeans );
        this.from = from;
    }

    @Override
    public int getItemViewType(int position) {
        return from;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from( parent.getContext() );
        }

        if (viewType==1)
        {

            ItemEmployeeListAdapterBinding itemEmployeeListAdapterBinding = DataBindingUtil.inflate( layoutInflater, R.layout.item_employee_list_adapter, parent, false );
            return new MyViewHolder( itemEmployeeListAdapterBinding );

        }else if (viewType==2)
        {
            ItemAbsentEmployeeBinding itemEmployeeListAdapterBinding = DataBindingUtil.inflate( layoutInflater, R.layout.item_absent_employee, parent, false );
            return new MyAbsentViewHolder(itemEmployeeListAdapterBinding);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolder)
        {
            ((MyViewHolder) holder).dataBinding.setEmployeeObj( employeeListBeans.get( position ) );

            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (from == 1) {
                        Intent intent = new Intent( context, TimeSheetActivity.class );
                        intent.putExtra("type",1);
                        intent.putExtra("id",employeeListBeans.get(position).getEmp_id());
                        context.startActivity( intent );
                    }
                }
            } );
        }else if (holder instanceof MyAbsentViewHolder)
        {

            ((MyAbsentViewHolder) holder).dataBinding.setEmployeeObj( employeeListBeans.get( position ) );


            ((MyAbsentViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (from == 2) {
                        Intent intent = new Intent( context, AbsentTrackerActivity.class );
                        intent.putExtra("data",employeeListBeans.get(position));
                        context.startActivity( intent );
                    }

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return employeeListBeans.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ItemEmployeeListAdapterBinding dataBinding;

        MyViewHolder(@NonNull ItemEmployeeListAdapterBinding itemView) {
            super( itemView.getRoot() );
            dataBinding = itemView;
        }
    }

    class MyAbsentViewHolder extends RecyclerView.ViewHolder {

        ItemAbsentEmployeeBinding dataBinding;

        MyAbsentViewHolder(@NonNull ItemAbsentEmployeeBinding itemView) {
            super( itemView.getRoot() );
            dataBinding = itemView;
        }
    }

    public void filter(String text) {
        employeeListBeans.clear();
        if (text.isEmpty()) {
            employeeListBeans.addAll( itemCopy );
        } else {
            for (EmployeeListBean item : itemCopy) {
                if (item.getEmp_name().contains( text ) || item.getEmp_designation().contains( text )) {
                    employeeListBeans.add( item );
                }
            }
        }
        notifyDataSetChanged();
    }
}
