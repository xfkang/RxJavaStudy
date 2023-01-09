package com.itbird.rxlogin;

import java.util.Map;

/**
 * Created by itbird on 2023/1/7
 */
public class RxLoginResult {
    private PlatformShare platformShare;
    private boolean b;

    public PlatformShare getPlatformShare() {
        return platformShare;
    }

    public boolean isB() {
        return b;
    }

    public Map<String, String> getData() {
        return data;
    }

    private Map<String, String> data;

    public void setPlatForm(PlatformShare platformShare) {
        this.platformShare = platformShare;
    }

    public void setResult(boolean b) {
        this.b = b;
    }

    public void setInfo(Map<String, String> data) {
        this.data = data;
    }
}
