package com.common.core.di.module;


import android.app.Application;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.common.core.base.BaseRepository;
import com.common.core.config.inter.AppliesOptions;
import com.common.res.action.ibase.IRepository;
import com.common.res.http.GlobalHttpHandler;
import com.common.res.http.log.RequestInterceptor;
import com.hjq.toast.ToastUtils;
import com.king.retrofit.retrofithelper.RetrofitHelper;
import com.squareup.moshi.Moshi;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import xin.sparkle.moshi.NullSafeStandardJsonAdapters;

/**
 *
 */
@InstallIn(SingletonComponent.class)
@Module
public abstract class HttpModule {


    /**
     * 提供 {@link Retrofit}
     *
     * @param application   {@link Application}
     * @param configuration {@link AppliesOptions.RetrofitConfiguration}
     * @param builder       {@link Retrofit.Builder}
     * @param client        {@link OkHttpClient}
     * @param httpUrl       {@link HttpUrl}
     * @param moshi         {@link Moshi}
     * @return {@link Retrofit}
     */
    @Singleton
    @Provides
    static Retrofit provideRetrofit(Application application, @Nullable AppliesOptions.RetrofitConfiguration configuration, Retrofit.Builder builder, OkHttpClient client
            , HttpUrl httpUrl, Moshi moshi) {
        builder
                .baseUrl(httpUrl)//域名
                .client(client);//设置 OkHttp

        if (configuration != null) {
            configuration.configRetrofit(application, builder);
        }


        builder
                .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization());//使用 Moshi
        return builder.build();
    }

    private static final int TIME_OUT = 10;

    /**
     * 提供 {@link OkHttpClient}
     *
     * @param application     {@link Application}
     * @param configuration   {@link AppliesOptions.OkhttpConfiguration}
     * @param builder         {@link OkHttpClient.Builder}
     * @param intercept       {@link Interceptor}
     * @param interceptors    {@link List<Interceptor>}
     * @param handler         {@link GlobalHttpHandler}
     * @param executorService {@link ExecutorService}
     * @return {@link OkHttpClient}
     */
    @Singleton
    @Provides
    static OkHttpClient provideClient(Application application, @Nullable AppliesOptions.OkhttpConfiguration configuration, OkHttpClient.Builder builder, Interceptor intercept
            , @Nullable List<Interceptor> interceptors, @Nullable GlobalHttpHandler handler, ExecutorService executorService) {
        builder.addNetworkInterceptor(intercept);

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

        if (configuration != null) {
            configuration.configOkhttp(application, builder);
        }
        return builder.build();
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


    @Binds
    abstract Interceptor bindInterceptor(RequestInterceptor interceptor);


    @Singleton
    @Provides
    static Moshi provideMoshi(Application application, Moshi.Builder builder, @Nullable AppliesOptions.MoshiConfiguration configuration) {
        if (configuration != null) {
            configuration.configMoshi(application, builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    static Moshi.Builder provideMoshiBuilder() {
        return new Moshi.Builder();
    }


    @Binds
    abstract IRepository bindDataRepository(BaseRepository dataBaseRepository);


}
