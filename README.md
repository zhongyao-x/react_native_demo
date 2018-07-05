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
   配置bundle-android时注意你的项目结构
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
    
8.到这里基本上就ok了接下来说下RN与Native之间的交互，正常运行请继续往下看
```

### RN 和 Android 之间页面跳转

```
一.Native to RN
    1.创建MyApplication 并在mainifests中 Application节点上添加name属性 (不细说请看代码)
       <application 
            android:name=".MyApplication">
            ....
       </application>
    2.创建RNHelloWorldActivity 继承 ReactActivity 重写 
        getMainComponentName 方法，返回你在index.android.js中注册的组件名
        -- 我这里是HelloWorld --
        AppRegistry.registerComponent('HelloWorld', () => HelloWorld);
    3.在MainActivity中直接Intent跳转到这个页面试一试吧
    
    以下问题解决办法
        1）Unable to load script from assets xxxxxxx
            原因是没有正确加载到bundle文件(rn核心文件)
            a.新建 app/src/main/assets 文件夹 
            b.执行npm run bundle-android(上一步我们在script中添加的命令)
二.RN to Native   
```

### 调试
```
1.在根目录执行 npm start
2.在mainifests中注册 <activity android:name="com.facebook.react.devsupport.DevSettingsActivity" />
2.启动应用 在RN页面 打开调试菜单 Android模拟器对应的则是Command⌘ + M（windows上可能是F1或者F2）。
3.更改你的index.android.js 文件 试一下reload(这时会访问你的8081端口来加载最新的bunle文件)

以下问题解决办法
    1) Clould not connect devlopment server
        a.检查是否添加 android.permission.INTERNET 网络权限
        b.浏览器执行 http://localhost:8081/index.android.bundle?platform=android 是否能正常加载bundel
        如果你是真机
        a.(在rn页面中)摇一摇触发dev菜单 选择dev settings 选中菜单 Debug server host & port fordevice
            (需在统一局域网内)输入你服务所在ip地址 如 192.168.1.123:8081
        b.或者 连接usb 输入 adb reverse tcp:8081 tcp:8081 (代理设备8081端口)      
```

### RN 和 Android 通信
> Android系统为我们提供了webview来加载网页，为了让webview加载的网页可以与App交互，系统提供了一套机制帮助我们更方便的实现通信。同样为了实现React Native与原生App之间的通信，FB也实现了自己的一套交互机制。

```
wait
```