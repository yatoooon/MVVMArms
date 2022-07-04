package com.common.core.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.common.core.base.ibase.IModel;
import com.common.core.base.ibase.IRepository;
import com.common.core.config.Constants;

import javax.inject.Inject;

/**
 *
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 *
 * 如果您继承使用了BaseModel或其子类，你需要参照如下方式在构造函数上添加@Inject注解
 *
 * @example BaseModel
 * //-------------------------
 *    public class YourModel extends BaseModel {
 *        @Inject
 *        public BaseModel(IDataRepository dataRepository){
 *            super(dataRepository);
 *        }
 *    }
 * //-------------------------
 *
 *
 * 标准MVVM模式中的M (Model)层基类
 *
 */
public class BaseModel implements IModel {

    private IRepository mDataRepository;

    @Inject
    public BaseModel(IRepository dataRepository){
        this.mDataRepository = dataRepository;
    }

    @Override
    public void onDestroy() {
        mDataRepository = null;
    }

    /**
     * 传入Class 获得{@link retrofit2.Retrofit#create(Class)} 对应的Class
     * @param service
     * @param <T>
     * @return {@link retrofit2.Retrofit#create(Class)}
     */
    public <T> T getRetrofitService(Class<T> service){
        return mDataRepository.getRetrofitService(service);
    }


    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     * @param database
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    public <T extends RoomDatabase> T getRoomDatabase(@NonNull Class<T> database){
        return getRoomDatabase(database, Constants.DEFAULT_DATABASE_NAME);
    }

    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     * @param database
     * @param dbName
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    public <T extends RoomDatabase> T getRoomDatabase(@NonNull Class<T> database, @Nullable String dbName){
        return mDataRepository.getRoomDatabase(database,dbName);
    }
}
