package com.arms.core.di.module;


import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.RoomDatabase;

import com.arms.core.config.CoreConfigModule;
import com.arms.core.config.ManifestParser;
import com.arms.core.config.inter.AppliesOptions;
import com.arms.common.http.GlobalHttpHandler;
import com.arms.common.http.imageloader.BaseImageLoaderStrategy;
import com.arms.common.http.log.DefaultFormatPrinter;
import com.arms.common.http.log.FormatPrinter;
import com.arms.common.http.log.RequestInterceptor;
import com.arms.common.utils.DataHelper;
import com.arms.common.utils.Preconditions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.internal.Util;


/**
 *
 */
@InstallIn(SingletonComponent.class)
@Module
public class AppModule {

    @Singleton
    @Provides
    List<CoreConfigModule> provideListCoreConfigModule(@ApplicationContext Context context){
        return new ManifestParser(context).parse();
    }


    @Singleton
    @Provides
    AppModule.Builder provideConfigModuleBuilder(@ApplicationContext Context context,List<CoreConfigModule> modules) {
        AppModule.Builder builder = new AppModule.Builder();
        //遍历配置
        for (CoreConfigModule configModule : modules) {
            //如果启用则申请配置参数
            if (configModule.isManifestParsingEnabled()) {
                configModule.applyOptions(context, builder);
            }
        }
        return builder;
    }

    /**
     * 提供 BaseUrl,默认使用 <"https://api.github.com/">
     *
     * @return
     */
    @Singleton
    @Provides
    HttpUrl provideBaseUrl(@NonNull Builder builder) {
        return builder.baseUrl == null ? HttpUrl.parse("https://api.github.com/") : builder.baseUrl;
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

    /**
     * 提供缓存文件
     */
    @Singleton
    @Provides
    File provideCacheFile(@ApplicationContext Context context, @NonNull Builder builder) {
        return builder.cacheFile == null ? DataHelper.getCacheFile(context) : builder.cacheFile;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.RetrofitConfiguration provideRetrofitConfiguration(@NonNull Builder builder) {
        return builder.retrofitConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    AppliesOptions.OkhttpConfiguration provideOkhttpConfiguration(@NonNull Builder builder) {
        return builder.okhttpConfiguration;
    }


    @Singleton
    @Provides
    @Nullable
    AppliesOptions.GsonConfiguration provideGsonConfiguration(@NonNull Builder builder) {
        return builder.gsonConfiguration;
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

    @Singleton
    @Provides
    @Nullable
    List<Interceptor> provideInterceptors(@NonNull Builder builder) {
        return builder.interceptors;
    }

    @Singleton
    @Provides
    AppliesOptions.RoomConfiguration provideRoomConfiguration(@NonNull Builder builder) {
        return builder.roomConfiguration;
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
        private File cacheFile;
        private List<Interceptor> interceptors;
        private AppliesOptions.RetrofitConfiguration retrofitConfiguration;
        private AppliesOptions.OkhttpConfiguration okhttpConfiguration;
        private AppliesOptions.GsonConfiguration gsonConfiguration;
        private AppliesOptions.RoomConfiguration roomConfiguration;
        private RequestInterceptor.Level printHttpLogLevel;
        private FormatPrinter formatPrinter;
        private ExecutorService executorService;

        private Builder() {
        }

        public Builder baseurl(String baseUrl) {//基础url
            if (TextUtils.isEmpty(baseUrl)) {
                throw new NullPointerException("BaseUrl can not be empty");
            }
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseurl(HttpUrl baseUrl) {
            this.baseUrl = Preconditions.checkNotNull(baseUrl, HttpUrl.class.getCanonicalName() + "can not be null.");
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

        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
            if (interceptors == null) {
                interceptors = new ArrayList<>();
            }
            this.interceptors.add(interceptor);
            return this;
        }


        public Builder retrofitConfiguration(AppliesOptions.RetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okhttpConfiguration(AppliesOptions.OkhttpConfiguration okhttpConfiguration) {
            this.okhttpConfiguration = okhttpConfiguration;
            return this;
        }

        public Builder gsonConfiguration(AppliesOptions.GsonConfiguration gsonConfiguration) {
            this.gsonConfiguration = gsonConfiguration;
            return this;
        }

        public Builder roomConfiguration(AppliesOptions.RoomConfiguration<? extends RoomDatabase> roomConfiguration) {
            this.roomConfiguration = roomConfiguration;
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
