package com.example.banquemisrchallenge05.di

import com.example.banquemisrchallenge05.model.apis.MovieApi
import com.example.banquemisrchallenge05.model.remote.IRemoteDataSource
import com.example.banquemisrchallenge05.model.remote.RemoteDataSourceImpl
import com.example.banquemisrchallenge05.model.repository.IRepository
import com.example.banquemisrchallenge05.model.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *      now we are trying to work with dipendancy injection ( digger hilt )
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val BaseUrl = "https://api.themoviedb.org/3/movie/"


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return return retrofit.create(MovieApi::class.java)
        // this return instance of MovieApi
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(api: MovieApi): IRemoteDataSource {
        return RemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: IRemoteDataSource): IRepository {
        return RepositoryImpl(remoteDataSource)
    }
}