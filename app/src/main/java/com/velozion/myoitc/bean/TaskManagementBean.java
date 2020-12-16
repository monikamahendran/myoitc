package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskManagementBean implements Parcelable {


    public TaskManagementBean(String clientName, String serviceType, String assignedBy, String address, String startTime, String endTime, String company, String date, String lat, String lng) {
        this.clientName = clientName;
        this.serviceType = serviceType;
        this.assignedBy = assignedBy;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.company = company;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    protected TaskManagementBean(Parcel in) {
        clientName = in.readString();
        serviceType = in.readString();
        assignedBy = in.readString();
        address = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        company = in.readString();
        date = in.readString();
        lat = in.readString();
        lng = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clientName);
        dest.writeString(serviceType);
        dest.writeString(assignedBy);
        dest.writeString(address);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(company);
        dest.writeString(date);
        dest.writeString(lat);
        dest.writeString(lng);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaskManagementBean> CREATOR = new Creator<TaskManagementBean>() {
        @Override
        public TaskManagementBean createFromParcel(Parcel in) {
            return new TaskManagementBean(in);
        }

        @Override
        public TaskManagementBean[] newArray(int size) {
            return new TaskManagementBean[size];
        }
    };

    public String getClientName() {
        return clientName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public String getAddress() {
        return address;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCompany() {
        return company;
    }

    public String getDate() {
        return date;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    private String clientName;
    private String serviceType;
    private String assignedBy;
    private String address;
    private String startTime;
    private String endTime;
    private String company;
    private String date;
    private String lat;
    private String lng;
}
