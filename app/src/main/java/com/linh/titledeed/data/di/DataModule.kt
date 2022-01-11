package com.linh.titledeed.data.di

import android.app.Application
import com.google.gson.Gson
import com.linh.titledeed.BuildConfig
import com.linh.titledeed.data.contract.WalletService
import com.linh.titledeed.data.remote.AuthInterceptor
import com.linh.titledeed.data.remote.ContractCallErrorInterceptor
import com.linh.titledeed.data.remote.IpfsApiService
import com.linh.titledeed.data.remote.IpfsGatewayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.ContractGasProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Singleton
    @Provides
    fun provideContractCallErrorInterceptor(gson: Gson): ContractCallErrorInterceptor {
        return ContractCallErrorInterceptor(gson)
    }

    @Singleton
    @Provides
    fun provideOkhttp(authInterceptor: AuthInterceptor, loggingInterceptor: HttpLoggingInterceptor, contractCallErrorInterceptor: ContractCallErrorInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(contractCallErrorInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeb3j(okHttpClient: OkHttpClient) : Web3j {
        return Web3j.build(HttpService("http://192.168.1.112:8545", okHttpClient))
    }

    @Singleton
    @Provides
    fun provideTokenContractService(web3j: Web3j, application: Application): WalletService {
        return WalletService(web3j, application)
    }

    @Singleton
    @Provides
    fun provideCredentials(): Credentials {
        return Credentials.create("fc03b915f2dc9c1ab6136e2a7328a68405d04ca261365b558c82a27d6029a6a9")
    }

    @Singleton
    @Provides
    fun provideContractGasProvider(web3j: Web3j) : ContractGasProvider {
        return object :
            ContractGasProvider {
            override fun getGasPrice(contractFunc: String?): BigInteger {
                return web3j.ethGasPrice().send().gasPrice
            }

            override fun getGasPrice(): BigInteger {
                return web3j.ethGasPrice().send().gasPrice
            }

            override fun getGasLimit(contractFunc: String?): BigInteger {
                return web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                    .send().block.gasLimit
            }

            override fun getGasLimit(): BigInteger {
                return web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                    .send().block.gasLimit
            }
        }
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @IpfsGatewayRetrofit
    @Provides
    fun provideIpfsGatewayRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.IPFS_GATEWAY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideIpfsGatewayService(@IpfsGatewayRetrofit retrofit: Retrofit): IpfsGatewayService {
        return retrofit.create(IpfsGatewayService::class.java)
    }

    @Singleton
    @IpfsApiRetrofit
    @Provides
    fun provideIpfsApiRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.IPFS_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideIpfsApiService(@IpfsApiRetrofit retrofit: Retrofit): IpfsApiService {
        return retrofit.create(IpfsApiService::class.java)
    }
}