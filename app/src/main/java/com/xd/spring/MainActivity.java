package com.xd.spring;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xd.spring.beans.XdDmfLoginBean;
import com.xd.spring.ui.view.XDViewTouch;
import com.xdroid.spring.httpRequest.XDHttpClient;
import com.xdroid.spring.httpRequest.exception.XDHttpErrType;
import com.xdroid.spring.httpRequest.listener.XDDownloadListener;
import com.xdroid.spring.httpRequest.listener.MKDataListener;
import com.xdroid.spring.httpRequest.request.XDRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.xdroid.spring.httpRequest.listener.XDJsonHandle.createFileHandler;
import static com.xdroid.spring.httpRequest.listener.XDJsonHandle.createJsonHandler;
import static com.xdroid.spring.httpRequest.request.XDRequest.createGetRequest;
import static com.xdroid.spring.httpRequest.request.XDRequest.createPostJSONRequest;
import static com.xdroid.spring.httpRequest.request.XDRequest.createPostRequest;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = findViewById(R.id.xdToolBar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("XDroid");

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        requestPermissions(null, 1);

        test2();

//        test();

//        testXDView();

//        testNotification2(this);

//        new Handler().postDelayed(() -> {
//
//            Log.e(TAG, "startService  XDService" );
//            startService(new Intent(MainActivity.this, XDService.class));
//            startForegroundService(new Intent(MainActivity.this, XDService.class));
//        }, 5000);

        //startForegroundService 与 startService 的区别

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");

    }

    /**
     * View的滑动
     */
    public void test() {
        XDViewTouch xdViewTouch = findViewById(R.id.xdViewTouch);

//        xdViewTouch.setOnClickListener((v) -> {
//            Log.e(TAG, "test: 点击事件");
//        });
//        xdViewTouch.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.e(TAG, "test: setOnTouchListener");
//                return true;
//            }
//        });

        findViewById(R.id.xdTranslate).setOnClickListener(v -> {
            xdViewTouch.smoothScrollToX(-400, -100);
        });


    }

    /**
     * 手势冲突处理
     */
    public void testXDView() {
        ListView lv_one = (ListView) this.findViewById(R.id.lv_one);
        ListView lv_two = (ListView) this.findViewById(R.id.lv_two);
        String[] strs1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, strs1);
        lv_one.setAdapter(adapter1);

        String[] strs2 = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, strs2);
        lv_two.setAdapter(adapter2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //没有授权的case走下面逻辑
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            //处理需要授权，但是被用户点击不再询问的情况
        }
    }


    public void test2() {
        String url = "http://39.105.38.116:8080/miyun/appManager/appLogin.do";//?userCode=dmf&passWord=dmf
        Map<String, Object> params = new HashMap<String, Object>() {
            {
                put("userCode", "dmf");
                put("passWord", 123);
            }
        };

        XDHttpClient.post(createPostJSONRequest(url, params), createJsonHandler(XdDmfLoginBean.class, new MKDataListener<XdDmfLoginBean>() {
            @Override
            public void onSuccess(XdDmfLoginBean res) {
                Log.e(TAG, "接口结果：" + res.toString());
            }

            @Override
            public void onFailure(XDHttpErrType err) {
                Log.e(TAG, "接口异常：" + err);
            }
        }));
        String path = getExternalFilesDir(null).getPath() + "/test.xlsx";
        Log.e(TAG, "path：" + path);


        XDHttpClient.downloadFile(XDRequest.createGetRequest("http://218.4.57.5:8889/aispeech/firstAid/task/listExcel?_t=1665972227&status=-1&startTime=2022-10-18+00:00:00&endTime=2022-10-19+00:00:00", null),
                createFileHandler(path, new XDDownloadListener() {
                    @Override
                    public void onProgress(int progress) {

                        Log.e(TAG, "onProgress：" + progress);
                    }

                    @Override
                    public void onSuccess(File res) {
                        Log.e(TAG, "onSuccess：" + res.length());

                    }

                    @Override
                    public void onFailure(XDHttpErrType err) {
                        Log.e(TAG, "onFailure：" + err);

                    }
                }));


    }


}