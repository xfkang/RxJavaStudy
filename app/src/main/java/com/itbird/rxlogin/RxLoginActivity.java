package com.itbird.rxlogin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by itbird on 2023/1/7
 */
public class RxLoginActivity extends Activity {
    PlatformShare platformShare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        platformShare = getIntent().getParcelableExtra("platfrom");
        UMShareAPI.get(this).getPlatformInfo(this, getPlatFormId(platformShare), authListener);
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(RxLoginActivity.this, "成功了", Toast.LENGTH_LONG).show();

            RxLoginResult rxLoginResult = new RxLoginResult();
            rxLoginResult.setPlatForm(platformShare);
            rxLoginResult.setResult(true);
            rxLoginResult.setInfo(data);
            RxLogin.getInstance().callback(rxLoginResult);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(RxLoginActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();

            RxLoginResult rxLoginResult = new RxLoginResult();
            rxLoginResult.setPlatForm(platformShare);
            rxLoginResult.setResult(false);
            rxLoginResult.setInfo(new HashMap<>());
            RxLogin.getInstance().callback(rxLoginResult);
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(RxLoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    private SHARE_MEDIA getPlatFormId(PlatformShare platformShare) {
        switch (platformShare) {
            case QQ:
                return SHARE_MEDIA.QQ;
            case WEIXIN:
                return SHARE_MEDIA.WEIXIN;
        }
        return SHARE_MEDIA.QQ;
    }
}
