package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class EmployeeFolderBean implements Parcelable {

    public EmployeeFolderBean(String empId, String empName, String userName, String company, String dept, String group, String role, String position, String empImg) {
        this.empId = empId;
        this.empName = empName;
        this.userName = userName;
        this.company = company;
        this.dept = dept;
        this.group = group;
        this.role = role;
        this.position = position;
        this.empImg = empImg;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getUserName() {
        return userName;
    }

    public String getCompany() {
        return company;
    }

    public String getDept() {
        return dept;
    }

    public String getGroup() {
        return group;
    }

    public String getRole() {
        return role;
    }

    public String getPosition() {
        return position;
    }

    public String getEmpImg() {
        return empImg;
    }

    private String empId;
    private String empName;
    private String userName;
    private String company;
    private String dept;
    private String group;
    private String role;
    private String position;
    private String empImg;

    protected EmployeeFolderBean(Parcel in) {
        empId = in.readString();
        empName = in.readString();
        userName = in.readString();
        company = in.readString();
        dept = in.readString();
        group = in.readString();
        role = in.readString();
        position = in.readString();
        empImg = in.readString();
    }

    public static final Creator<EmployeeFolderBean> CREATOR = new Creator<EmployeeFolderBean>() {
        @Override
        public EmployeeFolderBean createFromParcel(Parcel in) {
            return new EmployeeFolderBean(in);
        }

        @Override
        public EmployeeFolderBean[] newArray(int size) {
            return new EmployeeFolderBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(empId);
        parcel.writeString(empName);
        parcel.writeString(userName);
        parcel.writeString(company);
        parcel.writeString(dept);
        parcel.writeString(group);
        parcel.writeString(role);
        parcel.writeString(position);
        parcel.writeString(empImg);
    }
}
