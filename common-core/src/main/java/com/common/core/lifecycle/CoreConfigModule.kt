package com.common.core.lifecycle

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.common.core.base.delegate.BaseApplicationLifecycle
import com.common.core.config.CoreConfigModule
import com.common.core.config.inter.AppliesOptions
import com.common.core.di.module.AppModule
import com.common.res.http.SSLSocketClient
import com.common.res.config.Constants.BASE_URL
import com.common.res.config.GlobalHttpHandlerImpl
import com.common.res.glide.GlideImageLoaderStrategy
import com.google.gson.GsonBuilder
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * 自定义全局配置
 *
 *
 */
class CoreConfigModule : CoreConfigModule() {

    override fun applyOptions(context: Context, builder: AppModule.Builder) {
        builder.baseurl(BASE_URL)
            .imageLoaderStrategy(GlideImageLoaderStrategy()) //想支持多 BaseUrl, 以及运行时动态切换任意一个 BaseUrl, 请使用 https://github.com/JessYanCoding/RetrofitUrlManager
            //如果 BaseUrl 在 App 启动时不能确定, 需要请求服务器接口动态获取, 请使用以下代码
            //以下方式是 Arms 框架自带的切换 BaseUrl 的方式, 在整个 App 生命周期内只能切换一次, 若需要无限次的切换 BaseUrl, 以及各种复杂的应用场景还是需要使用 RetrofitUrlManager 框架
            //以下代码只是配置, 还要使用 Okhttp (AppComponent 中提供) 请求服务器获取到正确的 BaseUrl 后赋值给 GlobalConfiguration.sDomain
            //切记整个过程必须在第一次调用 Retrofit 接口之前完成, 如果已经调用过 Retrofit 接口, 此种方式将不能切换 BaseUrl
            //                .baseurl(new BaseUrl() {
            //                    @Override
            //                    public HttpUrl url() {
            //                        return HttpUrl.parse(sDomain);
            //                    }
            //                })
            //可根据当前项目的情况以及环境为框架某些部件提供自定义的缓存策略, 具有强大的扩展性
            //                .cacheFactory(new Cache.Factory() {
            //                    @NonNull
            //                    @Override
            //                    public Cache build(CacheType type) {
            //                        switch (type.getCacheTypeId()){
            //                            case CacheType.EXTRAS_TYPE_ID:
            //                                return new IntelligentCache(500);
            //                            case CacheType.CACHE_SERVICE_CACHE_TYPE_ID:
            //                                return new Cache(type.calculateCacheSize(context));//自定义 Cache
            //                            default:
            //                                return new LruCache(200);
            //                        }
            //                    }
            //                })
            //若觉得框架默认的打印格式并不能满足自己的需求, 可自行扩展自己理想的打印格式 (以下只是简单实现)
            //                .formatPrinter(new FormatPrinter() {
            //                    @Override
            //                    public void printJsonRequest(Request request, String bodyString) {
            //                        Timber.i("printJsonRequest:" + bodyString);
            //                    }
            //
            //                    @Override
            //                    public void printFileRequest(Request request) {
            //                        Timber.i("printFileRequest:" + request.url().toString());
            //                    }
            //
            //                    @Override
            //                    public void printJsonResponse(long chainMs, boolean isSuccessful, int code,
            //                                                  String headers, MediaType contentType, String bodyString,
            //                                                  List<String> segments, String message, String responseUrl) {
            //                        Timber.i("printJsonResponse:" + bodyString);
            //                    }
            //
            //                    @Override
            //                    public void printFileResponse(long chainMs, boolean isSuccessful, int code, String headers,
            //                                                  List<String> segments, String message, String responseUrl) {
            //                        Timber.i("printFileResponse:" + responseUrl);
            //                    }
            //                })
            //可以自定义一个单例的线程池供全局使用
            //                .executorService(Executors.newCachedThreadPool())
            //这里提供一个全局处理 Http 请求和响应结果的处理类, 可以比客户端提前一步拿到服务器返回的结果, 可以做一些操作, 比如 Token 超时后, 重新获取 Token
            .globalHttpHandler(GlobalHttpHandlerImpl(context)) //用来处理 RxJava 中发生的所有错误, RxJava 中发生的每个错误都会回调此接口
            //RxJava 必须要使用 ErrorHandleSubscriber (默认实现 Subscriber 的 onError 方法), 此监听才生效
            //.responseErrorListener(new ResponseErrorListenerImpl())
            .gsonConfiguration { context1: Context?, gsonBuilder: GsonBuilder ->  //这里可以自己自定义配置 Gson 的参数
                gsonBuilder
                    .serializeNulls() //支持序列化值为 null 的参数
                    .enableComplexMapKeySerialization() //支持将序列化 key 为 Object 的 Map, 默认只能序列化 key 为 String 的 Map
            }
            .retrofitConfiguration { context1: Context?, retrofitBuilder: Retrofit.Builder? -> }
            .okhttpConfiguration { context1: Context?, okhttpBuilder: OkHttpClient.Builder ->  //这里可以自己自定义配置 Okhttp 的参数
                okhttpBuilder.sslSocketFactory(
                    SSLSocketClient.getSSLSocketFactory(),
                    SSLSocketClient.getTrustManager()
                ) //支持 Https, 详情请百度
                okhttpBuilder.readTimeout(30, TimeUnit.SECONDS)
                okhttpBuilder.writeTimeout(30, TimeUnit.SECONDS)
                okhttpBuilder.connectTimeout(30, TimeUnit.SECONDS)
                //使用一行代码监听 Retrofit／Okhttp 上传下载进度监听,以及 Glide 加载进度监听, 详细使用方法请查看 https://github.com/JessYanCoding/ProgressManager
                ProgressManager.getInstance().with(okhttpBuilder)
                //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl, 详细使用方法请查看 https://github.com/JessYanCoding/RetrofitUrlManager
                RetrofitUrlManager.getInstance().with(okhttpBuilder)
            }.roomConfiguration(AppliesOptions.RoomConfiguration { context, builder -> })


    }

    override fun injectAppLifecycle(
        context: Context,
        lifecycles: MutableList<BaseApplicationLifecycle>
    ) {
        lifecycles.add(CoreLifecyclesImpl())
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>
    ) {
        lifecycles.add(CoreActivityLifecycleCallbacksImpl())
    }

    override fun injectFragmentLifecycle(
        context: Context,
        lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>
    ) {

    }
}