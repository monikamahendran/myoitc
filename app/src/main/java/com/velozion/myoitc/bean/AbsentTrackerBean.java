package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AbsentTrackerBean implements Parcelable {


    private String name;
    private String designation;
    private String month;
    private String workingDays;
    private String workingHrs;
    private String leaves;

    public AbsentTrackerBean(String name, String designation, String month, String workingDays, String workingHrs, String leaves) {
        this.name = name;
        this.designation = designation;
        this.month = month;
        this.workingDays = workingDays;
        this.workingHrs = workingHrs;
        this.leaves = leaves;
    }

    protected AbsentTrackerBean(Parcel in) {
        name = in.readString();
        designation = in.readString();
        month = in.readString();
        workingDays = in.readString();
        workingHrs = in.readString();
        leaves = in.readString();
    }

    public static final Creator<AbsentTrackerBean> CREATOR = new Creator<AbsentTrackerBean>() {
        @Override
        public AbsentTrackerBean createFromParcel(Parcel in) {
            return new AbsentTrackerBean(in);
        }

        @Override
        public AbsentTrackerBean[] newArray(int size) {
            return new AbsentTrackerBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public String getWorkingHrs() {
        return workingHrs;
    }

    public void setWorkingHrs(String workingHrs) {
        this.workingHrs = workingHrs;
    }

    public String getLeaves() {
        return leaves;
    }

    public void setLeaves(String leaves) {
        this.leaves = leaves;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(designation);
        dest.writeString(month);
        dest.writeString(workingDays);
        dest.writeString(workingHrs);
        dest.writeString(leaves);
    }
}
