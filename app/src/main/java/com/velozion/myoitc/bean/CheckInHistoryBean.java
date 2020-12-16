package com.velozion.myoitc.bean;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public class CheckInHistoryBean implements Parcelable {


    public CheckInHistoryBean(String clientName, String serviceName, String payloadName, String lunchTime, String clientInitial, String checkinTime, String checkinLat, String checkinLong, String checkinAddress, String checkoutTime, String checkoutLat, String checkoutLong, String checkoutAddress,String signatureImage) {
        this.clientName = clientName;
        this.serviceName = serviceName;
        this.payloadName = payloadName;
        this.lunchTime = lunchTime;
        this.clientInitial = clientInitial;
        this.checkinTime = checkinTime;
        this.checkinLat = checkinLat;
        this.checkinLong = checkinLong;
        this.checkinAddress = checkinAddress;
        this.checkoutTime = checkoutTime;
        this.checkoutLat = checkoutLat;
        this.checkoutLong = checkoutLong;
        this.checkoutAddress = checkoutAddress;
        this.signatureImage=signatureImage;
    }

    private CheckInHistoryBean(Parcel in) {
        clientName = in.readString();
        serviceName = in.readString();
        payloadName = in.readString();
        lunchTime = in.readString();
        clientInitial = in.readString();
        checkinTime = in.readString();
        checkinLat = in.readString();
        checkinLong = in.readString();
        checkinAddress = in.readString();
        checkoutTime = in.readString();
        checkoutLat = in.readString();
        checkoutLong = in.readString();
        checkoutAddress = in.readString();
        signatureImage=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clientName);
        dest.writeString(serviceName);
        dest.writeString(payloadName);
        dest.writeString(lunchTime);
        dest.writeString(clientInitial);
        dest.writeString(checkinTime);
        dest.writeString(checkinLat);
        dest.writeString(checkinLong);
        dest.writeString(checkinAddress);
        dest.writeString(checkoutTime);
        dest.writeString(checkoutLat);
        dest.writeString(checkoutLong);
        dest.writeString(checkoutAddress);
        dest.writeString( signatureImage );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CheckInHistoryBean> CREATOR = new Creator<CheckInHistoryBean>() {
        @Override
        public CheckInHistoryBean createFromParcel(Parcel in) {
            return new CheckInHistoryBean(in);
        }

        @Override
        public CheckInHistoryBean[] newArray(int size) {
            return new CheckInHistoryBean[size];
        }
    };

    public String getClientName() {
        return clientName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPayloadName() {
        return payloadName;
    }

    public String getLunchTime() {
        return lunchTime;
    }

    public String getClientInitial() {
        return clientInitial;
    }


    public String getCheckinTime() {
        return checkinTime;
    }

    public String getCheckinLat() {
        return checkinLat;
    }

    public String getCheckinLong() {
        return checkinLong;
    }

    public String getCheckinAddress() {
        return checkinAddress;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public String getCheckoutLat() {
        return checkoutLat;
    }

    public String getCheckoutLong() {
        return checkoutLong;
    }

    public String getCheckoutAddress() {
        return checkoutAddress;
    }
    public String getSignatureImage(){
        return signatureImage;
    }

    private String clientName;
    private String serviceName;
    private String payloadName;
    private String lunchTime;
    private String clientInitial;


    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    private String checkinTime;
    private String checkinLat;
    private String checkinLong;
    private String checkinAddress;
    private String checkoutTime;
    private String checkoutLat;
    private String checkoutLong;
    private String checkoutAddress;
    private String signatureImage;



}
