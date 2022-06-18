package pl.r.mmdd_pum_projekt.Models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDateTime;

public class LocationAndTime implements Parcelable {

    private LatLng latLng;
    private LocalDateTime time;

    public LocationAndTime() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocationAndTime(LatLng latLng) {
        this.latLng = latLng;
        this.time = LocalDateTime.now();
    }


    protected LocationAndTime(Parcel in) {
        latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(latLng, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LocationAndTime> CREATOR = new Creator<LocationAndTime>() {
        @Override
        public LocationAndTime createFromParcel(Parcel in) {
            return new LocationAndTime(in);
        }

        @Override
        public LocationAndTime[] newArray(int size) {
            return new LocationAndTime[size];
        }
    };

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "LocationAndTime{" +
                "latLng=" + latLng +
                ", time=" + time +
                '}';
    }
}
