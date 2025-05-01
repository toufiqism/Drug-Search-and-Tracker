package com.example.drugsearchandtracker.di

import com.example.drugsearchandtracker.data.remote.api.RxNavApi
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

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rxnav.nlm.nih.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRxNavApi(retrofit: Retrofit): RxNavApi {
        return retrofit.create(RxNavApi::class.java)
    }
} 