package com.velozion.myoitc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.bean.ClientChartBean;
import com.velozion.myoitc.bean.EmployeeFolderBean;
import com.velozion.myoitc.databinding.ItemEmployeeFolderAdapterBinding;

import java.util.ArrayList;

public class EmployeFolderAdapter extends RecyclerView.Adapter<EmployeFolderAdapter.MyViewHolder> {
    private ArrayList<EmployeeFolderBean> employeeFolderBeans;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<EmployeeFolderBean> itemCopy = new ArrayList<>();


    public EmployeFolderAdapter(Context context, ArrayList<EmployeeFolderBean> employeeFolderBeans) {
        this.context=context;
        this.employeeFolderBeans = employeeFolderBeans;
        itemCopy.addAll( employeeFolderBeans );
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from( parent.getContext() );
        }

        ItemEmployeeFolderAdapterBinding itemEmployeeFolderAdapterBinding = DataBindingUtil.inflate( layoutInflater, R.layout.item_employee_folder_adapter, parent, false );
        return new EmployeFolderAdapter.MyViewHolder( itemEmployeeFolderAdapterBinding );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.adapterBinding.setEmployeeFolderObj( employeeFolderBeans.get( position ) );
        Utils.LoadImage( employeeFolderBeans.get( position ).getEmpImg(), holder.adapterBinding.clintchtimage );
    }

    @Override
    public int getItemCount() {
        return employeeFolderBeans.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ItemEmployeeFolderAdapterBinding adapterBinding;

        public MyViewHolder(@NonNull ItemEmployeeFolderAdapterBinding itemView) {
            super( itemView.getRoot() );

            adapterBinding = itemView;
        }
    }
    public void filter(String text) {
        employeeFolderBeans.clear();
        if (text.isEmpty()) {
            employeeFolderBeans.addAll( itemCopy );
        } else {
            for (EmployeeFolderBean item : itemCopy) {
                if (item.getEmpName().contains( text ) || item.getDept().contains( text )) {
                    employeeFolderBeans.add( item );
                }
            }
        }
        notifyDataSetChanged();
    }
}
