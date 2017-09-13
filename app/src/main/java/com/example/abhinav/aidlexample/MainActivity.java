package com.example.abhinav.aidlexample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ServiceConnection serviceConnection;
    IRemoteService mRemoteService;
    Button button;
    Handler handler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        Intent intent=new Intent(this,CustomService.class);
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.v("serviceconnection","connected");
                mRemoteService = IRemoteService.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                Log.v("serviceconnection","disconnected");
            }
        };
        startService(new Intent(this,CustomService.class));
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(MainActivity.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message=new Message();
                    try {
                        message.obj=mRemoteService.getThings();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                  handler.sendMessage(message);
                }
            }).start();

        }
    }
}
