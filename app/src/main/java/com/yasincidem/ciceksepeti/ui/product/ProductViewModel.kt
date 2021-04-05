package com.yasincidem.ciceksepeti.ui.product

import androidx.core.util.Predicate
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yasincidem.ciceksepeti.core.Constants.PRODUCT_BASE_ENDPOINT
import com.yasincidem.ciceksepeti.core.FilterType
import com.yasincidem.ciceksepeti.core.util.ResultOf
import com.yasincidem.ciceksepeti.datasource.model.ProductData
import com.yasincidem.ciceksepeti.datasource.model.Values
import com.yasincidem.ciceksepeti.repository.product.impl.ProductRepository

class ProductViewModel @ViewModelInject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _products by lazy { getProductList() }
    val products = _products

    private val _filteredValues = MutableLiveData<MutableList<Values>>(mutableListOf())
    val filteredValues = _filteredValues

    private val _isLoading = MutableLiveData(true)

    val filteredProductList: LiveData<ResultOf<ProductData>> by lazy {
        Transformations.switchMap(filteredValues) {
            getProductList(filtersToEndPoint())
        }
    }

    fun updateFilteredValues(filterValue: Values) {
        if (_filteredValues.value?.filter { it.id == filterValue.id }?.size == 1)
            filter(_filteredValues.value!!) {
                it.id == filterValue.id
            }
        else
            _filteredValues.value?.add(filterValue)
        _filteredValues.notifyObserver()
    }

    private fun getProductList(endPoint: String = PRODUCT_BASE_ENDPOINT): LiveData<ResultOf<ProductData>> {
        return liveData {
            _isLoading.value = true
            try {
                emit(repository.getProducts(endPoint))
            } catch (err: Exception) {
                emit(ResultOf.Failure(error = err))
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getNumberOfFilters() = _filteredValues.value?.size

    fun clearFilters() {
        _filteredValues.apply {
            value?.clear()
            notifyObserver()
        }
    }

    fun isLoading() = _isLoading

    private fun filtersToEndPoint(): String {
        val stringBuilder =  StringBuilder("")
        return if (_filteredValues.value?.size == 0) {
            PRODUCT_BASE_ENDPOINT
        } else {
            _filteredValues.value?.forEachIndexed { index, item ->
                val filterType = FilterType.valueOf(item.group!!)
                stringBuilder.append("${if (index == 0) "" else "&"}${filterType?.value?.second}=${item.id}")
            }
            "$PRODUCT_BASE_ENDPOINT?$stringBuilder"
        }
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    fun <T> filter(list: MutableList<T>, predicate: Predicate<T>) {
        val itr = list.iterator()

        while (itr.hasNext()) {
            val t = itr.next()
            if (predicate.test(t)) {
                itr.remove()
            }
        }
    }
}