package com.workplaces.aslapov.data.di

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.squareup.moshi.Moshi
import com.workplaces.aslapov.data.auth.network.AuthApi
import com.workplaces.aslapov.data.feed.network.FeedApi
import com.workplaces.aslapov.data.interceptors.ErrorInterceptor
import com.workplaces.aslapov.data.interceptors.TokenInterceptor
import com.workplaces.aslapov.data.interceptors.UserAuthenticator
import com.workplaces.aslapov.data.moshiadapters.LocalDateAdapter
import com.workplaces.aslapov.data.moshiadapters.UriAdapter
import com.workplaces.aslapov.data.profile.network.ProfileApi
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://interns2021.redmadrobot.com/"
        private const val HOST_NAME = "interns2021.redmadrobot.com"
    }

    @Singleton
    @Provides
    fun provideAuthApi(@UnauthorizedZone retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideProfileApi(@AuthorizedZone retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)

    @Singleton
    @Provides
    fun provideFeedApi(@AuthorizedZone retrofit: Retrofit): FeedApi = retrofit.create(FeedApi::class.java)

    @Singleton
    @Provides
    @AuthorizedZone
    fun provideRetrofit(@AuthorizedZone client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    @UnauthorizedZone
    fun provideAuthRetrofit(@UnauthorizedZone client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(LocalDateAdapter())
            .add(UriAdapter())
            .build()
    }

    @Singleton
    @Provides
    @AuthorizedZone
    fun provideOkHttpClient(
        userAuthenticator: UserAuthenticator,
        tokenInterceptor: TokenInterceptor,
        errorInterceptor: ErrorInterceptor,
        certificatePinner: CertificatePinner,
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .authenticator(userAuthenticator)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(errorInterceptor)
            .addInterceptor(LoggingInterceptor.Builder().setLevel(Level.BODY).build())
            .certificatePinner(certificatePinner)
            .build()
    }

    @Singleton
    @Provides
    @UnauthorizedZone
    fun provideAuthOkHttpClient(
        errorInterceptor: ErrorInterceptor,
        certificatePinner: CertificatePinner,
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(errorInterceptor)
            .addInterceptor(LoggingInterceptor.Builder().setLevel(Level.BODY).build())
            .certificatePinner(certificatePinner)
            .build()
    }

    @Singleton
    @Provides
    fun provideCertificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
            .add(HOST_NAME, "sha256/PhNkc2DlW1XmAx2zBHgYaIeSanzL6bazbMHtdFeAhlE=")
            .build()
    }
}
