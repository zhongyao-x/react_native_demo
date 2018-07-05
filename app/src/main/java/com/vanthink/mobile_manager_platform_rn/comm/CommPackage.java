package com.vanthink.mobile_manager_platform_rn.comm;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.vanthink.mobile_manager_platform_rn.comm.module.HelloWorldModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommPackage implements ReactPackage {

    private HelloWorldModule mHelloWorldModule;

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        mHelloWorldModule = new HelloWorldModule(reactContext);
        modules.add(mHelloWorldModule);
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
