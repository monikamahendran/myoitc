package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeSheetTypeModel implements Parcelable {

    String id;
    String name;

    public TimeSheetTypeModel() {

    }

    public TimeSheetTypeModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected TimeSheetTypeModel(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<TimeSheetTypeModel> CREATOR = new Creator<TimeSheetTypeModel>() {
        @Override
        public TimeSheetTypeModel createFromParcel(Parcel in) {
            return new TimeSheetTypeModel(in);
        }

        @Override
        public TimeSheetTypeModel[] newArray(int size) {
            return new TimeSheetTypeModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
