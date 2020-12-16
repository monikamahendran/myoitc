package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TravelMapBean implements Parcelable {


    private String clientName;
    private Double latitude;
    private Double longitude;

    protected TravelMapBean(Parcel in) {
        clientName = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clientName);
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TravelMapBean> CREATOR = new Creator<TravelMapBean>() {
        @Override
        public TravelMapBean createFromParcel(Parcel in) {
            return new TravelMapBean(in);
        }

        @Override
        public TravelMapBean[] newArray(int size) {
            return new TravelMapBean[size];
        }
    };

    public String getClientName() {
        return clientName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public TravelMapBean(String clientName, Double latitude, Double longitude) {
        this.clientName = clientName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
