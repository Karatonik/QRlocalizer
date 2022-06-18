package pl.r.mmdd_pum_projekt.Models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Device implements Parcelable {

   private String name;

   private List<LocationAndTime> localizationList;

   public Device(String name) {
      this.name = name;
      this.localizationList = new ArrayList<>();
   }

   protected Device(Parcel in) {
      name = in.readString();
   }

   public static final Creator<Device> CREATOR = new Creator<Device>() {
      @Override
      public Device createFromParcel(Parcel in) {
         return new Device(in);
      }

      @Override
      public Device[] newArray(int size) {
         return new Device[size];
      }
   };

   public boolean addLocalization(LocationAndTime locationAndTime){
    return  this.localizationList.add(locationAndTime);
   }
   @RequiresApi(api = Build.VERSION_CODES.O)
   public LocationAndTime getNewestLocalization(){
      if (!this.localizationList.isEmpty()){
         return this.localizationList.stream().max(Comparator.comparing(LocationAndTime::getTime)).get();
      }
      return new LocationAndTime();
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<LocationAndTime> getLocalizationList() {
      return localizationList;
   }

   public void setLocalizationList(List<LocationAndTime> localizationList) {
      this.localizationList = localizationList;
   }

   @Override
   public String toString() {
      return "name='" + name + '\'' +
              ", localizationList=" + localizationList;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(name);
   }
}
