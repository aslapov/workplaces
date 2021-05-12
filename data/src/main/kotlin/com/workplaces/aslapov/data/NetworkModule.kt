package com.workplaces.aslapov.data

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.squareup.moshi.Moshi
import com.workplaces.aslapov.data.auth.AuthApi
import com.workplaces.aslapov.data.auth.UserAuthenticator
import com.workplaces.aslapov.data.feed.FeedApi
import com.workplaces.aslapov.data.profile.ProfileApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://interns2021.redmadrobot.com/"

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)

    @Singleton
    @Provides
    fun provideFeedApi(retrofit: Retrofit): FeedApi = retrofit.create(FeedApi::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
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
            .add(UuidAdapter())
            .add(LocalDateAdapter())
            .add(UriAdapter())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        userAuthenticator: UserAuthenticator,
        tokenInterceptor: TokenInterceptor,
        errorInterceptor: ErrorInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .authenticator(userAuthenticator)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(errorInterceptor)
            .addInterceptor(LoggingInterceptor.Builder().setLevel(Level.BODY).build())
            .build()
    }
}