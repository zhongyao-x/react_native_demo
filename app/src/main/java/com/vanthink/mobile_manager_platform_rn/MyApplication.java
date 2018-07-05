package com.vanthink.mobile_manager_platform_rn;

import android.app.Application;
import android.os.Build;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.vanthink.mobile_manager_platform_rn.comm.CommPackage;

import java.util.Arrays;
import java.util.List;

public class MyApplication extends Application implements ReactApplication {

    private static MyApplication instance;
    private static final CommPackage mCommPackage = new CommPackage(); // 通讯类

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SoLoader.init(this, false);
    }

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    mCommPackage
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
