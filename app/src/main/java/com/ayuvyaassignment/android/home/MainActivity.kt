package com.ayuvyaassignment.android.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ayuvyaassignment.android.R
import com.ayuvyaassignment.android.adapter.ProductAdapter
import com.ayuvyaassignment.android.cart.CartActivity
import com.ayuvyaassignment.android.databinding.ActivityMainBinding
import com.ayuvyaassignment.android.model.Product
import com.ayuvyaassignment.android.productDetails.ProductDetailActivity
import com.ayuvyaassignment.android.repository.MainRepo
import com.ayuvyaassignment.android.roomDb.AyuvyaDatabase
import com.ayuvyaassignment.android.roomDb.CartDao
import com.ayuvyaassignment.android.roomDb.CartData
import com.ayuvyaassignment.android.viewModel.MainViewModel
import com.ayuvyaassignment.android.viewModel.MainViewModelProvider
import com.ayuvyaassignment.android.viewUtils.ViewUtils
import com.ayuvyaassignment.android.viewUtils.ViewUtils.setStatusBarColor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var repo: MainRepo
    private lateinit var adapter: ProductAdapter
    private lateinit var cartDao: CartDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpObserver()
        setUpToolbar()
        setUpStatusBar()
        setUpProductRecyclerview()
    }

    private fun setUpObserver() {
        viewModel.cartProductCount.observe(this, Observer { count ->
            binding.toolBar.cartCount.text = count.toString()
        })
    }

    private fun setUpToolbar() {
        binding.toolBar.toolbarBackIcon.visibility = View.GONE
        binding.toolBar.cartCount.visibility = View.VISIBLE
        binding.toolBar.toolbarTitle.text = getString(R.string.ayuvya)
        binding.toolBar.toolbarIconMore.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun setUpStatusBar() {
        ViewUtils.addLightStatusBar(this@MainActivity)
        ViewUtils.setUpStatusBar(this, true)
        setStatusBarColor(
            this@MainActivity,
            ContextCompat.getColor(this@MainActivity, R.color.white)
        )
    }

    private fun setUpViewModel() {
        cartDao = AyuvyaDatabase.getDatabase(this).cartDao()
        repo = MainRepo(cartDao)
        viewModel = ViewModelProvider(this, MainViewModelProvider(repo))[MainViewModel::class.java]
    }

    private fun setUpProductRecyclerview() {
        binding.productRv.layoutManager = GridLayoutManager(this, 2)

        adapter = ProductAdapter(
            productList = viewModel.allProduct,
            cartDataMap = emptyMap(),
            object : ProductAdapter.OnItemClick {
                override fun onItemClick(product: Product) {
                    val intent = Intent(this@MainActivity, ProductDetailActivity::class.java)
                    intent.putExtra("PRODUCT_KEY", product)
                    startActivity(intent)
                }

                override fun addToCart(product: Product) {
                    val cart = CartData(
                        productId = product.id,
                        name = product.name,
                        price = product.price,
                        mrp = product.mrp,
                        image = product.images.firstOrNull() ?: "",
                        quantity = 1
                    )
                    viewModel.insertToCart(cart)
                }

                override fun updateQuantity(productId: Int, newQuantity: Int) {
                    viewModel.updateCartQuantity(productId, newQuantity)
                }
            }
        )

        binding.productRv.adapter = adapter

        viewModel.getCart().observe(this) { cartItems ->
            adapter.updateCartData(cartItems)
        }
    }
}