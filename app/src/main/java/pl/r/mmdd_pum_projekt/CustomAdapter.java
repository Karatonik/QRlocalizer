package pl.r.mmdd_pum_projekt;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import pl.r.mmdd_pum_projekt.Helpers.NotifyHelper;
import pl.r.mmdd_pum_projekt.Models.Device;
import pl.r.mmdd_pum_projekt.Models.LocationAndTime;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private Context context;
    private List<Device> devices;

    public CustomAdapter(Context context,
                         List<Device> devices) {
        this.context = context;
        this.devices = devices;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.deviceName.setText(String.valueOf(devices.stream()
                .map(Device::getName).collect(Collectors.toList()).get(position)));

        holder.localization.setOnClickListener(v->{
            System.out.println(devices.get(position));

            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra("device",devices.get(position));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("local",  devices.get(position).getNewestLocalization());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName;
        Button localization;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.deviceName);
            localization = itemView.findViewById(R.id.deviceLocalization);

        }
    }
}
