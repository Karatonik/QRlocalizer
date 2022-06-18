package pl.r.mmdd_pum_projekt.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDateTime;

public class LocationAndTime {

    private LatLng latLng;
    private LocalDateTime time;

    public LocationAndTime() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocationAndTime(LatLng latLng) {
        this.latLng = latLng;
        this.time = LocalDateTime.now();
    }


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
