# XNohttp
网络请求框架(NoHttp的二次封装)

> 添加依赖

`root build.gradle `
```
allprojects {
    repositories {
        ...
        maven {
            url 'https://jitpack.io'
        }
    }
}
```
`module build.gradle `
```
implementation 'com.github.fonuhuolian:XNohttp:1.1.9.3'
```

> 混淆
```
-dontwarn org.fonuhuolian.xnohttp.**
-keep class org.fonuhuolian.xnohttp.**{*;}
```

> 用法

在Application#onCreate()中初始化`XToastUtils`

```
XToastUtils.init(this); // XToastUtils初始化

```
在Application#onCreate()中初始化`NoHttp`

```
NoHttp.initialize(this); // NoHttp默认初始化。

```

高级初始化

```
InitializationConfig initializeConfig = 
    InitializationConfig.newBuilder(this)
        .addHeader() // 添加请求头信息
        .readTimeout(20 * 1000) // 全局服务器响应超时时间，单位毫秒。
        .build()
NoHttp.initialize(initializeConfig);
```
初始化gif动画加载
```
XNohttpServer.setImageLoader(new XNohttpServer.ImageLoader() {
    @Override
    public void onLoadGifImage(Context context, ImageView imageView, int gifResId) {
        Glide.with(context).load(gifResId).into(imageView);
    }
});
```

`StringRequest`

```
new XNoHttpStringRequester.Builder("请求地址")
    .addHeaderParams()   // 添加请求头信息 
    .addRequestParams()  // 添加请求参数
    .addBinaryParams()   // 添加file文件
    .addBitmapParams()   // 添加bitmap
    .addCancleSign(sign) // 添加取消标记
    .addResponseListener()  // 添加监听事件
    .build(); // 开始请求
```

`DownLoadRequest`

```
new XNoHttpDownLoadRequester.Builder()
    .addDownLoadUrl(url)    // 请求地址
    .addFileName("xxx.apk") // 下载文件时保存的名称
    .addDownLoadListener()  // 下载监听
    .build();               // 开始下载
```
