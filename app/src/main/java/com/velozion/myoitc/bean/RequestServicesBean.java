package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestServicesBean implements Parcelable {

    public RequestServicesBean(String referalDate, String clientName, String serviceType, String referalSource, String city, String state, String assignedEmployee, String status) {
        this.referalDate = referalDate;
        this.clientName = clientName;
        this.serviceType = serviceType;
        this.referalSource = referalSource;
        this.city = city;
        this.state = state;
        this.assignedEmployee = assignedEmployee;
        this.status = status;
    }

    protected RequestServicesBean(Parcel in) {
        referalDate = in.readString();
        clientName = in.readString();
        serviceType = in.readString();
        referalSource = in.readString();
        city = in.readString();
        state = in.readString();
        assignedEmployee = in.readString();
        status = in.readString();
    }

    public static final Creator<RequestServicesBean> CREATOR = new Creator<RequestServicesBean>() {
        @Override
        public RequestServicesBean createFromParcel(Parcel in) {
            return new RequestServicesBean(in);
        }

        @Override
        public RequestServicesBean[] newArray(int size) {
            return new RequestServicesBean[size];
        }
    };

    public String getReferalDate() {
        return referalDate;
    }

    public String getClientName() {
        return clientName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getReferalSource() {
        return referalSource;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getAssignedEmployee() {
        return assignedEmployee;
    }

    public String getStatus() {
        return status;
    }

    private String referalDate;
    private String clientName;
    private String serviceType;
    private String referalSource;
    private String city;
    private String state;
    private String assignedEmployee;
    private String status;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(referalDate);
        parcel.writeString(clientName);
        parcel.writeString(serviceType);
        parcel.writeString(referalSource);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(assignedEmployee);
        parcel.writeString(status);
    }
}
