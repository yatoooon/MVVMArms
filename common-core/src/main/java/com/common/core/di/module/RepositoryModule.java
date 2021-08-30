package com.common.core.di.module;

import com.common.core.base.BaseRepository;
import com.common.core.base.ibase.IRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@InstallIn(SingletonComponent.class)
@Module
public abstract class RepositoryModule {

    @Binds
    abstract IRepository bindDataRepository(BaseRepository dataBaseRepository);
}
