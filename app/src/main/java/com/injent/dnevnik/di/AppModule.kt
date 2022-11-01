package com.injent.dnevnik.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.injent.dnevnik.data.BASE_URL
import com.injent.dnevnik.data.Dnevnik
import com.injent.dnevnik.data.RepositoryImpl
import com.injent.dnevnik.domain.repository.Repository
import com.injent.dnevnik.domain.use_cases.GetUser
import com.injent.dnevnik.domain.use_cases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(
        api: Dnevnik
    ) : Repository {
        return RepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun buildApi() : Dnevnik {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Dnevnik::class.java)
    }

    @Provides
    fun provideUseCases(
        repository: Repository
    ) = UseCases(
        getUser = GetUser(repository)
    )
}