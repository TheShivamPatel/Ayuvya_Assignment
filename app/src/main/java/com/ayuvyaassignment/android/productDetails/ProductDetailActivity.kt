package com.ayuvyaassignment.android.productDetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ayuvyaassignment.android.databinding.ActivityProductDetailBinding
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ayuvyaassignment.android.R
import com.ayuvyaassignment.android.adapter.ProductAdapter
import com.ayuvyaassignment.android.cart.CartActivity
import com.ayuvyaassignment.android.model.Product
import com.ayuvyaassignment.android.repository.MainRepo
import com.ayuvyaassignment.android.roomDb.AyuvyaDatabase
import com.ayuvyaassignment.android.roomDb.CartDao
import com.ayuvyaassignment.android.roomDb.CartData
import com.ayuvyaassignment.android.viewModel.MainViewModel
import com.ayuvyaassignment.android.viewModel.MainViewModelProvider
import com.ayuvyaassignment.android.viewUtils.ViewUtils
import com.ayuvyaassignment.android.viewUtils.ViewUtils.setStatusBarColor
import com.bumptech.glide.Glide

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var repo: MainRepo
    private lateinit var cartDao: CartDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getSerializableExtra("PRODUCT_KEY") as? Product
        setUpToolbar()
        setUpStatusBar()
        if (product != null) {
            setUpViewModel()
            setUpView(product)
        } else {
            Log.e("zzz", "Product data is missing!")
        }
    }

    private fun setUpViewModel() {
        cartDao = AyuvyaDatabase.getDatabase(this).cartDao()
        repo = MainRepo(cartDao)
        viewModel = ViewModelProvider(this, MainViewModelProvider(repo))[MainViewModel::class.java]
        setUpObserver()
    }

    private fun setUpObserver() {
        viewModel.cartProductCount.observe(this, Observer { count ->
            binding.toolBar.cartCount.text = count.toString()
        })
    }


    private fun setUpToolbar() {
        binding.toolBar.toolbarBackIcon.setOnClickListener { finish() }
        binding.toolBar.cartCount.visibility = View.VISIBLE
        binding.toolBar.toolbarIconMore.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun setUpStatusBar() {
        ViewUtils.addLightStatusBar(this@ProductDetailActivity)
        ViewUtils.setUpStatusBar(this, true)
        setStatusBarColor(
            this@ProductDetailActivity,
            ContextCompat.getColor(this@ProductDetailActivity, R.color.white)
        )
    }

    private fun setUpView(product: Product) {
        Glide.with(this)
            .load(product.images.first())
            .into(binding.productImg)

        binding.toolBar.toolbarTitle.text = product.name
        binding.productDescTv.text = product.description
        binding.productRatingTv.text = "${product.rating}⭐"
        binding.productReviewsTv.text = "5673+ reviews"
        binding.mrpTv.text = "₹${product.mrp}"
        binding.priceTv.text = "₹${product.price}"

        binding.addToCartBtn.setOnClickListener {
            setupAddToCartButton(product)
        }
    }

    private fun setupAddToCartButton(product: Product) {
        binding.addToCartBtn.setOnClickListener {
            val cart = CartData(
                productId = product.id,
                name = product.name,
                price = product.price,
                mrp = product.mrp,
                image = product.images.firstOrNull() ?: "",
                quantity = 1
            )
            viewModel.insertToCart(cart)
            binding.addToCartBtn.visibility = View.GONE
            binding.addToCartOperationBtn.visibility = View.VISIBLE
        }

        var quantity = 1
        binding.minusBtn.setOnClickListener {
            if (quantity > 1) {
                val newQuantity = quantity--
                binding.quantityTv.text = quantity.toString()
                viewModel.updateCartQuantity(product.id, newQuantity)
            } else {
                binding.addToCartOperationBtn.visibility = View.GONE
                binding.addToCartBtn.visibility = View.VISIBLE
            }
        }

        binding.plusBtn.setOnClickListener {
            val newQuantity = quantity++
            binding.quantityTv.text = quantity.toString()
            viewModel.updateCartQuantity(product.id, newQuantity)
        }
    }
}

