### react-native 安卓相关 总结
---

### RN 集成到现有应用
```
1.创建android项目

2.项目根初始化npm
    npm init
    yarn add react
    yarn add react-native

3.配置package.json
   script {
    "start": "node node_modules/react-native/local-cli/cli.js start",
    "bundle-android": "react-native bundle --platform android --dev false --entry-file index.android.js --bundle-output app/src/main/assets/index.android.bundle --assets-dest app/src/main/res/"
   }
   注意你的项目结构
    app/src/main
    android/app/src/main

4.配置 app => gradle
    compileSdkVersion 23 (设置为23 因rn依赖包中使用了23的api)
    targetSdkVersion 23

    -- update -- 2018-7-5
    今天搭项目时发现升到0.56了
    "react": "^16.4.1",
    "react-native": "^0.56.0"
    然后用原来的23出现版本的问题于是我就直接上27(8.1)了
    compileSdkVersion 27
    targetSdkVersion 27

5.在 app => build.gradle 添加依赖包
    dependencies ｛
        implementation 'com.facebook.react:react-native:+'
        ...
    ｝
6.添加ndk支持
    android ｛
        defaultConfig ｛
            ...
            ndk {
                abiFilters "armeabi-v7a", "x86"
            }
        ｝
    ｝
7.在project => build.gradle 中添加如下(关联node_modules)
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
8.

```

### RN 和 Android 通信
> Android系统为我们提供了webview来加载网页，为了让webview加载的网页可以与App交互，系统提供了一套机制帮助我们更方便的实现通信。同样为了实现React Native与原生App之间的通信，FB也实现了自己的一套交互机制。

```
（1）RCTDeviceEventEmitter 事件方式
    优点：可任意时刻传递，Native主导控制。
（2）Callback 回调方式
    优点：JS调用，Native返回。
    缺点：CallBack为异步操作，返回时机不确定
（3）Promise
    优点：JS调用，Native返回。
    缺点：每次使用需要JS调用一次
（4）直传常量数据（原生向RN）
    跨域传值，只能从原生端向RN端传递。RN端可通过 NativeModules.[module名].[参数名] 的方式获取
```