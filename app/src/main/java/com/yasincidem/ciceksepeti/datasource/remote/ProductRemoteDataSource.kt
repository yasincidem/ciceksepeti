package com.yasincidem.ciceksepeti.datasource.remote

import com.yasincidem.ciceksepeti.core.base.repository.IRemoteDataSource
import com.yasincidem.ciceksepeti.core.ext.processResponse
import com.yasincidem.ciceksepeti.core.util.ResultOf
import com.yasincidem.ciceksepeti.datasource.model.ProductData
import com.yasincidem.ciceksepeti.network.ServiceManager
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val serviceManager: ServiceManager
) : IRemoteDataSource {

    suspend fun fetch(endPoint: String): ResultOf<ProductData> {
        return processResponse { serviceManager.productService.getProductList(endPoint) }
    }

}