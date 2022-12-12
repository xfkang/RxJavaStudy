package com.itbird.myapplication.rxpermission;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import androidx.annotation.VisibleForTesting;
import androidx.core.app.ActivityCompat;

import com.itbird.myapplication.rxpermission.Consumerr;
import com.itbird.myapplication.rxpermission.Pobservable;
import com.itbird.myapplication.rxpermission.RxFragment;

import java.lang.reflect.Proxy;

/**
 * Created by itbird on 2022/9/15
 */
public class RxPermission implements Pobservable {
    String[] permission;
    static final String FRAGMENT_TAG = "com.bumptech.glide.manager";
    Consumerr consumerr;
    RxFragment fragment;

    public RxPermission(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        fragment = (RxFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new RxFragment();
        }
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.add(fragment, FRAGMENT_TAG).commitAllowingStateLoss();
    }

    public Pobservable requset(String... permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public void subscribe(Consumerr consumerr) {
        this.consumerr = consumerr;
        fragment.requset(permission, consumerr);
    }
}
