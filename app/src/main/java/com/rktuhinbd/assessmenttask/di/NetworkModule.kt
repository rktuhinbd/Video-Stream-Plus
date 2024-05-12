package com.rktuhinbd.assessmenttask.di

import com.rktuhinbd.assessmenttask.BuildConfig
import com.rktuhinbd.assessmenttask.di.qualifier.RetrofitForVideo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @RetrofitForVideo
    fun providesVideoClient(builder: Retrofit.Builder): Retrofit {
        return builder
            .baseUrl(BuildConfig.baseurl)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
    }
}