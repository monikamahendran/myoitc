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
import com.velozion.myoitc.activities.TimeSheetActivity;
import com.velozion.myoitc.bean.TimeSheetTypeModel;
import com.velozion.myoitc.databinding.ItemTimesheetTypeBinding;

import java.util.ArrayList;

public class TimeSheetTypeAdapter  extends RecyclerView.Adapter<TimeSheetTypeAdapter.MyTimeSheetHolder> {

    Context context;
    ArrayList<TimeSheetTypeModel> data;

    public TimeSheetTypeAdapter(Context context, ArrayList<TimeSheetTypeModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyTimeSheetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       ItemTimesheetTypeBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_timesheet_type,parent,false);
        return new MyTimeSheetHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTimeSheetHolder holder, int position) {

        holder.itemTimesheetTypeBinding.setTimeSheetType(data.get(position));

        holder.itemTimesheetTypeBinding.typeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( context, TimeSheetActivity.class );
                intent.putExtra("type",2);
                intent.putExtra("id",data.get(position).getId());
                context.startActivity( intent );


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyTimeSheetHolder extends RecyclerView.ViewHolder{

        ItemTimesheetTypeBinding itemTimesheetTypeBinding;

        public MyTimeSheetHolder(@NonNull ItemTimesheetTypeBinding itemView) {
            super(itemView.getRoot());
            itemTimesheetTypeBinding=itemView;
        }
    }
}
