package com.chahinem.showhive.di

import android.app.Application
import com.chahinem.api.RxSchedulers
import com.chahinem.showhive.qualifiers.PerApp
import com.chahinem.showhive.qualifiers.Trakt
import com.chahinem.trakt.api.TraktApi
import com.chahinem.trakt.api.TraktApiClient
import com.chahinem.trakt.api.TraktAuthenticator
import com.chahinem.trakt.api.TraktInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module class TraktApiModule {

  @Provides
  @PerApp
  fun provideTraktInterceptor(app: Application) = TraktInterceptor(app)

  @Provides
  @PerApp
  fun provideTraktAuthenticator(
      app: Application,
      okHttpClient: OkHttpClient,
      moshi: Moshi
  ): TraktAuthenticator {
    return TraktAuthenticator(app, okHttpClient, moshi)
  }

  @Provides
  @PerApp
  @Trakt
  fun provideTraktHttpUrl() = HttpUrl.parse("https://api.trakt.tv/")!!

  @Provides
  @PerApp
  @Trakt
  fun provideOkHttpClient(
      client: OkHttpClient,
      interceptor: TraktInterceptor,
      authenticator: TraktAuthenticator,
      chuck: ChuckInterceptor): OkHttpClient {
    val clientBuilder = client.newBuilder()

    if (BuildConfig.DEBUG) {
      val httpLoggingInterceptor = HttpLoggingInterceptor()
      httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
      clientBuilder.addInterceptor(httpLoggingInterceptor)
      clientBuilder.addInterceptor(chuck)
    }
    clientBuilder.authenticator(authenticator)
    clientBuilder.addNetworkInterceptor(interceptor)
    return clientBuilder.build()
  }

  @Provides
  @PerApp
  @Trakt
  fun provideTraktRetrofit(
      moshi: Moshi,
      @Trakt baseUrl: HttpUrl,
      @Trakt client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }

  @Provides
  @PerApp
  fun provideTraktApiClient(@Trakt retrofit: Retrofit, scheduler: RxSchedulers): TraktApi {
    return TraktApiClient(
        retrofit.create(TraktApi::class.java),
        scheduler
    )
  }
}
