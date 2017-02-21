package com.example.android.sunshine.app;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

public class MyService extends WearableListenerService {

    GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (DataEvent dataEvent : dataEventBuffer) {
            if(dataEvent.getType() == DataEvent.TYPE_CHANGED){
                DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                String path = dataEvent.getDataItem().getUri().getPath();
                if(path.equals("/weather_data")){

                    String high = dataMap.getString("high");
                    String low = dataMap.getString("low");
                    int iconId = dataMap.getInt("icon");

                    Log.e("TAG", high+" "+low);

                    SharedPreferences.Editor editor = getSharedPreferences("Prefs", MODE_PRIVATE).edit();
                    editor.putString("high_temp", high);
                    editor.putString("low_temp", low);
                    editor.putInt("icon_id", iconId);
                    editor.commit();

                }
            }
        }
    }
}
