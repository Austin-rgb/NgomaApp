package com.example.ngomaapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UtilityService extends Service {
    public UtilityService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}