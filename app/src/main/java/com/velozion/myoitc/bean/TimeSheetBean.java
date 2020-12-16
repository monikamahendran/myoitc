package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeSheetBean implements Parcelable {

    public TimeSheetBean(String date, String day, String timeIn, String timeOut, String payCode, String serviceType, String clientInitial, String clientDesignee, String totalHours, String verifiedBy, String verifiedOn, String status, String employee) {
        this.date = date;
        this.day = day;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.payCode = payCode;
        this.serviceType = serviceType;
        this.clientInitial = clientInitial;
        this.clientDesignee = clientDesignee;
        this.totalHours = totalHours;
        this.verifiedBy = verifiedBy;
        this.verifiedOn = verifiedOn;
        this.status = status;
        this.employee = employee;
    }

    protected TimeSheetBean(Parcel in) {
        date = in.readString();
        day = in.readString();
        timeIn = in.readString();
        timeOut = in.readString();
        payCode = in.readString();
        serviceType = in.readString();
        clientInitial = in.readString();
        clientDesignee = in.readString();
        totalHours = in.readString();
        verifiedBy = in.readString();
        verifiedOn = in.readString();
        status = in.readString();
        employee = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(day);
        dest.writeString(timeIn);
        dest.writeString(timeOut);
        dest.writeString(payCode);
        dest.writeString(serviceType);
        dest.writeString(clientInitial);
        dest.writeString(clientDesignee);
        dest.writeString(totalHours);
        dest.writeString(verifiedBy);
        dest.writeString(verifiedOn);
        dest.writeString(status);
        dest.writeString(employee);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimeSheetBean> CREATOR = new Creator<TimeSheetBean>() {
        @Override
        public TimeSheetBean createFromParcel(Parcel in) {
            return new TimeSheetBean(in);
        }

        @Override
        public TimeSheetBean[] newArray(int size) {
            return new TimeSheetBean[size];
        }
    };

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public String getPayCode() {
        return payCode;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getClientInitial() {
        return clientInitial;
    }

    public String getClientDesignee() {
        return clientDesignee;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public String getVerifiedOn() {
        return verifiedOn;
    }

    public String getStatus() {
        return status;
    }

    public String getEmployee() {
        return employee;
    }

    private String date;
    private String day;
    private String timeIn;
    private String timeOut;
    private String payCode;
    private String serviceType;
    private String clientInitial;
    private String clientDesignee;
    private String totalHours;
    private String verifiedBy;
    private String verifiedOn;
    private String status;
    private String employee;


}
