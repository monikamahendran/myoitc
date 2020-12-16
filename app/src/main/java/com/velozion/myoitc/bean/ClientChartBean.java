package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ClientChartBean implements Parcelable {

    private String clientId;
    private String clientName;
    private String cliMail;
    private String cliMobile;
    private String cliImage;
    private String cliAddress;
    private String cliState;
    private String cliCity;
    private String cliCountry;
    private String zipcode;
    private ArrayList<ClientChartTaskListBean> clientTasks = null;

    public ClientChartBean(String clientId, String clientName, String cliMail, String cliMobile, String cliImage, String cliAddress) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.cliMail = cliMail;
        this.cliMobile = cliMobile;
        this.cliImage = cliImage;
        this.cliAddress = cliAddress;
    }

    public ClientChartBean(String clientId, String clientName, String cliMail, String cliMobile, String cliImage, String cliAddress, String cliState, String cliCity, String cliCountry, String zipcode, ArrayList<ClientChartTaskListBean> clientTasks) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.cliMail = cliMail;
        this.cliMobile = cliMobile;
        this.cliImage = cliImage;
        this.cliAddress = cliAddress;
        this.cliState = cliState;
        this.cliCity = cliCity;
        this.cliCountry = cliCountry;
        this.zipcode = zipcode;
        this.clientTasks = clientTasks;
    }


    protected ClientChartBean(Parcel in) {
        clientId = in.readString();
        clientName = in.readString();
        cliMail = in.readString();
        cliMobile = in.readString();
        cliImage = in.readString();
        cliAddress = in.readString();
        cliState = in.readString();
        cliCity = in.readString();
        cliCountry = in.readString();
        zipcode = in.readString();
        clientTasks = in.createTypedArrayList(ClientChartTaskListBean.CREATOR);
    }

    public static final Creator<ClientChartBean> CREATOR = new Creator<ClientChartBean>() {
        @Override
        public ClientChartBean createFromParcel(Parcel in) {
            return new ClientChartBean(in);
        }

        @Override
        public ClientChartBean[] newArray(int size) {
            return new ClientChartBean[size];
        }
    };

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getCliMail() {
        return cliMail;
    }

    public String getCliMobile() {
        return cliMobile;
    }

    public String getCliImage() {
        return cliImage;
    }

    public String getCliAddress() {
        return cliAddress;
    }

    public String getCliState() {
        return cliState;
    }

    public String getCliCity() {
        return cliCity;
    }

    public String getCliCountry() {
        return cliCountry;
    }


    public ArrayList<ClientChartTaskListBean> getClientTasks() {
        return clientTasks;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setCliMail(String cliMail) {
        this.cliMail = cliMail;
    }

    public void setCliMobile(String cliMobile) {
        this.cliMobile = cliMobile;
    }

    public void setCliImage(String cliImage) {
        this.cliImage = cliImage;
    }

    public void setCliAddress(String cliAddress) {
        this.cliAddress = cliAddress;
    }

    public void setCliState(String cliState) {
        this.cliState = cliState;
    }

    public void setCliCity(String cliCity) {
        this.cliCity = cliCity;
    }

    public void setCliCountry(String cliCountry) {
        this.cliCountry = cliCountry;
    }



    public void setClientTasks(ArrayList<ClientChartTaskListBean> clientTasks) {
        this.clientTasks = clientTasks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(clientId);
        dest.writeString(clientName);
        dest.writeString(cliMail);
        dest.writeString(cliMobile);
        dest.writeString(cliImage);
        dest.writeString(cliAddress);
        dest.writeString(cliState);
        dest.writeString(cliCity);
        dest.writeString(cliCountry);
        dest.writeString(zipcode);
        dest.writeTypedList(clientTasks);
    }


    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}