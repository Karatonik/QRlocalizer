package pl.r.mmdd_pum_projekt.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Device {

   private String name;

   private List<LocationAndTime> localizationList;

   public Device(String name) {
      this.name = name;
      this.localizationList = new ArrayList<>();
   }

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
}
