package com.common.res.component;

import com.common.res.appvm.AppViewModel;
import com.common.res.http.imageloader.BaseImageLoaderStrategy;
import com.common.res.http.imageloader.ImageLoader;
import com.common.res.utils.ArmsUtil;
import com.google.gson.Gson;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;


/**
 * ================================================
 * 可通过 {@link ArmsUtil#obtainAppComponent()} 拿到此接口的实现类
 * 拥有此接口的实现类即可调用对应的方法拿到 Dagger 提供的对应实例
 * ================================================
 */
@Singleton
public class AppComponent {

    @Inject
    public AppComponent() {

    }

    /**
     * 图片加载管理器, 用于加载图片的管理类, 使用策略者模式, 可在运行时动态替换任何图片加载框架
     * arms-imageloader-glide 提供 Glide 的策略实现类, 也可以自行实现
     * 需要在  ConfigModule#applyOptions(Context, GlobalConfigModule.Builder)} 中
     * 手动注册 {@link BaseImageLoaderStrategy}, {@link ImageLoader} 才能正常使用
     *
     * @return
     */
    @Inject
    public ImageLoader imageLoader;

    /**
     * 网络请求框架
     *
     * @return {@link OkHttpClient}
     */
    @Inject
    public OkHttpClient okHttpClient;

    /**
     * Json 序列化库
     *
     * @return {@link Gson}
     */
    @Inject
    public Moshi moshi;

    /**
     * 缓存文件根目录 (RxCache 和 Glide 的缓存都已经作为子文件夹放在这个根目录下), 应该将所有缓存都统一放到这个根目录下
     * 便于管理和清理
     *
     * @return {@link File}
     */
    @Inject
    public File cacheFile;


    /**
     * 返回一个全局公用的线程池,适用于大多数异步需求。
     * 避免多个线程池创建带来的资源消耗。
     *
     * @return {@link ExecutorService}
     */
    @Inject
    public ExecutorService executorService;


    /**
     * 返回一个全局ViewModel
     *
     * @return {@link AppViewModel}
     */
    @Inject
    public AppViewModel appViewModel;

}
