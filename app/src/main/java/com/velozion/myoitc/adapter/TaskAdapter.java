package com.velozion.myoitc.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.AnimUtils;
import com.velozion.myoitc.R;
import com.velozion.myoitc.bean.TaskManagementBean;
import com.velozion.myoitc.databinding.ItemTaskBinding;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyTaskHolder> {

    Context context;
    private ArrayList<TaskManagementBean> taskManagementBeanArrayList;
    private LayoutInflater layoutInflater;
    private int pos = 0;

    public TaskAdapter(Context context) {
        this.context = context;
        taskManagementBeanArrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemTaskBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_task, parent, false);
        return new MyTaskHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTaskHolder holder, int position) {

        holder.itemTaskBinding.setTask(taskManagementBeanArrayList.get(position));

//        int status = Integer.parseInt(taskManagementBeanArrayList.get(position).getStatus());
//
//        if (status == 0) { //Pending
//            holder.itemTaskBinding.taskPending.setVisibility(View.VISIBLE);
//            holder.itemTaskBinding.taskDelivered.setVisibility(View.GONE);
//        } else if (status == 1) { //done
//            holder.itemTaskBinding.taskPending.setVisibility(View.GONE);
//            holder.itemTaskBinding.taskDelivered.setVisibility(View.VISIBLE);
//        }

//        holder.itemTaskBinding.calLl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:" + taskManagementBeanArrayList.get(position).getMobile()));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });

        holder.itemTaskBinding.directionsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String uri = "http://maps.google.com/maps?daddr=" + taskManagementBeanArrayList.get(position).getLat() + "," + taskManagementBeanArrayList.get(position).getLng();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (position > pos) {
            AnimUtils.animate(holder.itemView.getRootView(), true);
        } else {
            // AnimUtils.animate(holder.itemView.getRootView(), false);
        }
        pos = position;
    }

    @Override
    public int getItemCount() {
        return taskManagementBeanArrayList.size();
    }

    class MyTaskHolder extends RecyclerView.ViewHolder {

        ItemTaskBinding itemTaskBinding;

        MyTaskHolder(@NonNull ItemTaskBinding itemView) {
            super(itemView.getRoot());
            itemTaskBinding = itemView;
        }
    }

    public void addTasks(ArrayList<TaskManagementBean> dataArrayList) {
        taskManagementBeanArrayList.clear();
        taskManagementBeanArrayList.addAll(dataArrayList);
        notifyDataSetChanged();
    }
}
