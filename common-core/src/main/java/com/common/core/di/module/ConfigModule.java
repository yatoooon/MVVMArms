package com.common.core.di.module;


import android.app.Application;
import android.content.Context;

import com.common.core.config.inter.AppliesOptions;
import com.common.core.config.CoreConfigModule;
import com.common.core.config.ManifestParser;
import com.common.core.http.GlobalHttpHandler;
import com.common.core.http.imageloader.BaseImageLoaderStrategy;
import com.common.core.http.log.DefaultFormatPrinter;
import com.common.core.http.log.FormatPrinter;
import com.common.core.http.log.RequestInterceptor;
import com.common.core.util.DataHelper;
import com.common.core.util.Preconditions;
import com.king.retrofit.retrofithelper.RetrofitHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.RoomDatabase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.internal.Util;


/**
 *
 */
@InstallIn(SingletonComponent.class)
@Module
public class ConfigModule {

    @Singleton
    @Provides
    Builder provideConfigModuleBuilder(@ApplicationContext Context context) {
        ConfigModule.Builder builder = new ConfigModule.Builder();
        //解析配置
        List<CoreConfigModule> modules = new ManifestParser(context).parse();
        //遍历配置
        for (CoreConfigModule configModule : modules) {
            //如果启用则申请配置参数
            if (configModule.isManifestParsingEnabled()) {
                configModule.applyOptions(context, builder);
            }
        }
        return builder;
    }

    @Singleton
    @Provides
    HttpUrl provideBaseUrl(@NonNull Builder builder) {
        HttpUrl baseUrl = builder.baseUrl;
        if (baseUrl == null) {//如果 mBaseUrl 为空表示没有在自定义配置 CoreConfigModule 中配过 BaseUrl
            //尝试去 RetrofitHelper 中取一次 BaseUrl，这里相当于多支持一种配置 BaseUrl 的方式
            baseUrl = RetrofitHelper.getInstance().getBaseUrl();
        }
        //再次检测 mBaseUrl 是否为空，如果依旧为空，表示两种配置方式都没有配置过，则直接抛出异常
        Preconditions.checkNotNull(baseUrl, "Base URL required.");
        return baseUrl;
    }


    /**
     * 提供图片加载框架
     *
     * @return
     */
    @Singleton
    @Provides
    @Nullable
    BaseImageLoaderStrategy provideImageLoaderStrategy(@NonNull Builder builder) {
        return builder.loaderStrategy;
    }

    /**
     * 提供处理 Http 请求和响应结果的处理类
     *
     * @return
     */
    @Singleton
    @Provides
    @Nullable
    GlobalHttpHandler provideGlobalHttpHandler(@NonNull Builder builder) {
        return builder.handler;
    }


    @Singleton
    @Provides
    @Nullable
    List<Interceptor> provideInterceptors(@NonNull Builder builder) {
        return builder.interceptors;
    }

    /**
     * 提供缓存文件
     */
    @Singleton
    @Provides
    File provideCacheFile(Application application, @NonNull Builder builder) {
        return builder.cacheFile == null ? DataHelper.getCacheFile(application) : builder.cacheFile;
    }


