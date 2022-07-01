package pl.r.mmdd_pum_projekt.Models;

import androidx.annotation.NonNull;

import com.google.firebase.database.PropertyName;

import java.util.Objects;

public class Device {


    private String name;
    private LatLng latLng;

    public Device(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public Device() {
    }

    @PropertyName("LatLng")
    public LatLng getLatLng() {
        return latLng;
    }

    @PropertyName("LatLng")
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @NonNull
    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", latLng=" + latLng +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(name, device.name) && Objects.equals(latLng, device.latLng);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latLng);
    }
}
