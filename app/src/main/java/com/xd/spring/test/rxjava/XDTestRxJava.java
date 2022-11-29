package com.xd.spring.test.rxjava;


import com.xd.spring.test.XDApplication;
import com.xd.spring.test.rxjava.events.XDEvent1;
import com.xd.spring.test.rxjava.events.XDEvent2;
import com.xdroid.spring.frames.okhttp.XDHttpClient;
import com.xdroid.spring.frames.okhttp.exception.XDHttpErrType;
import com.xdroid.spring.frames.okhttp.listener.XDDataListener;
import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.javas.tool.XDFiles;
import com.xdroid.spring.util.javas.tool.download.XDDownloadBean;
import com.xdroid.spring.util.javas.tool.download.XDSingleCallBack;
import com.xdroid.spring.util.javas.tool.download.XDDownloadChannel;
import com.xdroid.spring.util.javas.tool.download.XDDownloads;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static com.xdroid.spring.frames.okhttp.listener.XDJsonHandle.createJsonHandler;
import static com.xdroid.spring.frames.okhttp.request.XDRequest.createPostJSONRequest;

/**
 * rxjava、rxAndroid测试
 */
public class XDTestRxJava {
    private static final String TAG = "XDTestRxJava";
    public static final String analysisTime = "2022-11-07";
    public static String SOURCEDATA_FILE_PATH = "http://218.4.57.5:8889/aispeech/firstAid/task/listExcel?_t=1665972227&status=-1&startTime="
            + analysisTime + "+00:00:00&endTime=" + analysisTime + "+23:59:59";

    public static void test() {
//        testInterval();
//        testRange();
//        testRepeat();

//        testTranslate();
//        testFilter();
//        testAssist();
//        testOkhttp();

//        testEventBus("0");

//        testURL();
//        testOKHttpWS();

        testDownloadByOkhttp();

    }

