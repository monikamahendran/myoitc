package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class  ClientChartTaskListBean implements Parcelable {
    private String taskName;
    private String scheduleDate;
    private String assignedTo;
    private String status;


    protected ClientChartTaskListBean(Parcel in) {
        taskName = in.readString();
        scheduleDate = in.readString();
        assignedTo = in.readString();
        status = in.readString();
    }

    public static final Creator<ClientChartTaskListBean> CREATOR = new Creator<ClientChartTaskListBean>() {
        @Override
        public ClientChartTaskListBean createFromParcel(Parcel in) {
            return new ClientChartTaskListBean(in);
        }

        @Override
        public ClientChartTaskListBean[] newArray(int size) {
            return new ClientChartTaskListBean[size];
        }
    };

    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName){
        this.taskName=taskName;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public ClientChartTaskListBean(String taskName, String scheduleDate, String assignedTo, String status) {
        this.taskName = taskName;
        this.scheduleDate = scheduleDate;
        this.assignedTo = assignedTo;
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(taskName);
        parcel.writeString(scheduleDate);
        parcel.writeString(assignedTo);
        parcel.writeString(status);
    }
}
