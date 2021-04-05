package com.yasincidem.ciceksepeti.repository.product.contract

import com.yasincidem.ciceksepeti.core.util.ResultOf
import com.yasincidem.ciceksepeti.datasource.model.ProductData

interface ProductRepositoryContract {
    suspend fun getProducts(endPoint: String) : ResultOf<ProductData>
}