package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckInFormServiceData implements Parcelable {

    private String id;
    private String name;

    public CheckInFormServiceData() {

    }

    public CheckInFormServiceData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected CheckInFormServiceData(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<CheckInFormServiceData> CREATOR = new Creator<CheckInFormServiceData>() {
        @Override
        public CheckInFormServiceData createFromParcel(Parcel in) {
            return new CheckInFormServiceData(in);
        }

        @Override
        public CheckInFormServiceData[] newArray(int size) {
            return new CheckInFormServiceData[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }
}
