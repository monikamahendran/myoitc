package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ClientAcknowledgementBean implements Parcelable {

    public ClientAcknowledgementBean(String client_name, String request_type, String service_type, String service_startDate, String service_endDate, String total_hrs, String admission_status, String payment_status, String total_cost) {
        this.client_name = client_name;
        this.request_type = request_type;
        this.service_type = service_type;
        this.service_startDate = service_startDate;
        this.service_endDate = service_endDate;
        this.total_hrs = total_hrs;
        this.admission_status = admission_status;
        this.payment_status = payment_status;
        this.total_cost = total_cost;
    }

    protected ClientAcknowledgementBean(Parcel in) {
        client_name = in.readString();
        request_type = in.readString();
        service_type = in.readString();
        service_startDate = in.readString();
        service_endDate = in.readString();
        total_hrs = in.readString();
        total_cost = in.readString();
        admission_status = in.readString();
        payment_status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( client_name );
        dest.writeString( request_type );
        dest.writeString( service_type );
        dest.writeString( service_startDate );
        dest.writeString( service_endDate );
        dest.writeString( total_hrs );
        dest.writeString( total_cost );
        dest.writeString( admission_status );
        dest.writeString( payment_status );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClientAcknowledgementBean> CREATOR = new Creator<ClientAcknowledgementBean>() {
        @Override
        public ClientAcknowledgementBean createFromParcel(Parcel in) {
            return new ClientAcknowledgementBean( in );
        }

        @Override
        public ClientAcknowledgementBean[] newArray(int size) {
            return new ClientAcknowledgementBean[size];
        }
    };

    public String getClient_name() {
        return client_name;
    }

    public String getRequest_type() {
        return request_type;
    }

    public String getService_type() {
        return service_type;
    }

    public String getService_startDate() {
        return service_startDate;
    }

    public String getService_endDate() {
        return service_endDate;
    }

    public String getTotal_hrs() {
        return total_hrs;
    }

    public String getAdmission_status() {
        return admission_status;
    }

    public String getPayment_status() {
        return payment_status;

    }

    public String getTotal_cost() {
        return total_cost;
    }

    private String client_name;
    private String request_type;
    private String service_type;
    private String service_startDate;
    private String service_endDate;
    private String total_hrs;
    private String total_cost;
    private String admission_status;
    private String payment_status;


}
