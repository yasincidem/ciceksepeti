package com.yasincidem.ciceksepeti.di

import com.yasincidem.ciceksepeti.network.ProductService
import com.yasincidem.ciceksepeti.network.ServiceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideServiceManager(
        productService: ProductService
    ): ServiceManager {
        return ServiceManager(
            productService
        )
    }

}