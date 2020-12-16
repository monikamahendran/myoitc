package com.velozion.myoitc.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.R;
import com.velozion.myoitc.activities.Discharge_TransferSummaryForm;
import com.velozion.myoitc.bean.ClientChartTaskListBean;

import java.util.ArrayList;

public class unCompleted_adapter extends RecyclerView.Adapter<unCompleted_adapter.MyViewHolder> {

    Context context;
    ArrayList<ClientChartTaskListBean> clientChartTaskListBeans;

    public unCompleted_adapter(Context context, ArrayList<ClientChartTaskListBean> taskListBeans) {
        this.context = context;
        this.clientChartTaskListBeans = taskListBeans;
        Log.d( "ResponseCon", "" + clientChartTaskListBeans.size() );
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_client_chart_details, parent, false ) );

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d( "ResponseBind", "pos:" + position );
        holder.clntTaskName.setText( clientChartTaskListBeans.get( position ).getTaskName() );
        holder.clntStatus.setText( clientChartTaskListBeans.get( position ).getStatus() );
        holder.clntasssignedTo.setText( clientChartTaskListBeans.get( position ).getAssignedTo() );
        holder.cntTaskDate.setText( clientChartTaskListBeans.get( position ).getScheduleDate() );

        holder.clntTaskName.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, Discharge_TransferSummaryForm.class );
                context.startActivity( intent );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return clientChartTaskListBeans.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView clntTaskName, cntTaskDate, clntasssignedTo, clntStatus;


        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            clntTaskName = itemView.findViewById( R.id.clntTaskName );
            cntTaskDate = itemView.findViewById( R.id.cntTaskDate );
            clntasssignedTo = itemView.findViewById( R.id.clntasssignedTo );
            clntStatus = itemView.findViewById( R.id.clntStatus );
        }
    }
}
