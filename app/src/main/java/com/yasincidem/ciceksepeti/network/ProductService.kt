package com.yasincidem.ciceksepeti.network

import com.yasincidem.ciceksepeti.datasource.model.ProductData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ProductService {
    //    detailList=2007140&detailList=2009846
    @GET
    suspend fun getProductList(@Url endPoint: String): Response<ProductData>
}