package com.velozion.myoitc.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.activities.ChoosePackagesDetailsActivity;
import com.velozion.myoitc.bean.ChoosePackageBeans;
import com.velozion.myoitc.databinding.ItemChoosepackageadapterBinding;
import com.velozion.myoitc.databinding.ItemPackageBinding;

import java.util.ArrayList;

public class ChoosePackageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChoosePackageBeans> packageBeanArrayList;
    private LayoutInflater inflater;
    private Context context;
    int type;

    public ChoosePackageAdapter(Context context, ArrayList<ChoosePackageBeans> packageBeanArrayList,int type) {
        this.packageBeanArrayList = packageBeanArrayList;
        this.context=context;
        this.type=type;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        if (viewType==1)
        {
            ItemChoosepackageadapterBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_choosepackageadapter, parent, false);
            return new MyViewHolder(binding);

        }else {

            ItemPackageBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_package, parent, false);
            return new MyPackageHolder(binding);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ChoosePackageBeans model=packageBeanArrayList.get(position);

        if (holder instanceof MyViewHolder)
        {

            ((MyViewHolder) holder).dataBinding.setChoosePackage(packageBeanArrayList.get(position));
            ((MyViewHolder) holder).dataBinding.topLin.setBackgroundColor(Color.parseColor(packageBeanArrayList.get(position).getColorScheme()));
            ((MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChoosePackagesDetailsActivity.class);
                    intent.putExtra("packageBeanList", packageBeanArrayList);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });

        }else if (holder instanceof MyPackageHolder)
        {

            ((MyPackageHolder) holder).dataBinding.setChoosePackage(packageBeanArrayList.get(position));

            if (model.getPack_type().equals("1"))
            {
                ((MyPackageHolder) holder).dataBinding.view.setBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));
            }else if (model.getPack_type().equals("2"))
            {
                ((MyPackageHolder) holder).dataBinding.view.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));


            }else if (model.getPack_type().equals("3"))
            {
                ((MyPackageHolder) holder).dataBinding.view.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_dark));

            }

            ((MyPackageHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChoosePackagesDetailsActivity.class);
                    intent.putExtra("packageBeanList", packageBeanArrayList);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return packageBeanArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemChoosepackageadapterBinding dataBinding;

        MyViewHolder(ItemChoosepackageadapterBinding itemChoosepackageadapterBinding) {
            super(itemChoosepackageadapterBinding.getRoot());
            dataBinding = itemChoosepackageadapterBinding;
        }
    }


    class MyPackageHolder extends RecyclerView.ViewHolder {
        ItemPackageBinding dataBinding;

        MyPackageHolder(ItemPackageBinding itemview) {
            super(itemview.getRoot());
            dataBinding = itemview;
        }
    }
}