    public void testOkhttpErr() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();

            }
        })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(Throwable throwable) throws Throwable {
                        return null;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {


                    }
                })
        ;
    }

    public static void testOKHttpWS() {

        new Thread(() -> {
            String urlPath = String.format("ws://%s/aispeech/ws/car/%s", "58.210.212.107:8089", URLEncoder.encode("苏EKX257"));
            OkHttpClient okHttpClient = XDHttpClient.getOkHttpClient();
            Request request = new Request.Builder()
                    .url(urlPath)
                    .build();
            okHttpClient.newWebSocket(request, new XDWebSocketListener());
        }).start();
    }

    private static class XDWebSocketListener extends WebSocketListener {

        @Override
        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosed(webSocket, code, reason);
            XDLog.e(TAG, "webSocket onClosed");
        }

        @Override
        public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosing(webSocket, code, reason);
            XDLog.e(TAG, "webSocket onClosing");
        }

        @Override
        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
            XDLog.e(TAG, "webSocket onFailure");
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
            super.onMessage(webSocket, text);
            XDLog.e(TAG, "webSocket onMessage = ", text);
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
            super.onMessage(webSocket, bytes);
            XDLog.e(TAG, "webSocket onMessage = ", bytes.toString());
        }

        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            super.onOpen(webSocket, response);
            try {
                InputStream inputStream = response.body().byteStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) sb.append(line);
                XDLog.e(TAG, "webSocket onOpen ", sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用OKhttp进行断点续传
     */
    public static void testDownloadByOkhttp() {
        XDDownloads.getInstance().downloadSingleThread(
                new XDDownloadBean(webApkPath, apkPath, XDDownloadChannel.HttpURLConnection),
                new XDSingleCallBack() {

                    @Override
                    public void onFail(String info) {
                        XDLog.e(TAG, "onFail", info);

                    }

                    @Override
                    public void onDownloading(long pos, long contentLength) {

                    }

                    @Override
                    public void onSuccess(String info, long pos, long contentLength) {
                        XDLog.e(TAG, "onSuccess", info, pos, "/", contentLength);
                    }

                });
    }

    //   //storage/emulated/0/test/app.apk
    public static String apkPath = XDFiles.buildFile("test", "app.apk");
    //    public static String apkPath = XDFiles.buildFile(XDApplication.INSTANCE(),"test", "app.apk");
    public static String webApkPath = "http://218.4.57.5:8889/aispeech/firstAid/apk/download?type=car";
    //    public static long pos = 6230205;
    public static long pos = 47458602;
//  public static long pos = 154284998;
//  public static long pos = 4242606;
//  public static long pos = 47458602;
    //总长度：154284998

    public static void testDownload() {

        new Thread(() -> {
            int webFileLength = XDFiles.getWebFileLength(webApkPath);
            XDLog.e(TAG, "下载的文件总长度：", webFileLength);

            try {
//                String webApkPath =  "https://image.baidu.com/search/detail?ct=503316480&z=9&tn=baiduimagedetail&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&hd=undefined&latest=undefined&copyright=undefined&cs=2331925024,632055215&os=338128329,1239438900&simid=2331925024,632055215&pn=124&rn=1&di=7146857200093233153&ln=1694&fr=&fmq=1668741563004_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=0&height=0&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=5a&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%253A%252F%252Fup.iosdesk.com%252F22%252Faa%252Ff1%252Fe0%252Fc8f6be7d7589a393d4f6f39c4e392d49.jpg%26refer%3Dhttp%253A%252F%252Fup.iosdesk.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Dauto%3Fsec%3D1671333623%26t%3D5026182df2f4c031f163e69d73be000b&rpstart=0&rpnum=0&adpicid=0&nojc=undefined&dyTabStr=MCwzLDYsMSw0LDIsNSw3LDgsOQ%3D%3D";
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(webApkPath).openConnection();
                //设置下载位置(从服务器上取要下载文件的某一段) 下载范围
//                urlConnection.setRequestProperty("RANGE", "bytes=" + pos);//这样设置range，没有成功
                urlConnection.setRequestProperty("Range", "bytes=" + pos + "-" + webFileLength);
                InputStream appData = urlConnection.getInputStream();

                RandomAccessFile rw = new RandomAccessFile(apkPath, "rw");
                rw.seek(pos);

                byte[] temp = new byte[1024 * 5];
                int dataLen = 0;
                while ((dataLen = appData.read(temp)) > 0) {
                    pos += dataLen;
                    rw.write(temp, 0, dataLen);
                    XDLog.e(TAG, "dataLen = ", pos);
                }
                appData.close();
                urlConnection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }


    public static void testURL() {
        new Thread(() -> {
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(SOURCEDATA_FILE_PATH).openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    XDLog.e(line);
                }
                br.close();
                urlConnection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }

    public static void testEventBus(String info) {
        EventBus.getDefault().post(new XDEvent1(" 测试通知XDEvent1 - " + info));
        EventBus.getDefault().post(new XDEvent2(" 测试通知XDEvent2 - " + info));

    }

    public static void testOkhttp() {

        Observable<Integer> just = Observable.just(1, 2);


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                String url = "http://39.105.38.116:8080/miyun/appManager/appLogin.do";//?userCode=dmf&passWord=dmf
                XDHttpClient.post(createPostJSONRequest(url, new HashMap<String, Object>() {
                    {
                        put("userCode", "dmf");
                        put("passWord", 123);
                        put("passWord", 123);
                    }
                }), createJsonHandler(String.class, new XDDataListener<String>() {
                    @Override
                    public void onSuccess(String res) {
                        emitter.onNext(res);
                    }

                    @Override
                    public void onFailure(XDHttpErrType err) {
                        emitter.onError(new Throwable(err.name()));
                    }
                }));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        XDLog.e(TAG, "onSubscribe");

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        XDLog.e(TAG, "onNext", s);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        XDLog.e(TAG, "onError", e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        XDLog.e(TAG, "onComplete");

                    }
                });


    }

    public static void testAssist() {
        Observable.range(1, 100)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {

                    }
                });

//        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
//            for (int i = 0; i < 10; i++) {
//                emitter.onNext(i);
////                Thread.sleep(1000);
//                if (i == 3) {
////                    emitter.onError(new Throwable("手动抛出异常"));
//                }
//
//            }
//            XDLog.e(TAG, System.currentTimeMillis() / 1000, "--", Thread.currentThread().getName());
//            emitter.onComplete();
//        })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .retry(3)
//                .isEmpty()
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean res) throws Throwable {
//                        XDLog.e(TAG, "/", res);
//                    }
//                });


        Thread t1 = new Thread("t1");
        Thread t2 = new Thread("t2");
        Thread t3 = new Thread("t3");

        Observable.just(t1, t2, t3)
                .toMap(new Function<Thread, String>() {
                    @Override
                    public String apply(Thread thread) throws Throwable {
                        return thread.getName();
                    }
                })
                .subscribe(new Consumer<Map<String, Thread>>() {
                    @Override
                    public void accept(Map<String, Thread> stringThreadMap) throws Throwable {
                        for (Map.Entry<String, Thread> item : stringThreadMap.entrySet()) {

                            XDLog.e(TAG, "/", item.getKey(), " - ", item.getValue());
                        }
                    }
                });


    }

    /**
     * 过滤
     */
    public static void testFilter() {
//        Observable.fromIterable(Arrays.asList(1, 2, 3, 4,5,6,7,8,9))
//                .filter(new Predicate<Integer>() {
//                    @Override
//                    public boolean test(Integer item) throws Throwable {
//                        return item > 3;
//                    }
//                }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer data) throws Throwable {
//                XDLog.e(TAG,data);
//            }
//        });


    }

    /**
     * 转换操作符
     */
    public static void testTranslate() {
        Observable.just("1", "2", "3")
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Throwable {
                        return new ObservableSource<String>() {
                            @Override
                            public void subscribe(@NonNull Observer<? super String> observer) {
                                observer.onNext("【" + s + "】");
                            }
                        };
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Throwable {
                        XDLog.e(TAG, "flatMap  ", o);
                    }
                });

        Observable.just("1", "2", "3")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Throwable {
                        return "index_" + s;
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                XDLog.e(TAG, "map  ", s);
            }
        });


