package com.vanthink.mobile_manager_platform_rn.comm.module;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class HelloWorldModule extends ReactContextBaseJavaModule {

    private ReactApplicationContext mContext;

    public static final String MODULE_NAME = "mHelloWorldModule";

    public HelloWorldModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    /**
     * RN调用Native的方法
     *
     * @param msg
     */
    @ReactMethod
    public void showToast(String msg) {
        Toast.makeText(mContext, "andorid:" + msg, Toast.LENGTH_SHORT).show();
    }

    @ReactMethod
    public void sumCallback(int a, int b, Callback callback) {
        callback.invoke(a + "+" + b + "=" + (a + b));
    }

    @ReactMethod
    public void sumPromise(int a, int b, Promise promise) {
        promise.resolve(a + "+" + b + "=" + (a + b));
        // promise.reject();
    }

    /**
     * Native调用RN
     *
     * @param msg
     */
    public void nativeCallRn(String msg) {
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("addCount", msg);
    }
}
