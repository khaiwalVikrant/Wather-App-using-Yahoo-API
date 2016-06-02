package com.showers.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.showers.R;
import com.showers.adapter.ForcastAdapter;
import com.showers.model.ViewModel;
import com.showers.restModel.Root;
import com.showers.restclient.RestClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;

/**
* Created by vikrant on 01/6/16.
*/
public class FilterFragment extends Fragment{
    private RecyclerView recyclerView;
    private LinearLayout forCastView;
    private TextView cityText, timeText;
    private static List<ViewModel> items = new ArrayList<>();
    private View root;
    private SimpleDateFormat srcFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        root = (View) inflater.inflate(R.layout.forecast_fragment, null);

        cityText = (TextView) root.findViewById(R.id.city);
        timeText = (TextView) root.findViewById(R.id.dayTime);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler);
        forCastView = (LinearLayout) root.findViewById(R.id.forCastView);
        getHomePageImages();
        return root;
    }
    private void setupRecyclerView(int view_type) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), view_type));
        ForcastAdapter rAdapter = new ForcastAdapter(items, view_type, getActivity());
        recyclerView.setAdapter(rAdapter);

    }
    private void getHomePageImages() {


        RestClient.RestInterface service = RestClient.getClient(getActivity());
        Call<Root> call = service.getYahooWeather();
        call.enqueue(new retrofit2.Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, retrofit2.Response<Root> response) {
                if (response != null && response.isSuccessful() && response.errorBody() == null){
                    items.clear();
                    cityText.setText(response.body().getQuery().getResults().getChannel().getLocation().getCity());

                    int end = response.body().getQuery().getResults().getChannel().getItem().getForecast().size();
                    int dayCode = Integer.parseInt(response.body().getQuery().getResults().getChannel().getItem().getCondition().getCode());
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    Date createdDate = null;

                    try {
                        SimpleDateFormat displayDateFormatter = new SimpleDateFormat("hh:mm a");
                        srcFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
                        createdDate = srcFormatter.parse(response.body().getQuery().getCreated());
                        displayDateFormatter.setTimeZone(TimeZone.getDefault());
                        timeText.setText(displayDateFormatter.format(createdDate));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }catch (NullPointerException ne){
                        ne.printStackTrace();
                    }

                    if(dayCode == 27 || dayCode == 29){
                        //mostly cloudy (night)
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            forCastView.setBackgroundDrawable( getResources().getDrawable(R.drawable.rainview) );
                        } else {
                            forCastView.setBackground( getResources().getDrawable(R.drawable.rainview));
                        }
                    }else if(dayCode == 28 || dayCode == 30){
                        //mostly cloudy (day)
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            forCastView.setBackgroundDrawable( getResources().getDrawable(R.drawable.sunny) );
                        } else {
                            forCastView.setBackground( getResources().getDrawable(R.drawable.sunny));
                        }
                    }
                    else if(dayCode == 31 || dayCode == 33){
                        //clear (night)
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            forCastView.setBackgroundDrawable( getResources().getDrawable(R.drawable.clearnight) );
                        } else {
                            forCastView.setBackground( getResources().getDrawable(R.drawable.clearnight));
                        }
                    }
                    else if(dayCode == 32 || dayCode == 34){
                        //fair (day)
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            forCastView.setBackgroundDrawable( getResources().getDrawable(R.drawable.cleanweather) );
                        } else {
                            forCastView.setBackground( getResources().getDrawable(R.drawable.cleanweather));
                        }
                    }
                    for (int i = 0; i < end; i++) {
                        ViewModel viewModel = new ViewModel();
                        viewModel.setCode(response.body().getQuery().getResults().getChannel().getItem().getForecast().get(i).getCode());
                        viewModel.setDay(response.body().getQuery().getResults().getChannel().getItem().getForecast().get(i).getDay());
                        viewModel.setDate(response.body().getQuery().getResults().getChannel().getItem().getForecast().get(i).getDate());
                        viewModel.setLow(response.body().getQuery().getResults().getChannel().getItem().getForecast().get(i).getLow());
                        viewModel.setHigh(response.body().getQuery().getResults().getChannel().getItem().getForecast().get(i).getHigh());
                        viewModel.setText(response.body().getQuery().getResults().getChannel().getItem().getForecast().get(i).getText());

                        items.add(viewModel);
                    }
                    setupRecyclerView(1);
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
