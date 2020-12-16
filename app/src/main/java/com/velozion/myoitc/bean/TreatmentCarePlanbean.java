package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TreatmentCarePlanbean implements Parcelable {

    private String client_name;
    private String primaryDiagnosis;
    private String serviceType;
    private String serviceLocation;
    private String duration;
    private String frequency;
    private String hoursPerVisit;
    private String assignedEmployee;
    private String status;

    protected TreatmentCarePlanbean(Parcel in) {
        client_name = in.readString();
        primaryDiagnosis = in.readString();
        serviceType = in.readString();
        serviceLocation = in.readString();
        duration = in.readString();
        frequency = in.readString();
        hoursPerVisit = in.readString();
        assignedEmployee = in.readString();
        status = in.readString();
    }

    public static final Creator<TreatmentCarePlanbean> CREATOR = new Creator<TreatmentCarePlanbean>() {
        @Override
        public TreatmentCarePlanbean createFromParcel(Parcel in) {
            return new TreatmentCarePlanbean( in );
        }

        @Override
        public TreatmentCarePlanbean[] newArray(int size) {
            return new TreatmentCarePlanbean[size];
        }
    };

    public String getClient_name() {
        return client_name;
    }

    public String getPrimaryDiagnosis() {
        return primaryDiagnosis;
    }

    public TreatmentCarePlanbean(String client_name, String primaryDiagnosis, String serviceType, String serviceLocation, String duration, String frequency, String hoursPerVisit, String assignedEmployee, String status) {
        this.client_name = client_name;
        this.primaryDiagnosis = primaryDiagnosis;
        this.serviceType = serviceType;
        this.serviceLocation = serviceLocation;
        this.duration = duration;
        this.frequency = frequency;
        this.hoursPerVisit = hoursPerVisit;
        this.assignedEmployee = assignedEmployee;
        this.status = status;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public String getDuration() {
        return duration;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getHoursPerVisit() {
        return hoursPerVisit;
    }

    public String getAssignedEmployee() {
        return assignedEmployee;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString( client_name );
        parcel.writeString( primaryDiagnosis );
        parcel.writeString( serviceType );
        parcel.writeString( serviceLocation );
        parcel.writeString( duration );
        parcel.writeString( frequency );
        parcel.writeString( hoursPerVisit );
        parcel.writeString( assignedEmployee );
        parcel.writeString( status );
    }
}
