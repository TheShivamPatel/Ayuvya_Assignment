package com.ayuvyaassignment.android.cart

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayuvyaassignment.android.R
import com.ayuvyaassignment.android.adapter.CartAdapter
import com.ayuvyaassignment.android.adapter.ProductAdapter
import com.ayuvyaassignment.android.databinding.ActivityCartBinding
import com.ayuvyaassignment.android.model.Product
import com.ayuvyaassignment.android.repository.MainRepo
import com.ayuvyaassignment.android.roomDb.AyuvyaDatabase
import com.ayuvyaassignment.android.roomDb.CartDao
import com.ayuvyaassignment.android.roomDb.CartData
import com.ayuvyaassignment.android.viewModel.MainViewModel
import com.ayuvyaassignment.android.viewModel.MainViewModelProvider
import com.ayuvyaassignment.android.viewUtils.ViewUtils
import com.ayuvyaassignment.android.viewUtils.ViewUtils.setStatusBarColor

class CartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCartBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var repo: MainRepo
    private lateinit var adapter: CartAdapter
    private lateinit var cartDao: CartDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpToolbar()
        setUpStatusBar()
        setUpCartRecyclerview()
    }

    private fun setUpViewModel() {
        cartDao = AyuvyaDatabase.getDatabase(this).cartDao()
        repo = MainRepo(cartDao)
        viewModel = ViewModelProvider(this, MainViewModelProvider(repo))[MainViewModel::class.java]
    }

    private fun setUpToolbar() {
        binding.toolBar.toolbarBackIcon.setOnClickListener { finish() }
        binding.toolBar.toolbarTitle.text = getString(R.string.cart)
        binding.toolBar.toolbarIconMore.visibility = View.GONE
    }

    private fun setUpStatusBar() {
        ViewUtils.addLightStatusBar(this@CartActivity)
        ViewUtils.setUpStatusBar(this, true)
        setStatusBarColor(
            this@CartActivity,
            ContextCompat.getColor(this@CartActivity, R.color.white)
        )
    }

    private fun setUpCartRecyclerview() {
        binding.cartRv.layoutManager = LinearLayoutManager(this)
        viewModel.getCart().observe(this, Observer {
            adapter = CartAdapter(it, object : CartAdapter.OnItemClick{
                override fun removeFromCart(cart: CartData) {
                    viewModel.removeFromCart(cart)
                }
                override fun updateQuantity(productId: Int, newQuantity: Int) {
                    viewModel.updateCartQuantity(productId, newQuantity)
                }
            })
            binding.cartRv.adapter = adapter
        })
    }
}