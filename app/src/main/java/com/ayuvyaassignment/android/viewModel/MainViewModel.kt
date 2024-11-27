package com.ayuvyaassignment.android.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ayuvyaassignment.android.model.Product
import com.ayuvyaassignment.android.repository.MainRepo
import com.ayuvyaassignment.android.roomDb.CartData
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepo) : ViewModel() {

    val cartProductCount: LiveData<Int> = repo.getDistinctProductCount()

    val allProduct = mutableListOf<Product>()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            val data = repo.getData()
            allProduct.addAll(data)
        }
    }

    fun insertToCart(cartData: CartData) {
        viewModelScope.launch {
            repo.addToCart(cartData)
        }
    }

    fun removeFromCart(cartData: CartData) {
        viewModelScope.launch {
            repo.removeFromCart(cartData)
        }
    }

    fun updateCartQuantity(productId: Int, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity == 0) {
                repo.deleteCartItemByProductId(productId)
            } else {
                repo.updateCartItemQuantity(productId, newQuantity)
            }
        }
    }

    fun getCart() = repo.getAllProductFromCart()
}

class MainViewModelProvider(private val repo: MainRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}