    @Singleton
    @Provides
    @Nullable
    AppliesOptions.RetrofitOptions provideRetrofitOptions(@NonNull Builder builder) {
        return builder.retrofitOptions;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.OkHttpClientOptions provideOkHttpClientOptions(@NonNull Builder builder) {
        return builder.okHttpClientOptions;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.GsonOptions provideGsonOptions(@NonNull Builder builder) {
        return builder.gsonOptions;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.InterceptorConfigOptions provideInterceptorConfigOptions(@NonNull Builder builder) {
        return builder.interceptorConfigOptions;
    }

    @Singleton
    @Provides
    AppliesOptions.RoomDatabaseOptions provideRoomDatabaseOptions(@NonNull Builder builder) {
        if (builder.roomDatabaseOptions != null) {
            return builder.roomDatabaseOptions;
        }
        return it -> {
        };
    }

    @Singleton
    @Provides
    RequestInterceptor.Level providePrintHttpLogLevel(@NonNull Builder builder) {
        return builder.printHttpLogLevel == null ? RequestInterceptor.Level.ALL : builder.printHttpLogLevel;
    }

    @Singleton
    @Provides
    FormatPrinter provideFormatPrinter(@NonNull Builder builder) {
        return builder.formatPrinter == null ? new DefaultFormatPrinter() : builder.formatPrinter;
    }

    /**
     * 返回一个全局公用的线程池,适用于大多数异步需求。
     * 避免多个线程池创建带来的资源消耗。
     *
     * @return {@link Executor}
     */
    @Singleton
    @Provides
    ExecutorService provideExecutorService(@NonNull Builder builder) {
        return builder.executorService == null ? new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new SynchronousQueue<>(), Util.threadFactory("Arms Executor", false)) : builder.executorService;
    }

    public static final class Builder {

        private HttpUrl baseUrl;

        private BaseImageLoaderStrategy loaderStrategy;

        private GlobalHttpHandler handler;

        private List<Interceptor> interceptors;

        private File cacheFile;

        private AppliesOptions.RetrofitOptions retrofitOptions;

        private AppliesOptions.OkHttpClientOptions okHttpClientOptions;

        private AppliesOptions.GsonOptions gsonOptions;

        private AppliesOptions.InterceptorConfigOptions interceptorConfigOptions;

        private AppliesOptions.RoomDatabaseOptions roomDatabaseOptions;

        private RequestInterceptor.Level printHttpLogLevel;

        private FormatPrinter formatPrinter;

        private ExecutorService executorService;


        public Builder() {

        }

        public Builder baseUrl(@NonNull String baseUrl) {
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(@NonNull HttpUrl baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder imageLoaderStrategy(BaseImageLoaderStrategy loaderStrategy) {//用来请求网络图片
            this.loaderStrategy = loaderStrategy;
            return this;
        }

        public Builder globalHttpHandler(GlobalHttpHandler handler) {//用来处理http响应结果
            this.handler = handler;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
            if (interceptors == null) {
                interceptors = new ArrayList<>();
            }
            this.interceptors.add(interceptor);
            return this;
        }


        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }


        public Builder retrofitOptions(AppliesOptions.RetrofitOptions retrofitOptions) {
            this.retrofitOptions = retrofitOptions;
            return this;
        }

        public Builder okHttpClientOptions(AppliesOptions.OkHttpClientOptions okHttpClientOptions) {
            this.okHttpClientOptions = okHttpClientOptions;
            return this;
        }

        public Builder gsonOptions(AppliesOptions.GsonOptions gsonOptions) {
            this.gsonOptions = gsonOptions;
            return this;
        }

        public Builder interceptorConfigOptions(AppliesOptions.InterceptorConfigOptions interceptorConfigOptions) {
            this.interceptorConfigOptions = interceptorConfigOptions;
            return this;
        }

        public Builder roomDatabaseOptions(AppliesOptions.RoomDatabaseOptions<? extends RoomDatabase> roomDatabaseOptions) {
            this.roomDatabaseOptions = roomDatabaseOptions;
            return this;
        }

        public Builder printHttpLogLevel(RequestInterceptor.Level printHttpLogLevel) {//是否让框架打印 Http 的请求和响应信息
            this.printHttpLogLevel = Preconditions.checkNotNull(printHttpLogLevel, "The printHttpLogLevel can not be null, use RequestInterceptor.Level.NONE instead.");
            return this;
        }

        public Builder formatPrinter(FormatPrinter formatPrinter) {
            this.formatPrinter = Preconditions.checkNotNull(formatPrinter, FormatPrinter.class.getCanonicalName() + "can not be null.");
            return this;
        }

        public Builder executorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }


    }


}
