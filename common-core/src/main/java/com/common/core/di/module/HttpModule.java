package com.common.core.di.module;


import com.common.core.config.inter.AppliesOptions;
import com.common.core.http.GlobalHttpHandler;
import com.common.core.http.InterceptorConfig;
import com.common.core.http.log.RequestInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.king.retrofit.retrofithelper.RetrofitHelper;

import javax.inject.Singleton;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */
@InstallIn(SingletonComponent.class)
@Module
public abstract class HttpModule {


    @Singleton
    @Provides
    static Retrofit provideRetrofit(HttpUrl httpUrl, OkHttpClient client, Gson gson, InterceptorConfig config, Retrofit.Builder builder, @Nullable AppliesOptions.RetrofitOptions options) {
        builder.baseUrl(httpUrl)
                .client(client);
        if (config.isAddGsonConverterFactory()) {
            builder.addConverterFactory(GsonConverterFactory.create(gson));
        }
        if (options != null) {
            options.applyOptions(builder);
        }
        return builder.build();
    }

    private static final int TIME_OUT = 10;

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(InterceptorConfig config, Interceptor intercept, OkHttpClient.Builder builder, @Nullable GlobalHttpHandler handler, @Nullable AppliesOptions.OkHttpClientOptions options, @Nullable List<Interceptor> interceptors, ExecutorService executorService) {
        builder
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS);
        if (config.isAddLog()) {
            builder.addInterceptor(intercept);
        }
        if (handler != null) {
            builder.addInterceptor(chain -> chain.proceed(handler.onHttpRequestBefore(chain, chain.request())));
        }
        //如果外部提供了 Interceptor 的集合则遍历添加
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        //为 OkHttp 设置默认的线程池
        builder.dispatcher(new Dispatcher(executorService));
        if (options != null) {
            options.applyOptions(builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    static Gson provideGson(GsonBuilder builder, @Nullable AppliesOptions.GsonOptions options) {
        if (options != null) {
            options.applyOptions(builder);
        }
        return builder.create();
    }

    @Singleton
    @Provides
    static Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    static OkHttpClient.Builder provideClientBuilder() {
        return RetrofitHelper.getInstance().createClientBuilder();
    }

    @Singleton
    @Provides
    static GsonBuilder provideGsonBuilder() {
        return new GsonBuilder();
    }

    @Singleton
    @Provides
    static InterceptorConfig provideInterceptorConfig(InterceptorConfig.Builder builder, @Nullable AppliesOptions.InterceptorConfigOptions options) {
        if (options != null) {
            options.applyOptions(builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    static InterceptorConfig.Builder provideInterceptorConfigBuilder() {
        return InterceptorConfig.newBuilder();
    }

    @Binds
    abstract Interceptor bindInterceptor(RequestInterceptor interceptor);


}
