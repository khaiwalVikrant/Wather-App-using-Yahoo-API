package com.showers.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.showers.R;
import com.showers.model.ViewModel;
import com.showers.utility.UnitUtility;
import com.thbs.skycons.library.CloudHvRainView;
import com.thbs.skycons.library.CloudMoonView;
import com.thbs.skycons.library.CloudRainView;
import com.thbs.skycons.library.CloudSunView;
import com.thbs.skycons.library.CloudThunderView;
import com.thbs.skycons.library.CloudView;
import com.thbs.skycons.library.MoonView;
import com.thbs.skycons.library.SkyconView;
import com.thbs.skycons.library.SunView;
import com.thbs.skycons.library.WindView;

import java.util.List;

/**
 * Created by vikrant on 30/05/16.
 */
public class ForcastAdapter extends RecyclerView.Adapter<ForcastAdapter.ViewHolder> {
    private List<ViewModel> itemsData;
    private int displayType;
    private static Context context;
    public ForcastAdapter(List<ViewModel> itemsData, int view_type, Context context) {
        this.itemsData = itemsData;
        this.displayType = view_type;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ForcastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_forecast_layout, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //notifyItemChanged(position);

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        try {

            SunView sunView = new SunView(context);
            SkyconView skyconView = new SkyconView(context);
            MoonView moonView = new MoonView(context);
            CloudThunderView thunderView = new CloudThunderView(context);
            CloudRainView cloudRainView = new CloudRainView(context);
            CloudMoonView cloudMoonView = new CloudMoonView(context);
            CloudSunView cloudSunView = new CloudSunView(context);
            CloudView cloudView = new CloudView(context);
            WindView windView = new WindView(context);
            CloudHvRainView cloudHvRainView = new CloudHvRainView(context);

            sunView.setBgColor(context.getResources().getColor(android.R.color.transparent));
            skyconView.setBgColor(context.getResources().getColor(android.R.color.transparent));
            cloudHvRainView.setBgColor(context.getResources().getColor(android.R.color.transparent));
            moonView.setBgColor(context.getResources().getColor(android.R.color.transparent));
            thunderView.setBgColor(context.getResources().getColor(android.R.color.transparent));
            cloudRainView.setBgColor(context.getResources().getColor(android.R.color.transparent));
            cloudMoonView.setBgColor(context.getResources().getColor(android.R.color.transparent));
            cloudSunView.setBgColor(context.getResources().getColor(android.R.color.transparent));
            cloudView.setBgColor(context.getResources().getColor(android.R.color.transparent));
            windView.setBgColor(context.getResources().getColor(android.R.color.transparent));

            LinearLayout layout = new LinearLayout(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(params);
            int dayCode = Integer.parseInt(itemsData.get(position).getCode());

            if(dayCode == 4 || dayCode == 47){
                viewHolder.imageViewdayIcon.addView(thunderView);
            }
            else if(dayCode == 11 || dayCode == 12){
                viewHolder.imageViewdayIcon.addView(cloudRainView);
            }//23
            else if(dayCode == 23){
                viewHolder.imageViewdayIcon.addView(cloudMoonView);
            }
            else if(dayCode == 29){
                viewHolder.imageViewdayIcon.addView(windView);
            }
            else if(dayCode == 28 || dayCode == 30){
                viewHolder.imageViewdayIcon.addView(cloudSunView);
            }else if(dayCode == 27 || dayCode == 31 || dayCode == 33){
                viewHolder.imageViewdayIcon.addView(moonView);
            }
            else if(dayCode == 32 || dayCode == 34){
                viewHolder.imageViewdayIcon.addView(sunView);
            }
            else if(dayCode == 39){
                viewHolder.imageViewdayIcon.addView(cloudHvRainView);
            }
            else if(dayCode == 44 || dayCode == 26){
                viewHolder.imageViewdayIcon.addView(cloudView);
            }
                viewHolder.textviewDayName.setText(itemsData.get(position).getDay());
                viewHolder.textviewdayDate.setText(itemsData.get(position).getDate());
                viewHolder.textviewdayTempMin.setText(UnitUtility.toCelcius(Float.parseFloat(itemsData.get(position).getLow()))+"°");
                viewHolder.textviewdayTempMax.setText(UnitUtility.toCelcius(Float.parseFloat(itemsData.get(position).getHigh()))+"°");
                viewHolder.textviewdayDescr.setText(itemsData.get(position).getText());

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



    }

    // inner class to hold a reference to each item of RecyclerView
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LinearLayout imageViewdayIcon;
        public TextView textviewDayName;
        public TextView textviewdayDate;
        public TextView textviewdayTempMin;
        public TextView textviewdayTempMax;
        public TextView textviewdayDescr;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            context = itemLayoutView.getContext();
            itemLayoutView.setOnClickListener(this);

            textviewDayName = (TextView) itemLayoutView.findViewById(R.id.dayName);
            imageViewdayIcon = (LinearLayout) itemLayoutView.findViewById(R.id.dayIcon);
            textviewdayDate = (TextView) itemLayoutView.findViewById(R.id.dayDate);
            textviewdayTempMin = (TextView) itemLayoutView.findViewById(R.id.dayTempMin);
            textviewdayTempMax = (TextView) itemLayoutView.findViewById(R.id.dayTempMax);
            textviewdayDescr = (TextView) itemLayoutView.findViewById(R.id.dayDesc);

        }

        @Override
        public void onClick(View view) {
        }

    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}
