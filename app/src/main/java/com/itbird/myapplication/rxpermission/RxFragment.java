package com.itbird.myapplication.rxpermission;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.itbird.myapplication.R;

@SuppressLint("ValidFragment")
public class RxFragment extends Fragment {
    String[] permissions;
    Consumerr consumerr;

    @SuppressLint("ValidFragment")
    public RxFragment(String[] strings, Consumerr consumerr) {
        this.permissions = strings;
        this.consumerr = consumerr;
    }

    public RxFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            for (int result : grantResults) {
                consumerr.onResult(result == PackageManager.PERMISSION_GRANTED);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rx, container, false);
    }

    public void requset(String[] permissions, Consumerr consumerr) {
        this.consumerr = consumerr;
        requestPermissions(permissions, 100);
    }
}