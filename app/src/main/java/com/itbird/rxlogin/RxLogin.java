package com.itbird.rxlogin;

import android.app.Activity;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * Created by itbird on 2023/1/7
 */
public class RxLogin implements RxLoginResultCallback {
    static WeakReference<Activity> activityWeakReference;
    PublishSubject<RxLoginResult> subject;
    public static RxLogin mInstance;

    @Override
    public void callback(RxLoginResult rxLoginResult) {
        subject.onNext(rxLoginResult);
        subject.onComplete();
    }

    public static RxLogin create(Activity activity) {
        if (mInstance == null) {
            synchronized (RxLogin.class) {
                if (mInstance == null) {
                    mInstance = new RxLogin(activity);
                }
            }
        }
        return mInstance;
    }

    public static RxLogin getInstance() {
        return mInstance;
    }

    private RxLogin(Activity activity) {
        subject = PublishSubject.create();
        activityWeakReference = new WeakReference<>(activity);
    }

    public Observable<RxLoginResult> loginPlatform(PlatformShare qq) {
        Intent intent = new Intent(activityWeakReference.get(), RxLoginActivity.class);
        intent.putExtra("platfrom", PlatformShare.QQ);
        activityWeakReference.get().overridePendingTransition(0, 0);
        activityWeakReference.get().startActivity(intent);
        List<Observable<RxLoginResult>> list = new ArrayList(1);
        list.add(subject);
        return Observable.concat(Observable.fromIterable(list));
    }
}
