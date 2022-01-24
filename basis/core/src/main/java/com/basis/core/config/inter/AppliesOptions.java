package com.basis.core.config.inter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;

import com.basis.core.di.module.AppModule;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * 为框架提供一些配置参数入口
 *
 * @see <a href="https://github.com/bumptech/glide/blob/f7d860412f061e059aa84a42f2563a01ac8c303b/library/src/main/java/com/bumptech/glide/module/AppliesOptions.java">Glide</a>
 */
public interface AppliesOptions {

    /**
     * 使用 {@link AppModule.Builder} 给框架配置一些配置参数
     *
     * @param context {@link Context}
     * @param builder {@link AppModule.Builder}
     */
    void applyOptions(@NonNull Context context, @NonNull AppModule.Builder builder);


    /**
     * {@link Retrofit} 自定义配置接口
     */
     interface RetrofitConfiguration {
        void configRetrofit(@NonNull Context context, @NonNull Retrofit.Builder builder);
    }

    /**
     * {@link OkHttpClient} 自定义配置接口
     */
     interface OkhttpConfiguration {
        void configOkhttp(@NonNull Context context, @NonNull OkHttpClient.Builder builder);
    }

     interface GsonConfiguration {
        void configGson(@NonNull Context context, @NonNull GsonBuilder builder);
    }


     interface RoomConfiguration<T extends RoomDatabase> {
        void configRoom(@NonNull Context context, @NonNull RoomDatabase.Builder<T> builder);
    }

}
