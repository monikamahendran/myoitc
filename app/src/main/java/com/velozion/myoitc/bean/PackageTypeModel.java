package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PackageTypeModel implements Parcelable {

    String id;
    String package_name;
    String package_desc;
    int package_type;
    int status;
    String type_name;

    public PackageTypeModel() {

    }

    protected PackageTypeModel(Parcel in) {
        id = in.readString();
        package_name = in.readString();
        package_desc = in.readString();
        package_type = in.readInt();
        status = in.readInt();
        type_name = in.readString();
    }

    public static final Creator<PackageTypeModel> CREATOR = new Creator<PackageTypeModel>() {
        @Override
        public PackageTypeModel createFromParcel(Parcel in) {
            return new PackageTypeModel(in);
        }

        @Override
        public PackageTypeModel[] newArray(int size) {
            return new PackageTypeModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_desc() {
        return package_desc;
    }

    public void setPackage_desc(String package_desc) {
        this.package_desc = package_desc;
    }

    public int getPackage_type() {
        return package_type;
    }

    public void setPackage_type(int package_type) {
        this.package_type = package_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(package_name);
        dest.writeString(package_desc);
        dest.writeInt(package_type);
        dest.writeInt(status);
        dest.writeString(type_name);
    }
}
