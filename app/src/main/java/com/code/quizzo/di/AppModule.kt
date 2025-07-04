package com.code.quizzo.di

import com.code.quizzo.constants.Constants
import com.code.quizzo.network.QuizApiService
import com.code.quizzo.repository.QuizRepository
import com.code.quizzo.repository.QuizRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        impl: QuizRepositoryImpl
    ): QuizRepository

    companion object {

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): QuizApiService =
            retrofit.create(QuizApiService::class.java)
    }
}
