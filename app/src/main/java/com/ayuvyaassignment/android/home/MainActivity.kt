package com.ayuvyaassignment.android.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ayuvyaassignment.android.R
import com.ayuvyaassignment.android.adapter.ProductAdapter
import com.ayuvyaassignment.android.cart.CartActivity
import com.ayuvyaassignment.android.databinding.ActivityMainBinding
import com.ayuvyaassignment.android.repository.MainRepo
import com.ayuvyaassignment.android.viewModel.MainViewModel
import com.ayuvyaassignment.android.viewModel.MainViewModelProvider
import com.ayuvyaassignment.android.viewUtils.ViewUtils
import com.ayuvyaassignment.android.viewUtils.ViewUtils.setStatusBarColor

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var repo: MainRepo
    private lateinit var adapter : ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpToolbar()
        setUpStatusBar()
        setUpProductRecyclerview()
    }

    private fun setUpProductRecyclerview() {
        binding.productRv.layoutManager = GridLayoutManager(this, 2)
        adapter = ProductAdapter(viewModel.allProduct)
        binding.productRv.adapter = adapter
    }

    private fun setUpViewModel() {
        repo = MainRepo()
        viewModel = ViewModelProvider(this, MainViewModelProvider(repo))[MainViewModel::class.java]
    }

    private fun setUpToolbar() {
        binding.toolBar.toolbarBackIcon.visibility = View.GONE
        binding.toolBar.toolbarIconMore.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun setUpStatusBar() {
        ViewUtils.addLightStatusBar(this@MainActivity)
        setStatusBarColor(
            this@MainActivity,
            ContextCompat.getColor(this@MainActivity, R.color.white)
        )
    }
}