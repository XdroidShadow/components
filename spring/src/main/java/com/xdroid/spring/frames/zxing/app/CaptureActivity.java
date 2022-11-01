/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xdroid.spring.frames.zxing.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xdroid.spring.R;
import com.xdroid.spring.frames.zxing.camera.CameraManager;
import com.xdroid.spring.frames.zxing.decode.BeepManager;
import com.xdroid.spring.frames.zxing.decode.CaptureActivityHandler;
import com.xdroid.spring.frames.zxing.decode.DecodeFormatManager;
import com.xdroid.spring.frames.zxing.decode.FinishListener;
import com.xdroid.spring.frames.zxing.decode.InactivityTimer;
import com.xdroid.spring.frames.zxing.decode.Intents;
import com.xdroid.spring.frames.zxing.decode.RGBLuminanceSource;
import com.xdroid.spring.frames.zxing.util.Util;
//import com.xdroid.spring.frames.zxing.util.ZxingCode;
import com.xdroid.spring.frames.zxing.util.ZxingCode;
import com.xdroid.spring.frames.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.xdroid.spring.util.androids.tool.XDLog;

import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import static com.xdroid.spring.frames.zxing.util.ZxingCode.RESPONSE_CODE_QR;


/**
 *   扫码界面
 */
public final class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private static final long INTENT_RESULT_DURATION = 1500L;
    private static final long BULK_MODE_SCAN_DELAY_MS = 1000L;

    private static final String PRODUCT_SEARCH_URL_PREFIX = "http://www.google";
    private static final String PRODUCT_SEARCH_URL_SUFFIX = "/m/products/scan";
    private static final String ZXING_URL = "http://zxing.appspot.com/scan";
    private static final String RETURN_URL_PARAM = "ret";

    private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES;

    private RelativeLayout mkFlashContainer;
    /**
     *   传感器
     */
    private SensorManager sensorManager ;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
//            MKLog.e("光照强度 ："+event.values[0]);

            boolean flag = event.values[0]>80? false:true;
            showFlashImage(flag);
