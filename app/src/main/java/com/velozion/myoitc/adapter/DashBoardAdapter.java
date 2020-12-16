package com.velozion.myoitc.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.velozion.myoitc.PreferenceUtil;
import com.velozion.myoitc.R;
import com.velozion.myoitc.activities.AbsentTrackerEmployeList;
import com.velozion.myoitc.activities.CheckInActivity;
import com.velozion.myoitc.activities.ChoosePackagesActivity;
import com.velozion.myoitc.activities.ClientChartActivity;
import com.velozion.myoitc.activities.ClientRequestServicesActivity;
import com.velozion.myoitc.activities.DashBoardActivity;
import com.velozion.myoitc.activities.EmployeeFolderActivity;
import com.velozion.myoitc.activities.EmployeeListActivity;
import com.velozion.myoitc.activities.PackageTypeActivity;
import com.velozion.myoitc.activities.ProviderReferralServiceActivity;
import com.velozion.myoitc.activities.RequestListActivity;
import com.velozion.myoitc.activities.ServiceAcknowledgementActivity;
import com.velozion.myoitc.activities.ServiceFormActivity;
import com.velozion.myoitc.activities.TaskManagement;
import com.velozion.myoitc.activities.TimeSheetTypesActivity;
import com.velozion.myoitc.activities.TravelMapActivity;
import com.velozion.myoitc.activities.TreatmentCarePlanActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {
    Context context;
    private ArrayList<HashMap<String, Object>> stringArrayList;
    int userType;

    public DashBoardAdapter(Context context, ArrayList<HashMap<String, Object>> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
        userType = PreferenceUtil.getUserType( "user_type", context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = layoutInflater.inflate( R.layout.item_dashboard_adapter, parent, false );
        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        String str = (String) stringArrayList.get( position ).get( "name" );
        holder.txttaskmanget.setText( str );
        Drawable drawable = (Drawable) stringArrayList.get( position ).get( "image" );
        holder.imgtaskmangt.setImageDrawable( drawable );
        int pos = (int) stringArrayList.get( position ).get( "pos" );

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos == 0) {
                    Intent intentemplist = new Intent( context, TaskManagement.class );
                    intentemplist.putExtra( "from", 1 );
                    context.startActivity( intentemplist );
                }
                if (pos == 1) {

                    if (userType==1)
                    {

                        Intent intentemplist = new Intent( context, EmployeeListActivity.class );
                        intentemplist.putExtra( "from", 1 );
                        context.startActivity( intentemplist );

                    }else {

                        Intent intentemplist = new Intent( context, TimeSheetTypesActivity.class );
                        intentemplist.putExtra( "from", 1 );
                        context.startActivity( intentemplist );
                    }


                }
                if (pos == 2) {
                    Intent intent1 = new Intent( context, ClientChartActivity.class );
                    context.startActivity( intent1 );
                }
                if (pos == 3) {
                    Intent intentemplist = new Intent( context, CheckInActivity.class );
                    intentemplist.putExtra( "from", 1 );
                    context.startActivity( intentemplist );
                }
                if (pos == 4) {
                    Intent intent = new Intent( context, AbsentTrackerEmployeList.class );
                    intent.putExtra( "from", 2 );
                    context.startActivity( intent );
                }
                if (pos == 5) {
                    Intent intent = new Intent( context, EmployeeFolderActivity.class );
                    context.startActivity( intent );
                }
                if (pos == 6) {
                    Intent intent = new Intent( context, RequestListActivity.class );
                    context.startActivity( intent );
                }
                if (pos == 7) {
                    Intent intent = new Intent( context, PackageTypeActivity.class );
                    context.startActivity( intent );
                }

                if (pos == 8) {
                    Intent intent = new Intent( context, TravelMapActivity.class );
                    context.startActivity( intent );
                }

                if (pos == 9) {
                    Intent intent = new Intent( context, ProviderReferralServiceActivity.class );
                    context.startActivity( intent );
                }
                if (pos == 10) {
                    Intent intent = new Intent( context, ServiceFormActivity.class );
                    context.startActivity( intent );
                }

                if (pos == 11) {
                    Intent intent = new Intent( context, ServiceAcknowledgementActivity.class );
                    context.startActivity( intent );
                }
                if (pos == 12) {
                    Intent intent = new Intent( context, ClientRequestServicesActivity.class );
                    context.startActivity( intent );
                }
                if (pos == 13) {
                    Intent intent = new Intent( context, TreatmentCarePlanActivity.class );
                    context.startActivity( intent );
                }
            }
        } );
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txttaskmanget;
        ImageView imgtaskmangt;

        MyViewHolder(@NonNull View itemView) {
            super( itemView );
            txttaskmanget = itemView.findViewById( R.id.txttaskmanagemnt );
            imgtaskmangt = itemView.findViewById( R.id.imagetaskmanagemnt );
        }
    }
}
