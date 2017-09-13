package com.example.abhinav.aidlexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.example.abhinav.aidlexample.*;
/**
 * Created by Abhinav on 9/11/2017.
 */

public class CustomService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IRemoteService.Stub(){

            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public String getThings() throws RemoteException {

                return "I am Aidl 1";
            }
        };
    }


}