//            if (flag) {
//                CameraManager.get().turnLightOn();
//            } else {
//                CameraManager.get().turnLightOff();
//            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    static {
        DISPLAYABLE_METADATA_TYPES = new HashSet<ResultMetadataType>(5);
        DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.ISSUE_NUMBER);
        DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.SUGGESTED_PRICE);
        DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.ERROR_CORRECTION_LEVEL);
        DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.POSSIBLE_COUNTRY);
    }

    private enum Source {
        NATIVE_APP_INTENT, PRODUCT_SEARCH_LINK, ZXING_LINK, NONE
    }

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private Button mButtonBack;
    private Button createBtn;
    private Button photoBtn;
    private Button flashBtn;

    private Result lastResult;
    private boolean hasSurface;
    private Source source;
    private String sourceUrl;
    private String returnUrlTemplate;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private boolean isFlash = false;

    private int REQUEST_CODE = 3;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    @Override
    public void onCreate(Bundle icicle) {
        XDLog.e(TAG,"onCreate");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_qrcode_capture_layout);

        //验证是否有相机权限
        mkFlashContainer = findViewById(R.id.mkFlashContainer);


        Util.currentActivity = this;
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        mButtonBack = (Button) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(click);
        createBtn = (Button) findViewById(R.id.qrcode_btn);
        createBtn.setOnClickListener(click);
        photoBtn = (Button) findViewById(R.id.photo_btn);
        photoBtn.setOnClickListener(click);
        flashBtn = (Button) findViewById(R.id.flash_btn);
        flashBtn.setOnClickListener(click);

        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        handler = null;
        lastResult = null;
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        // showHelpOnFirstLaunch();
        //传感器
        loadSensor();
    }


    /**
     *   传感器
     */
    private void loadSensor(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     *   控制 电筒 图标显示/隐藏
     */
    private void showFlashImage(boolean isFlash){
        if (isFlash){
            mkFlashContainer.setVisibility(View.VISIBLE);
        }else {
            mkFlashContainer.setVisibility(View.GONE);
            CameraManager.get().turnLightOff();
        }
    }

    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    /**
     * 闪光灯点击事件
     */
    private OnClickListener click = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.button_back) {
                finish();
            } else if (id == R.id.flash_btn) {
                if (!isFlash) {
                    CameraManager.get().turnLightOn();
                } else {

                    CameraManager.get().turnLightOff();
                }
                isFlash = !isFlash;
            } else if (id == R.id.photo_btn) {
                // 打开手机中的相册
                Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
                innerIntent.setType("image/*");
                Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
                startActivityForResult(wrapperIntent, REQUEST_CODE);
            } else if (id == R.id.qrcode_btn) {
                // 跳转到生成二维码页面
//                Bitmap b = createQRCode();
//                Intent intent = getIntent();
//                intent.putExtra("QR_CODE", b);
//                setResult(200, intent);
//                finish();
            }

        }
    };

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        resetStatusView();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            Log.e("CaptureActivity", "onResume");
        }

        Intent intent = getIntent();
        String action = intent == null ? null : intent.getAction();
        String dataString = intent == null ? null : intent.getDataString();
        if (intent != null && action != null) {
            if (action.equals(Intents.Scan.ACTION)) {
                // Scan the formats the intent requested, and return the result
                // to the calling activity.
                source = Source.NATIVE_APP_INTENT;
                decodeFormats = DecodeFormatManager.parseDecodeFormats(intent);
            } else if (dataString != null && dataString.contains(PRODUCT_SEARCH_URL_PREFIX)
                && dataString.contains(PRODUCT_SEARCH_URL_SUFFIX)) {
                // Scan only products and send the result to mobile Product
                // Search.
                source = Source.PRODUCT_SEARCH_LINK;
                sourceUrl = dataString;
                decodeFormats = DecodeFormatManager.PRODUCT_FORMATS;
            } else if (dataString != null && dataString.startsWith(ZXING_URL)) {
                // Scan formats requested in query string (all formats if none
                // specified).
                // If a return URL is specified, send the results there.
                // Otherwise, handle it ourselves.
                source = Source.ZXING_LINK;
                sourceUrl = dataString;
                Uri inputUri = Uri.parse(sourceUrl);
                returnUrlTemplate = inputUri.getQueryParameter(RETURN_URL_PARAM);
                decodeFormats = DecodeFormatManager.parseDecodeFormats(inputUri);
            } else {
                // Scan all formats and handle the results ourselves (launched
                // from Home).
                source = Source.NONE;
                decodeFormats = null;
            }
            characterSet = intent.getStringExtra(Intents.Scan.CHARACTER_SET);
        } else {
            source = Source.NONE;
            decodeFormats = null;
            characterSet = null;
        }
        beepManager.updatePrefs();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();

        //解绑
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (source == Source.NATIVE_APP_INTENT) {
                setResult(RESULT_CANCELED);
                finish();
                return true;
            } else if ((source == Source.NONE || source == Source.ZXING_LINK) && lastResult != null) {
                resetStatusView();
                if (handler != null) {
                    handler.sendEmptyMessage(R.id.restart_preview);
                }
                return true;
            }
        } else if (keyCode == KeyEvent.KEYCODE_FOCUS || keyCode == KeyEvent.KEYCODE_CAMERA) {
            // Handle these events so they don't launch the Camera app
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param barcode   A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(Result rawResult, Bitmap barcode) {
        inactivityTimer.onActivity();
        lastResult = rawResult;
        if (barcode == null) {
            // This is from history -- no saved barcode
            handleDecodeInternally(rawResult, null);
        } else {
            beepManager.playBeepSoundAndVibrate();
            drawResultPoints(barcode, rawResult);
            switch (source) {
                case NATIVE_APP_INTENT:
                case PRODUCT_SEARCH_LINK:
                    handleDecodeExternally(rawResult, barcode);
                    break;
                case ZXING_LINK:
                    if (returnUrlTemplate == null) {
                        handleDecodeInternally(rawResult, barcode);
                    } else {
                        handleDecodeExternally(rawResult, barcode);
                    }
                    break;
                case NONE:
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    if (prefs.getBoolean(PreferencesActivity.KEY_BULK_MODE, false)) {
                        Toast.makeText(this, R.string.msg_bulk_mode_scanned, Toast.LENGTH_SHORT).show();
                        // Wait a moment or else it will scan the same barcode
                        // continuously about 3 times
                        if (handler != null) {
                            handler.sendEmptyMessageDelayed(R.id.restart_preview, BULK_MODE_SCAN_DELAY_MS);
                        }
                        resetStatusView();
                    } else {
                        handleDecodeInternally(rawResult, barcode);
                    }
                    break;
            }
        }
    }

    /**
     * Superimpose a line for 1D or dots for 2D to highlight the key features of
     * the barcode.
     *
     * @param barcode   A bitmap of the captured image.
     * @param rawResult The decoded results which contains the points to draw.
     */
    private void drawResultPoints(Bitmap barcode, Result rawResult) {
        ResultPoint[] points = rawResult.getResultPoints();
        if (points != null && points.length > 0) {
            Canvas canvas = new Canvas(barcode);
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.result_image_border));
            paint.setStrokeWidth(3.0f);
            paint.setStyle(Paint.Style.STROKE);
            Rect border = new Rect(2, 2, barcode.getWidth() - 2, barcode.getHeight() - 2);
            canvas.drawRect(border, paint);

            paint.setColor(getResources().getColor(R.color.result_points));
            if (points.length == 2) {
                paint.setStrokeWidth(4.0f);
                drawLine(canvas, paint, points[0], points[1]);
            } else if (points.length == 4 && (rawResult.getBarcodeFormat().equals(BarcodeFormat.UPC_A))
                || (rawResult.getBarcodeFormat().equals(BarcodeFormat.EAN_13))) {
                // Hacky special case -- draw two lines, for the barcode and
                // metadata
                drawLine(canvas, paint, points[0], points[1]);
                drawLine(canvas, paint, points[2], points[3]);
            } else {
                paint.setStrokeWidth(10.0f);
                for (ResultPoint point : points) {
                    canvas.drawPoint(point.getX(), point.getY(), paint);
                }
            }
        }
    }

    private static void drawLine(Canvas canvas, Paint paint, ResultPoint a, ResultPoint b) {
        canvas.drawLine(a.getX(), a.getY(), b.getX(), b.getY(), paint);
    }

    // Put up our own UI for how to handle the decoded contents.
    @SuppressWarnings("unchecked")
    private void handleDecodeInternally(Result rawResult, Bitmap barcode) {
        viewfinderView.setVisibility(View.GONE);

        Map<ResultMetadataType, Object> metadata = (Map<ResultMetadataType, Object>) rawResult.getResultMetadata();
        if (metadata != null) {
            StringBuilder metadataText = new StringBuilder(20);
            for (Map.Entry<ResultMetadataType, Object> entry : metadata.entrySet()) {
                if (DISPLAYABLE_METADATA_TYPES.contains(entry.getKey())) {
                    metadataText.append(entry.getValue()).append('\n');
                }
            }
            if (metadataText.length() > 0) {
                metadataText.setLength(metadataText.length() - 1);
            }
        }
    }

    // Briefly show the contents of the barcode, then handle the result outside
    // Barcode Scanner.
    private void handleDecodeExternally(Result rawResult, Bitmap barcode) {
        viewfinderView.drawResultBitmap(barcode);

        // Since this message will only be shown for a second, just tell the
        // user what kind of
        // barcode was found (e.g. contact info) rather than the full contents,
        // which they won't
        // have time to read.

        if (source == Source.NATIVE_APP_INTENT) {
            // Hand back whatever action they requested - this can be changed to
            // Intents.Scan.ACTION when
            // the deprecated intent is retired.
            Intent intent = new Intent(getIntent().getAction());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.putExtra(Intents.Scan.RESULT, rawResult.toString());
            intent.putExtra(Intents.Scan.RESULT_FORMAT, rawResult.getBarcodeFormat().toString());
            Message message = Message.obtain(handler, R.id.return_scan_result);
            message.obj = intent;
            handler.sendMessageDelayed(message, INTENT_RESULT_DURATION);
        } else if (source == Source.PRODUCT_SEARCH_LINK) {
            // Reformulate the URL which triggered us into a query, so that the
            // request goes to the same
            // TLD as the scan URL.
            Message message = Message.obtain(handler, R.id.launch_product_query);
            // message.obj = sourceUrl.substring(0, end) + "?q=" +
            // resultHandler.getDisplayContents().toString()
            // + "&source=zxing";
            handler.sendMessageDelayed(message, INTENT_RESULT_DURATION);
        } else if (source == Source.ZXING_LINK) {
            // Replace each occurrence of RETURN_CODE_PLACEHOLDER in the
            // returnUrlTemplate
            // with the scanned code. This allows both queries and REST-style
            // URLs to work.
            Message message = Message.obtain(handler, R.id.launch_product_query);
            // message.obj = returnUrlTemplate.replace(RETURN_CODE_PLACEHOLDER,
            // resultHandler.getDisplayContents()
            // .toString());
            handler.sendMessageDelayed(message, INTENT_RESULT_DURATION);
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            /**
             * use a CameraManager to manager the camera's life cycles
             */
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
            return;
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.e(TAG, "Unexpected error initializating camera", e);
            displayFrameworkBugMessageAndExit();
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
        lastResult = null;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private Uri uri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        XDLog.e(TAG,"onActivityResult   requestCode = "+requestCode );
        if (resultCode == RESULT_OK) {

            uri = data.getData();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    Result result = scanningImage(uri);
                    if (result == null) {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "图片格式有误", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        // 数据返回，在这里去处理扫码结果
                        String recode = (result.toString());
                        XDLog.e(TAG,"识别结果：",recode);
                        Intent data = new Intent();
                        data.putExtra(ZxingCode.SCAN_RESULT, recode);
                        setResult(RESPONSE_CODE_QR, data);
                        finish();
                    }
                }
            }).start();
        }
    }

    protected Result scanningImage(Uri path) {

        if (path == null || path.equals("")) {
            return null;
        }
        // DecodeHintType 和EncodeHintType
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
        try {
            Bitmap scanBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

            RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
            BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
            QRCodeReader reader = new QRCodeReader();
            return reader.decode(bitmap1, hints);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap createQRCode() {
        int QR_WIDTH = 100;
        int QR_HEIGHT = 100;

        try {
            // 需要引入core包
            QRCodeWriter writer = new QRCodeWriter();
            String text = Util.getIMEI(this);
            if (text == null || "".equals(text) || text.length() < 1) {
                return null;
            }
            // 把输入的文本转为二维码
            BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);

            System.out.println("w:" + martix.getWidth() + "h:" + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            // cheng chen de er wei ma
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  使用zxing生成二维码 -- 工外部类生成二维码
     * @param width   宽
     * @param height  高
     * @param source  字符串资源
     *                返回一个图片
     */
    public static Bitmap createQRBitmap(int width, int height, String source) {
        try {
            if (TextUtils.isEmpty(source)) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(source, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // sheng chen de er wei ma
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

/**
 *       example：
     private void testCapture(){
     Intent intent = new Intent(this,CaptureActivity.class);
     startActivityForResult(intent,ZxingCode.REQUEST_CODE);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
     switch (requestCode){
     case ZxingCode.REQUEST_CODE:
     if (resultCode == Activity.RESULT_OK){
     String resUrl = data.getStringExtra(ZxingCode.SCAN_RESULT);
     MKLog.e("扫码结果：",resUrl);

     }
     break;

     }
     }
 */

}
