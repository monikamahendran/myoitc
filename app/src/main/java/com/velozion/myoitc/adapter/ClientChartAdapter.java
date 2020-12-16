package com.velozion.myoitc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.activities.ClientChartDetails;
import com.velozion.myoitc.bean.ClientChartBean;
import com.velozion.myoitc.databinding.ItemClientChartAdapterBinding;

import java.util.ArrayList;

public class ClientChartAdapter extends RecyclerView.Adapter<ClientChartAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ClientChartBean> clientChartListsBeans;
    private ArrayList<ClientChartBean> itemCopy = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ClientChartAdapter(Context context, ArrayList<ClientChartBean> clientChartListsBeans) {
        this.context = context;
        this.clientChartListsBeans = clientChartListsBeans;
        itemCopy.addAll( clientChartListsBeans );
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from( parent.getContext() );
        }

        ItemClientChartAdapterBinding itemClientChartAdapterBinding = DataBindingUtil.inflate( layoutInflater, R.layout.item_client_chart_adapter, parent, false );
        return new MyViewHolder( itemClientChartAdapterBinding );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.adapterBinding.setClientChartBean( clientChartListsBeans.get( position ) );


        Utils.ImageLoaderInitialization( context );
        Utils.LoadImage( clientChartListsBeans.get( position ).getCliImage(), holder.adapterBinding.clintchtimage );


        String email = clientChartListsBeans.get( position ).getCliMail();

        if (email.isEmpty()) {
            holder.id_client_email.setText( "-" );
        } else {
            holder.id_client_email.setText( email );
        }

        holder.itemView.getRootView().setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, ClientChartDetails.class );
                intent.putExtra( "client_chart_array_list", clientChartListsBeans.get( position ) );
                ///  intent.putExtra( "client_chart_task_array_list", clientChartListsBeans.get( position ).getClientTasks() );
                context.startActivity( intent );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return clientChartListsBeans.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id_client_email;
        ItemClientChartAdapterBinding adapterBinding;

        MyViewHolder(@NonNull ItemClientChartAdapterBinding itemView) {
            super( itemView.getRoot() );
            id_client_email = itemView.idClientEmail;

            adapterBinding = itemView;

        }
    }

    public void filter(String text) {
        clientChartListsBeans.clear();
        if (text.isEmpty()) {
            clientChartListsBeans.addAll( itemCopy );
        } else {
            for (ClientChartBean item : itemCopy) {
                if (item.getClientName().contains( text )) {
                    clientChartListsBeans.add( item );
                }
            }
        }
        notifyDataSetChanged();
    }
}
