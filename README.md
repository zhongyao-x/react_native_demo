## RN（持续更新中）

<img src="http://img1.vued.vanthink.cn/vued656b397cfd8facd7e9fdcb477c4e98a1.png" alt="GitHub" title="GitHub,Social Coding" width="360" height="560" />



1.创建android项目

2.项目根初始化npm

```
npm init
yarn add react
yarn add react-native
```

3.配置 package.json
```
script {
    "start": "node node_modules/react-native/local-cli/cli.js start",
    "bundle-android": "react-native bundle --platform android --dev false --entry-file index.android.js --bundle-output app/src/main/assets/index.android.bundle --assets-dest app/src/main/res/"
}
```

`--platform：平台`

`--dev：开发模式`

`--entry-file：条目文件`

`--bundle-output：bundle文件生成的目录`

`--assets-dest：资源文件生成的目录`


> 配置bundle-android时注意你的项目结构

app/src/main

android/app/src/main

4.配置 app => gradle
```
compileSdkVersion 23 (设置为23 因rn依赖包中使用了23的api)
targetSdkVersion 23
```

`-- update 2018-7-5 --`

今天发现升到0.56了

```
"react": "^16.4.1",
"react-native": "^0.56.0"
```

这个版本貌似不能用23(API) 我换成了27

```
compileSdkVersion 27
targetSdkVersion 27
```

5.在 app => build.gradle 添加依赖包
```
dependencies {
    implementation 'com.facebook.react:react-native:+'
    ...
}
```
    
6.添加ndk支持
```
android {
    defaultConfig {
        ...
        ndk {
            abiFilters "armeabi-v7a", "x86"
        }
    }
}
```
7.在 project => build.gradle 中添加如下(关联node_modules)
```
allprojects {
    repositories{
        ...
        maven {
            url "$rootDir/node_modules/react-native/android"
            // url "$rootDir/../node_modules/react-native/android"
            // 根据目录结构选择
        }
    }
}
```

## RN 和 Android 之间页面跳转

### Native to RN

1.创建MyApplication (不细说请看代码)
```
<application 
    android:name=".MyApplication">
    ....
</application>
```
2.创建RNHelloWorldActivity 继承 ReactActivity
重写 getMainComponentName 方法，返回你在index.android.js中注册的组件名

`-- 我这里是HelloWorld --`

    AppRegistry.registerComponent('HelloWorld', () => HelloWorld);
    
3.在MainActivity中跳转到这个页面试一试吧

以下问题解决办法

1）Unable to load script from assets xxxxxxx
    
    原因是没有正确加载到bundle文件(rn核心文件)
        
    a.新建 app/src/main/assets 文件夹
         
    b.执行npm run bundle-android(上一步我们在script中添加的命令)
        
### RN to Native   

...

## 调试

1.在根目录执行 npm start 启动服务

2.在mainifests中注册

    <activity android:name="com.facebook.react.devsupport.DevSettingsActivity" />
    
3.启动应用 在RN页面 打开调试菜单 Android模拟器对应的则是Command⌘ + M（windows上可能是F1或者F2）。

4.更改你的index.android.js 文件 试一下reload(这时会访问你的8081端口来加载最新的bunle文件)

以下问题解决办法

1) Clould not connect devlopment server
```
a.检查是否添加 android.permission.INTERNET 网络权限
b.浏览器执行 http://localhost:8081/index.android.bundle?platform=android 是否能正常加载bundel

如果你是真机

a.(在rn页面中)摇一摇触发dev菜单 选择dev settings 选中菜单 Debug server host & port fordevice
(需在统一局域网内)输入你服务所在ip地址 如 192.168.1.123:8081
b.或者 连接usb 输入 adb reverse tcp:8081 tcp:8081 (代理设备8081端口)  
```

## RN 和 Android 通信

### rn 调用原生方法

1.新建HellWorldModule类 继承 ReactContextBaseJavaModule
```java'
public class HelloWorldModule extends ReactContextBaseJavaModule {
    private HelloWorldModule mHelloWorldModule;
    
    @Override
    public String getName() {
        return MODULE_NAME;
    }
    
    @ReactMethod
    public void showToast(String msg) {
        Toast.makeText(mContext, "andorid:" + msg, Toast.LENGTH_SHORT).show();
    }
}
```
被RN调用的方法一定要加上注解 @ReactMethod

MODULE_NAME 一会儿会在RN中使用

2.新建CommPackage类 实现 ReactPackage
```java
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
```
将 HelloWorldModule 添加到集合中返回

3.在MyApplication中注册 CommPackage

```java
private static final CommPackage mCommPackage = new CommPackage(); // 通讯类

private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }

   @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                mCommPackage  // 嘿! 看这儿
        );
    }
};
```

4.在js中调用 引入 NativeModules (部分代码，具体请看index.android.js)
```
import {
    ...
    NativeModules
} from 'react-native'

class HelloWorld {
    ...
    callNatShowToast() {
        NativeModules.mHelloWorldModule.showToast('红鲤鱼与绿鲤鱼与驴');
     }
    ...
}
```

### rn 调用原生方法 (callback 回调)
请看项目
### rn 调用原生方法 (返回 promise)
请看项目

