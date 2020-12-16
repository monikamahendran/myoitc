package com.velozion.myoitc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class EmployeeListBean implements Parcelable {

    private String emp_id;
    private String emp_name;
    private String emp_designation;
    private String emp_comp;
    private String ttype;
    private String subdate;
    private String status;
    private String emp_list_date;
    private String emp_work_hrs;
    private String emp_leave;
    private String emp_total;

    public EmployeeListBean() {
    }

    protected EmployeeListBean(Parcel in) {
        emp_id = in.readString();
        emp_name = in.readString();
        emp_designation = in.readString();
        emp_comp = in.readString();
        ttype = in.readString();
        subdate = in.readString();
        status = in.readString();
        emp_list_date = in.readString();
        emp_work_hrs = in.readString();
        emp_leave = in.readString();
        emp_total = in.readString();
    }

    public static final Creator<EmployeeListBean> CREATOR = new Creator<EmployeeListBean>() {
        @Override
        public EmployeeListBean createFromParcel(Parcel in) {
            return new EmployeeListBean(in);
        }

        @Override
        public EmployeeListBean[] newArray(int size) {
            return new EmployeeListBean[size];
        }
    };

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public void setEmp_designation(String emp_designation) {
        this.emp_designation = emp_designation;
    }

    public void setEmp_list_date(String emp_list_date) {
        this.emp_list_date = emp_list_date;
    }

    public void setEmp_work_hrs(String emp_work_hrs) {
        this.emp_work_hrs = emp_work_hrs;
    }

    public void setEmp_leave(String emp_leave) {
        this.emp_leave = emp_leave;
    }

    public void setEmp_total(String emp_total) {
        this.emp_total = emp_total;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public String getEmp_designation() {
        return emp_designation;
    }

    public String getEmp_list_date() {
        return emp_list_date;
    }

    public String getEmp_work_hrs() {
        return emp_work_hrs;
    }

    public String getEmp_leave() {
        return emp_leave;
    }

    public String getEmp_total() {
        return emp_total;
    }


    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_comp() {
        return emp_comp;
    }

    public void setEmp_comp(String emp_comp) {
        this.emp_comp = emp_comp;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public String getSubdate() {
        return subdate;
    }

    public void setSubdate(String subdate) {
        this.subdate = subdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(emp_id);
        dest.writeString(emp_name);
        dest.writeString(emp_designation);
        dest.writeString(emp_comp);
        dest.writeString(ttype);
        dest.writeString(subdate);
        dest.writeString(status);
        dest.writeString(emp_list_date);
        dest.writeString(emp_work_hrs);
        dest.writeString(emp_leave);
        dest.writeString(emp_total);
    }
}