//        Observable.fromIterable(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"))
//                .concatMap(new Function<String, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(String s) throws Throwable {
//                        return Observable.just(s);
//                    }
//                })
//                .cast(String.class)
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Throwable {
//                        XDLog.e(TAG, s);
//                    }
//                });

//        Observable.fromIterable(Arrays.asList(1,2,3,4))
//                .flatMapIterable(new Function<Integer, Iterable<Integer>>() {
//                    @Override
//                    public Iterable<Integer> apply(Integer integer) throws Throwable {
//                        return new ArrayList<Integer>(integer);
//                    }
//                })
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Throwable {
//
//                    }
//                });

//        Observable.fromIterable(Arrays.asList(1,2,3,4))
//                .buffer(3)
//                .subscribe(new Consumer<List<Integer>>() {
//                    @Override
//                    public void accept(List<Integer> integers) throws Throwable {
//                        for (Integer data:integers) {
//                            XDLog.e(TAG,data);
//                        }
//                        XDLog.e(TAG," ****** ");
//                    }
//                });

//    Observable.fromIterable(Arrays.asList("1", "22", "111", "2", "11", "222"))
//                .groupBy(new Function<String, Integer>() {
//                    @Override
//                    public Integer apply(String s) throws Throwable {
//                        return s.length();
//                    }
//                }).subscribe(new Consumer<GroupedObservable<Integer, String>>() {
//                    @Override
//                    public void accept(GroupedObservable<Integer, String> data) throws Throwable {
//                        XDLog.e(TAG,"groupBy-concat  ",data.getKey() ,data);
//                    }
//                });

//       Observable.concat(groupedObservableObservable)
//               .subscribe(new Consumer<String>() {
//                   @Override
//                   public void accept(String s) throws Throwable {
//                       XDLog.e(TAG,"groupBy-concat  ",s);
//                   }
//               });
    }

    /**
     * 重复
     */
    public static void testRepeat() {
        Observable<Integer> observable = Observable.range(1, 5);
        observable.repeat(3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Throwable {
                XDLog.e(TAG, integer);
            }
        });
    }


    /**
     * 定时
     */
    public static void testInterval() {

        Disposable disposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(aLong -> XDLog.e(TAG, aLong));

    }

    /**
     * 代替for循环
     */
    public static void testRange() {
        Observable.range(1, 100)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        XDLog.e(TAG, integer);
                    }
                });
    }

    /**
     * 创建 Create、just
     */
    public void testCreate_just() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                XDLog.e(TAG, "onSubscribe");

            }

            @Override
            public void onNext(@NonNull String s) {
                XDLog.e(TAG, "onNext", s);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                XDLog.e(TAG, "onError");

            }

            @Override
            public void onComplete() {
                XDLog.e(TAG, "onComplete");

            }
        };

        Observable<String> observable = Observable.just("1", "2", "3");
        observable.subscribe(observer);


        Observable.just("one", "two", "three", "four", "five")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.computation())
                .subscribe(observer);

    }


}
