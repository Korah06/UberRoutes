package baeza.guillermo.uberroutes.dependencyinjection

import baeza.guillermo.uberroutes.create.data.network.CreateClient
import baeza.guillermo.uberroutes.dependencyinjection.Constants.IP_ADDRESS
import baeza.guillermo.uberroutes.home.data.network.HomeClient
import baeza.guillermo.uberroutes.login.data.network.LoginClient
import baeza.guillermo.uberroutes.profile.data.network.ProfileClient
import baeza.guillermo.uberroutes.register.data.network.RegisterClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
            request.addHeader("Accept", "application/json")
            val actualRequest = request.build()
            it.proceed(actualRequest)
        }
    }

    @Singleton
    @Provides
    fun getHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(IP_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient).build()
    }

    @Singleton
    @Provides
    fun provideLoginClient(retrofit: Retrofit) : LoginClient {
        return retrofit.create(LoginClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRegisterClient(retrofit: Retrofit) : RegisterClient {
        return retrofit.create(RegisterClient::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeClient(retrofit: Retrofit) : HomeClient {
        return retrofit.create(HomeClient::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileClient(retrofit: Retrofit) : ProfileClient {
        return retrofit.create(ProfileClient::class.java)
    }

    @Singleton
    @Provides
    fun provideCreateClient(retrofit: Retrofit) : CreateClient {
        return retrofit.create(CreateClient::class.java)
    }
}