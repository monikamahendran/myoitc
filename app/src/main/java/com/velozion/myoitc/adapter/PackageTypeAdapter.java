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
import com.velozion.myoitc.activities.ChoosePackagesActivity;
import com.velozion.myoitc.bean.PackageTypeModel;
import com.velozion.myoitc.databinding.ItemPackageTypeBinding;

import java.util.ArrayList;

public class PackageTypeAdapter extends RecyclerView.Adapter<PackageTypeAdapter.MyPackageTyeHolder> {

    Context context;
    ArrayList<PackageTypeModel> data;

    public PackageTypeAdapter(Context context, ArrayList<PackageTypeModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyPackageTyeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemPackageTypeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_package_type, parent, false);


        return new MyPackageTyeHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPackageTyeHolder holder, int position) {

        PackageTypeModel model=data.get(position);

        holder.itemPackageTypeBinding.setPackageType(model);

        if (model.getPackage_type()==1)
        {
            holder.itemPackageTypeBinding.view.setBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));
        }else if (model.getPackage_type()==2)
        {
            holder.itemPackageTypeBinding.view.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));


        }else if (model.getPackage_type()==3)
        {
            holder.itemPackageTypeBinding.view.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_dark));

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ChoosePackagesActivity.class);
                intent.putExtra("data",model);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyPackageTyeHolder extends RecyclerView.ViewHolder{

        ItemPackageTypeBinding  itemPackageTypeBinding;

        public MyPackageTyeHolder(@NonNull ItemPackageTypeBinding itemView) {
            super(itemView.getRoot());
            itemPackageTypeBinding=itemView;
        }
    }
}
