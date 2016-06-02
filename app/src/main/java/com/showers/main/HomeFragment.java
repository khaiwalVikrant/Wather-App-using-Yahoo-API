package com.showers.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.showers.R;
import com.showers.model.ViewModel;
import com.showers.restModel.Root;
import com.showers.restclient.RestClient;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;


/**
 * Created by vikrant on 01/6/16.
 */
public class HomeFragment extends Fragment{
    private SimpleDateFormat srcFormatter=new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView recyclerView;
    private static List<ViewModel> items = new ArrayList<>();
    private View root;
    private AppCompatActivity mContext;
    // UI elements
    private TextView cityText, dayDateText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;
    private TextView unitTemp;
    private TextView visibility;
    private TextView hum;
//    private TextView tempMin;
//    private TextView tempMax;
    private TextView sunset;
    private TextView sunrise;
    private LinearLayout dayView;
    private LinearLayout mainView;
    private TextView colorTextLine;
    private TextView rain;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        root = (View) inflater.inflate(R.layout.content_main, null);
        context = getActivity();
        mainView = (LinearLayout) root.findViewById(R.id.mainView);
        dayView = (LinearLayout) root.findViewById(R.id.dayView);
        dayDateText = (TextView) root.findViewById(R.id.dayDate);
        cityText = (TextView) root.findViewById(R.id.location);
        temp = (TextView) root.findViewById(R.id.temp);
        condDescr = (TextView) root.findViewById(R.id.descrWeather);
        hum = (TextView) root.findViewById(R.id.humidity);
        press = (TextView) root.findViewById(R.id.pressure);
        windSpeed = (TextView) root.findViewById(R.id.windSpeed);
        windDeg = (TextView) root.findViewById(R.id.windDeg);
//        tempMin = (TextView) root.findViewById(R.id.tempMin);
//        tempMax = (TextView) root.findViewById(R.id.tempMax);
        unitTemp = (TextView) root.findViewById(R.id.tempUnit);
        visibility = (TextView) root.findViewById(R.id.visibility);
        sunrise = (TextView) root.findViewById(R.id.sunrise);
        sunset = (TextView) root.findViewById(R.id.sunset);
        //cloud = (TextView) root.findViewById(R.id.cloud);
        //rain = (TextView) root.findViewById(R.id.rain);
        getHomePageImages();
        return root;
    }

    private void getHomePageImages() {

        RestClient.RestInterface service = RestClient.getClient(getActivity());
        Call<Root> call = service.getYahooWeather();
        call.enqueue(new retrofit2.Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, retrofit2.Response<Root> response) {
                if (response != null && response.isSuccessful() && response.errorBody() == null){
                    Date createdDate = null;
                    try {
                        createdDate = srcFormatter.parse(response.body().getQuery().getCreated());
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    SunView sunView = new SunView(context);
                    MoonView moonView = new MoonView(context);
                    SkyconView skyconView = new SkyconView(context);
                    CloudThunderView thunderView = new CloudThunderView(context);
                    CloudRainView cloudRainView = new CloudRainView(context);
                    CloudHvRainView cloudHvRainView = new CloudHvRainView(context);
                    CloudMoonView cloudMoonView = new CloudMoonView(context);
                    CloudSunView cloudSunView = new CloudSunView(context);
                    CloudView cloudView = new CloudView(context);
                    WindView windView = new WindView(context);

                    sunView.setBgColor(context.getResources().getColor(android.R.color.transparent));
                    cloudHvRainView.setBgColor(context.getResources().getColor(android.R.color.transparent));
                    skyconView.setBgColor(context.getResources().getColor(android.R.color.transparent));

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
                    int dayCode = Integer.parseInt(response.body().getQuery().getResults().getChannel().getItem().getCondition().getCode());
                    final int sdk = android.os.Build.VERSION.SDK_INT;

                    if(dayCode == 27 || dayCode == 29){
                        //mostly cloudy (night)
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            mainView.setBackgroundDrawable( getResources().getDrawable(R.drawable.rainview) );
                        } else {
                            mainView.setBackground( getResources().getDrawable(R.drawable.rainview));
                        }
                    }else if(dayCode == 28 || dayCode == 30){
                        //mostly cloudy (day)
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            mainView.setBackgroundDrawable( getResources().getDrawable(R.drawable.sunny) );
                        } else {
                            mainView.setBackground( getResources().getDrawable(R.drawable.sunny));
                        }
                    }
                    else if(dayCode == 31 || dayCode == 33){
                        //clear (night)
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            mainView.setBackgroundDrawable( getResources().getDrawable(R.drawable.clearnight) );
                        } else {
                            mainView.setBackground( getResources().getDrawable(R.drawable.clearnight));
                        }
                    }
                    else if(dayCode == 32 || dayCode == 34){
                        //fair (day)
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            mainView.setBackgroundDrawable( getResources().getDrawable(R.drawable.cleanweather) );
                        } else {
                            mainView.setBackground( getResources().getDrawable(R.drawable.cleanweather));
                        }
                    }

                    if(dayCode == 4 || dayCode == 47){
                        dayView.addView(thunderView);
                    }
                    else if(dayCode == 11 || dayCode == 12){
                        dayView.addView(cloudRainView);
                    }
                    else if(dayCode == 23){
                        dayView.addView(windView);
                    }
                    else if(dayCode == 29){
                        dayView.addView(cloudMoonView);
                    }
                    else if(dayCode == 28 || dayCode == 30){
                        dayView.addView(cloudSunView);
                    }else if(dayCode == 27 || dayCode == 31 || dayCode == 33){
                        moonView.setStrokeColor(getResources().getColor(R.color.color_white));
                        dayView.addView(moonView);
                    }
                    else if(dayCode == 32 || dayCode == 34){
                        dayView.addView(sunView);
                    }
                    else if(dayCode == 39){
                        dayView.addView(cloudHvRainView);
                    }
                    else if(dayCode == 44 || dayCode == 26){
                        dayView.addView(cloudView);
                    }


                    SimpleDateFormat expectedDateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
                    cityText.setText(response.body().getQuery().getResults().getChannel().getLocation().getCity() + "," + response.body().getQuery().getResults().getChannel().getLocation().getCountry());
                    condDescr.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getText());
                    dayDateText.setText(expectedDateFormat.format(createdDate));
                    temp.setText("" + UnitUtility.toCelcius(Float.parseFloat(response.body().getQuery().getResults().getChannel().getItem().getCondition().getTemp())));
                    unitTemp.setText(UnitUtility.tempUnit(response.body().getQuery().getResults().getChannel().getUnits().getTemperature()));
                    visibility.setText(response.body().getQuery().getResults().getChannel().getAtmosphere().getVisibility() + response.body().getQuery().getResults().getChannel().getUnits().getDistance());
                    hum.setText(response.body().getQuery().getResults().getChannel().getAtmosphere().getHumidity() + "%");
                    windSpeed.setText(Float.parseFloat(response.body().getQuery().getResults().getChannel().getWind().getSpeed()) + UnitUtility.speedUnit(response.body().getQuery().getResults().getChannel().getUnits().getSpeed()));
                    press.setText(response.body().getQuery().getResults().getChannel().getAtmosphere().getPressure() + response.body().getQuery().getResults().getChannel().getUnits().getPressure());
                    sunrise.setText(response.body().getQuery().getResults().getChannel().getAstronomy().getSunrise());
                    sunset.setText(response.body().getQuery().getResults().getChannel().getAstronomy().getSunset());

                }else{

                }
            }
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
