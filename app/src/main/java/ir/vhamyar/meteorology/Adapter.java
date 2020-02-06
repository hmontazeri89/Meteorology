package ir.vhamyar.meteorology;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import ir.vhamyar.meteorology.util.Helper;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context mContext;
    private List<ir.vhamyar.meteorology.model.forecast.List> lists;

    public Adapter(Context mContext, List<ir.vhamyar.meteorology.model.forecast.List> lists) {
        this.mContext = mContext;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.forecast_list_item, parent, false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] date_and_time = lists.get(position).getDtTxt().split(" ");
        String date = date_and_time[0];
        String fullTime = date_and_time[1];
        String time = fullTime.split(":")[0] + ":" + fullTime.split(":")[1];

        setDayOfWeek(holder, position, time);

        holder.tv_temp.setText(String.format("%s %s",
                Helper.convert(String.valueOf(lists.get(position).getMain().getTemp().intValue())),
                mContext.getResources().getString(R.string.celsius)));
        // http://openweathermap.org/img/wn/10d@2x.png

        int iconId = lists.get(position).getWeather().get(0).getId();

        Helper.setWeatherIcon(mContext, holder.img_weather, String.valueOf(iconId));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    private void setDayOfWeek(ViewHolder holder, int position, String time) {
        Timestamp ts = new Timestamp((long) lists.get(position).getDt() * 1000);
        Date date = new Date(ts.getTime());
        switch (date.toString().split(" ")[0].toLowerCase()) {
            case "sun":
                holder.tv_day.setText(String.format("%s\n%s", mContext.getResources().getString(R.string.Sunday), Helper.convert(time)));
                break;
            case "mon":
                holder.tv_day.setText(String.format("%s\n%s", mContext.getResources().getString(R.string.Monday), Helper.convert(time)));
                break;
            case "tue":
                holder.tv_day.setText(String.format("%s\n%s", mContext.getResources().getString(R.string.Tuesday), Helper.convert(time)));
                break;
            case "wed":
                holder.tv_day.setText(String.format("%s\n%s", mContext.getResources().getString(R.string.Wednesday), Helper.convert(time)));
                break;
            case "thu":
                holder.tv_day.setText(String.format("%s\n%s", mContext.getResources().getString(R.string.Thursday), Helper.convert(time)));
                break;
            case "fri":
                holder.tv_day.setText(String.format("%s\n%s", mContext.getResources().getString(R.string.Friday), Helper.convert(time)));
                break;
            case "sat":
                holder.tv_day.setText(String.format("%s\n%s", mContext.getResources().getString(R.string.Saturday), Helper.convert(time)));
                break;
            default:
                holder.tv_day.setText("؟");
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_day, tv_temp;
        ImageView img_weather;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_temp = itemView.findViewById(R.id.tv_temp);
            img_weather = itemView.findViewById(R.id.img_weather);
        }
    }
}
