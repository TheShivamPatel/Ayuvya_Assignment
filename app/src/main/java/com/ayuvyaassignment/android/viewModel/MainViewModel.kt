package com.ayuvyaassignment.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ayuvyaassignment.android.model.Product
import com.ayuvyaassignment.android.repository.MainRepo
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepo) : ViewModel() {

    val allProduct = mutableListOf<Product>()

    init {
        getAllProducts()
    }

    fun  getAllProducts() {
        viewModelScope.launch {
            val data = repo.getData()
            allProduct.addAll(data)
        }
    }
}

class MainViewModelProvider(private val repo: MainRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}
