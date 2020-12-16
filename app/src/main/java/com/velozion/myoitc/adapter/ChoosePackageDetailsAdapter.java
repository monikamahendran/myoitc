package com.velozion.myoitc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.velozion.myoitc.R;
import com.velozion.myoitc.bean.ChoosePackageBeans;
import com.velozion.myoitc.databinding.ItemChoosePackageDetailsAdapterBinding;

import java.util.ArrayList;

public class ChoosePackageDetailsAdapter extends PagerAdapter {

    Context context;
    private ArrayList<ChoosePackageBeans> packageBeanArrayList;
    private LayoutInflater inflater;

    public ChoosePackageDetailsAdapter(Context context, ArrayList<ChoosePackageBeans> packageBeanArrayList) {
        this.context = context;
        this.packageBeanArrayList = packageBeanArrayList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        final ChoosePackageBeans choosePackageBean = packageBeanArrayList.get(position);

        if (inflater == null) {
            inflater = LayoutInflater.from(container.getContext());
        }

        ItemChoosePackageDetailsAdapterBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_choose_package_details_adapter, container, false);
        binding.setChoosePackage(choosePackageBean);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, packageBeanArrayList.get(position).getPermissionArrayList());
        binding.rcvPackageAdapter.setAdapter(adapter);
        binding.btnBuyNow.setBackgroundColor(Color.parseColor(choosePackageBean.getColorScheme()));

        container.addView(binding.getRoot(), 0);

        return binding.getRoot();
    }

    @Override
    public int getCount() {
        return packageBeanArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view.equals(object);
    }
}
