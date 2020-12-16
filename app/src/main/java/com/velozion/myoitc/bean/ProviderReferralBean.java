package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ProviderReferralBean implements Parcelable {
    public ProviderReferralBean(String client_name, String requestType, String serviceType, String serviceDate, String total_hrs, String assignedemployee, String status) {
        this.client_name = client_name;
        this.requestType = requestType;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.total_hrs = total_hrs;
        this.assignedemployee = assignedemployee;
        this.status = status;
    }

    private String client_name;
    private String requestType;
    private String serviceType;
    private String serviceDate;
    private String total_hrs;
    private String assignedemployee;

    protected ProviderReferralBean(Parcel in) {
        client_name = in.readString();
        requestType = in.readString();
        serviceType = in.readString();
        serviceDate = in.readString();
        total_hrs = in.readString();
        assignedemployee = in.readString();
        status = in.readString();
    }

    public static final Creator<ProviderReferralBean> CREATOR = new Creator<ProviderReferralBean>() {
        @Override
        public ProviderReferralBean createFromParcel(Parcel in) {
            return new ProviderReferralBean( in );
        }

        @Override
        public ProviderReferralBean[] newArray(int size) {
            return new ProviderReferralBean[size];
        }
    };

    public String getClient_name() {
        return client_name;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public String getTotal_hrs() {
        return total_hrs;
    }

    public String getAssignedemployee() {
        return assignedemployee;
    }

    public String getStatus() {
        return status;
    }

    private String status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString( client_name );
        parcel.writeString( requestType );
        parcel.writeString( serviceType );
        parcel.writeString( serviceDate );
        parcel.writeString( total_hrs );
        parcel.writeString( assignedemployee );
        parcel.writeString( status );
    }
}
