package com.apmasquio.cadastrousuario.di

import com.apmasquio.cadastrousuario.data.api.LocationApi
import com.apmasquio.cadastrousuario.data.AppDatabase
import android.app.Application
import com.apmasquio.cadastrousuario.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideUserDao(application: Application): UserDao {
        return AppDatabase.dbInstance(application.applicationContext).userDao()
    }
    @Singleton
    @Provides
    fun provideLocationApi(): LocationApi {
        return LocationApi.create()
    }
}