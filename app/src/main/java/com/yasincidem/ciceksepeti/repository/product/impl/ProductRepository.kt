package com.yasincidem.ciceksepeti.repository.product.impl

import com.yasincidem.ciceksepeti.core.base.repository.BaseRepositoryRemote
import com.yasincidem.ciceksepeti.core.util.ResultOf
import com.yasincidem.ciceksepeti.datasource.model.ProductData
import com.yasincidem.ciceksepeti.datasource.remote.ProductRemoteDataSource
import com.yasincidem.ciceksepeti.repository.product.contract.ProductRepositoryContract
import javax.inject.Inject

class ProductRepository @Inject constructor(
    remoteDataSource: ProductRemoteDataSource
) : BaseRepositoryRemote<ProductRemoteDataSource>(remoteDataSource), ProductRepositoryContract {

    override suspend fun getProducts(endPoint: String): ResultOf<ProductData> {
        return try {
            when(val response = remoteDataSource.fetch(endPoint)) {
                is ResultOf.Success -> {
                    ResultOf.Success(response.data)
                }
                is ResultOf.Failure -> {
                    ResultOf.failure(error = Throwable("Unknown Error"))
                }
            }
        } catch (err: Exception) {
            ResultOf.failure(error = err)
        }
    }

}