package com.xd.spring;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;

//import com.xdroid.spring.httpRequest.XDHttpClient;
//import com.xdroid.spring.httpRequest.exception.XDHttpErrType;
//import com.xdroid.spring.httpRequest.listener.MKDataListener;
//import com.xdroid.spring.httpRequest.listener.XDJsonHandle;
//import com.xdroid.spring.httpRequest.request.XDRequest;

import java.util.HashMap;

public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        XDHttpClient.post(XDRequest.createPostJSONRequest("",new HashMap<String,String>(){
//
//        }), XDJsonHandle.createJsonHandler(String.class, new MKDataListener<String>() {
//            @Override
//            public void onSuccess(String res) {
//
//            }
//
//            @Override
//            public void onFailure(XDHttpErrType err) {
//
//            }
//        }));

    }

}