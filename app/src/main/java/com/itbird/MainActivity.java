package com.itbird;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.itbird.rxjava.R;
import com.itbird.rxjava.AndroidSchedulers;
import com.itbird.rxjava.Function;
import com.itbird.rxjava.Observable;
import com.itbird.rxjava.Observer;
import com.itbird.rxjava.Schedulers;
import com.itbird.rxpermissions.PermissionsUtils;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.rxjava3.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getSimpleName();
    ImageView imageView;
    private String url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimagepphcloud.thepaper.cn%2Fpph%2Fimage%2F178%2F242%2F971.jpg&refer=http%3A%2F%2Fimagepphcloud.thepaper.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673685426&t=5b3eeea3db1afbd39c5abf7ffd877621";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageview);

        /**
         * 普通的下载图片，显示到imageview的方法
         */
//        normalDownloadImageToView();
        /**
         * Rxjava下载图片，显示到imageview的方法
         */
        rxjavaDownloadImageToView();
        /**
         * 查看rxjava观察者模式的代码
         */
        test();
        /**
         * 测试获取权限
         */
        testPermissions();

        testRxPermissions();
    }

    /**
     * RxPermission使用代码
     */
    private void testRxPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Throwable {
                        if (!aBoolean) {
                            Log.e("Permission", "授权失败！");
                            Toast.makeText(MainActivity.this, "授权失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void testPermissions() {
        if (!PermissionsUtils.checkPermission(this, Manifest.permission.CAMERA)) {
            PermissionsUtils.requestPermissions(this, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsUtils.REQUEST_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "授权失败！");
                    Toast.makeText(this, "授权失败！", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    /**
     * Rxjava下载图片，显示到imageview的方法
     */
    private void rxjavaDownloadImageToView() {
//        Observable.just(url)
//                .map(new Function<String, Bitmap>() {
//                    @Override
//                    public Bitmap apply(String s) throws Exception {
//                        return downloadImage(s);
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Bitmap>() {
//                    @Override
//                    public void accept(Bitmap bitmap) throws Exception {
//                        imageView.setImageBitmap(bitmap);
//                    }
//                });
    }

    private void test() {
        Observable.just("https://img-blog.csdn.net/20160903083319668")
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        Bitmap bitmap = downloadImage(s);
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe() {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Bitmap s) {
                        Log.d(TAG, "onNext s = " + s);
                        imageView.setImageBitmap(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    /**
     * 普通的下载图片，显示到imageview的方法
     */
    private void normalDownloadImageToView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(url);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    /**
     * 下载图片功能函数
     *
     * @param url
     * @return
     */
    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlCon.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }
}