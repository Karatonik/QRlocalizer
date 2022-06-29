package pl.r.mmdd_pum_projekt;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

import pl.r.mmdd_pum_projekt.Models.Device;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private final Context context;
    private final List<Device> devices;

    public CustomAdapter(Context context,
                         List<Device> devices) {
        this.context = context;
        this.devices = devices;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.deviceName.setText(String.valueOf(devices.stream()
                .map(Device::getName).collect(Collectors.toList()).get(position)));

        holder.localization.setOnClickListener(v -> {

            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra("deviceName", devices.get(position).getName());
            intent.putExtra("latLng", devices.get(position).getLatLng());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        holder.qrBtn.setOnClickListener(v -> {

            Intent intent = new Intent(context, QRCode_Activity.class);
            intent.putExtra("deviceName", devices.get(position).getName());
            intent.putExtra("latLng", devices.get(position).getLatLng());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName;
        Button localization;
        Button qrBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.deviceName);
            localization = itemView.findViewById(R.id.deviceLocalization);
            qrBtn = itemView.findViewById(R.id.deviceInfo);
        }
    }
}
