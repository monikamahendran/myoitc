package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckInFormClientData implements Parcelable {

    private String id;
    private String name;
    private String lat;
    private String lon;

    public CheckInFormClientData() {

    }

    public CheckInFormClientData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected CheckInFormClientData(Parcel in) {
        id = in.readString();
        name = in.readString();
        lat = in.readString();
        lon = in.readString();
    }

    public static final Creator<CheckInFormClientData> CREATOR = new Creator<CheckInFormClientData>() {
        @Override
        public CheckInFormClientData createFromParcel(Parcel in) {
            return new CheckInFormClientData( in );
        }

        @Override
        public CheckInFormClientData[] newArray(int size) {
            return new CheckInFormClientData[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
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

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString( id );
        parcel.writeString( name );
    }
}
