package pl.r.mmdd_pum_projekt.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class LocationAndTime {

    private double x;
    private double y;
    private LocalDateTime time;

    public LocationAndTime() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocationAndTime(double x, double y) {
        this.x = x;
        this.y = y;
        this.time = LocalDateTime.now();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return
                "x=" + x +
                ", y=" + y +
                ", time=" + time;
    }
}
