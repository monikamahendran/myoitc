package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ChoosePackageBeans implements Parcelable {

    private String pacId;
    private String pacName;
    private String myoticCareMgmt;
    private String clientsUpTo;
    private String expireInDays;
    private String jobsAllow;
    private String price;
    private String colorScheme;
    private ArrayList<String> permissionArrayList;
    private String pack_type;

    public ChoosePackageBeans(String pacId, String pacName, String myoticCareMgmt, String clientsUpTo, String expireInDays, String jobsAllow, String price, String colorScheme, ArrayList<String> permissionArrayList,String type) {
        this.pacId = pacId;
        this.pacName = pacName;
        this.myoticCareMgmt = myoticCareMgmt;
        this.clientsUpTo = clientsUpTo;
        this.expireInDays = expireInDays;
        this.jobsAllow = jobsAllow;
        this.price = price;
        this.colorScheme = colorScheme;
        this.permissionArrayList = permissionArrayList;
        this.pack_type=type;
    }


    protected ChoosePackageBeans(Parcel in) {
        pacId = in.readString();
        pacName = in.readString();
        myoticCareMgmt = in.readString();
        clientsUpTo = in.readString();
        expireInDays = in.readString();
        jobsAllow = in.readString();
        price = in.readString();
        colorScheme = in.readString();
        permissionArrayList = in.createStringArrayList();
        pack_type = in.readString();
    }

    public static final Creator<ChoosePackageBeans> CREATOR = new Creator<ChoosePackageBeans>() {
        @Override
        public ChoosePackageBeans createFromParcel(Parcel in) {
            return new ChoosePackageBeans(in);
        }

        @Override
        public ChoosePackageBeans[] newArray(int size) {
            return new ChoosePackageBeans[size];
        }
    };

    public String getPacId() {
        return pacId;
    }

    public void setPacId(String pacId) {
        this.pacId = pacId;
    }

    public String getPacName() {
        return pacName;
    }

    public void setPacName(String pacName) {
        this.pacName = pacName;
    }

    public String getMyoticCareMgmt() {
        return myoticCareMgmt;
    }

    public void setMyoticCareMgmt(String myoticCareMgmt) {
        this.myoticCareMgmt = myoticCareMgmt;
    }

    public String getClientsUpTo() {
        return clientsUpTo;
    }

    public void setClientsUpTo(String clientsUpTo) {
        this.clientsUpTo = clientsUpTo;
    }

    public String getExpireInDays() {
        return expireInDays;
    }

    public void setExpireInDays(String expireInDays) {
        this.expireInDays = expireInDays;
    }

    public String getJobsAllow() {
        return jobsAllow;
    }

    public void setJobsAllow(String jobsAllow) {
        this.jobsAllow = jobsAllow;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(String colorScheme) {
        this.colorScheme = colorScheme;
    }

    public ArrayList<String> getPermissionArrayList() {
        return permissionArrayList;
    }

    public void setPermissionArrayList(ArrayList<String> permissionArrayList) {
        this.permissionArrayList = permissionArrayList;
    }

    public String getPack_type() {
        return pack_type;
    }

    public void setPack_type(String pack_type) {
        this.pack_type = pack_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pacId);
        dest.writeString(pacName);
        dest.writeString(myoticCareMgmt);
        dest.writeString(clientsUpTo);
        dest.writeString(expireInDays);
        dest.writeString(jobsAllow);
        dest.writeString(price);
        dest.writeString(colorScheme);
        dest.writeStringList(permissionArrayList);
        dest.writeString(pack_type);
    }
}
