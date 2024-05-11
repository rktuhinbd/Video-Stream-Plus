package com.rktuhinbd.assessmenttask.home.di

import android.content.Context
import com.rktuhinbd.assessmenttask.home.di.qualifier.RetrofitForVideo
import com.rktuhinbd.assessmenttask.home.repo.ApiService
import com.rktuhinbd.assessmenttask.home.repo.MyRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiApplicationComponent {
    @Singleton
    @Provides
    fun providesMyRepo(@ApplicationContext context: Context, service: ApiService): MyRepo {
        return MyRepo(context, service)
    }

    @Singleton
    @Provides
    fun providesApiService(@RetrofitForVideo retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}