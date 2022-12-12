package com.itbird.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.itbird.myapplication.rxjava.Function;
import com.itbird.myapplication.rxjava.Observable;
import com.itbird.myapplication.rxjava.Observerr;
import com.itbird.myapplication.rxjava.Schedulers;
import com.itbird.myapplication.rxpermission.Consumerr;
import com.itbird.myapplication.rxpermission.RxPermission;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getSimpleName();
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageview);

        /**
         * 请求权限
         */
        requestPermission();
        /**
         * 自定义实现逻辑
         */
        testDefineRxjava();
    }

    private void requestPermission() {
        RxPermission rxPermission = new RxPermission(this);
        rxPermission.requset(Manifest.permission.CAMERA)
                .subscribe(new Consumerr() {
                    @Override
                    public void onResult(boolean result) {
                        if (result) {
                            Toast.makeText(MainActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 100) {
//            for (int result : grantResults) {
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }

    private void testDefineRxjava() {
        Observable.just("https://img-blog.csdn.net/20160903083326871")
                //事件转换
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) {
                        URL url = null;
                        try {
                            url = new URL(s);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            return BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) {
                        return addTimeWatermark("testRxjavaSrC", bitmap);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.mainThread())
                .subscribe(new Observerr<Bitmap>() {
                    @Override
                    public void onNext(Bitmap finalBitmap) {
                        Log.d(TAG, "onNext");
                        imageView.setImageBitmap(finalBitmap);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }

                    @Override
                    public void onSubscribe() {
                        Log.d(TAG, "onSubscribe");
                    }
                });
    }

    private static Bitmap addTimeWatermark(String str, Bitmap mBitmap) {
        //获取原始图片与水印图片的宽与高
        int mBitmapWidth = mBitmap.getWidth();
        int mBitmapHeight = mBitmap.getHeight();
        Bitmap mNewBitmap = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mNewBitmap);
        //向位图中开始画入MBitmap原始图片
        mCanvas.drawBitmap(mBitmap, 0, 0, null);
        //添加文字
        Paint mPaint = new Paint();
        //String mFormat = TingUtils.getTime()+"\n"+" 纬度:"+GpsService.latitude+"  经度:"+GpsService.longitude;
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);
        //水印的位置坐标
        mCanvas.drawText(str, mBitmapWidth / 4, mBitmapHeight / 2, mPaint);
        mCanvas.save();
        mCanvas.restore();
        return mNewBitmap;
    }
}