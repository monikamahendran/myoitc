package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckInFormPayType implements Parcelable {


    String id;
    String name;

    public CheckInFormPayType() {

    }

    protected CheckInFormPayType(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<CheckInFormPayType> CREATOR = new Creator<CheckInFormPayType>() {
        @Override
        public CheckInFormPayType createFromParcel(Parcel in) {
            return new CheckInFormPayType(in);
        }

        @Override
        public CheckInFormPayType[] newArray(int size) {
            return new CheckInFormPayType[size];
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
