package com.velozion.myoitc.bean;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.velozion.myoitc.Utils;

public class UserProfileBean implements Parcelable {

    Context context;

    String id;
    String pic;
    String name;
    String username;
    String email;
    String phone_no;
    String state;
    String city;
    String zipcode;


    public UserProfileBean(Context context) {
        this.context = context;
        Utils.ImageLoaderInitialization(context);
    }


    protected UserProfileBean(Parcel in) {
        id = in.readString();
        pic = in.readString();
        name = in.readString();
        username = in.readString();
        email = in.readString();
        phone_no = in.readString();
        state = in.readString();
        city = in.readString();
        zipcode = in.readString();
    }

    public static final Creator<UserProfileBean> CREATOR = new Creator<UserProfileBean>() {
        @Override
        public UserProfileBean createFromParcel(Parcel in) {
            return new UserProfileBean(in);
        }

        @Override
        public UserProfileBean[] newArray(int size) {
            return new UserProfileBean[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getPic() {
        return pic;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


    @BindingAdapter({"android:profileImage"})
    public static void loadImage(ImageView view, String imageUrl) {
        Utils.LoadImage(imageUrl, view);

    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pic);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(phone_no);
        dest.writeString(state);
        dest.writeString(city);
        dest.writeString(zipcode);
    }
}
