package com.ayuvyaassignment.android.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayuvyaassignment.android.databinding.ProductItemBinding
import com.ayuvyaassignment.android.model.Product
import com.ayuvyaassignment.android.roomDb.CartData
import com.bumptech.glide.Glide

class ProductAdapter(
    private val productList: List<Product>,
    private var cartDataMap: Map<Int, CartData>,
    private val listener: OnItemClick,
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, cartItem: CartData?) {
            binding.productNameTv.text = product.name
            binding.mrpTv.apply {
                text = "₹${product.mrp}"
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            binding.priceTv.text = "₹${product.price}"

            if (product.mrp > product.price) {
                val percentageOff = ((product.mrp - product.price) / product.mrp * 100).toInt()
                binding.offPercentageTv.text = "$percentageOff% OFF"
            } else {
                binding.offPercentageTv.text = ""
            }

            Glide.with(binding.productImg.context)
                .load(product.images.firstOrNull())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.productImg)

            if (cartItem != null) {
                binding.quantityContainer.visibility = View.VISIBLE
                binding.addToCartBtn.visibility = View.GONE
                binding.quantityTv.text = cartItem.quantity.toString()

                binding.plusBtn.setOnClickListener {
                    val newQuantity = cartItem.quantity + 1
                    listener.updateQuantity(cartItem.productId, newQuantity)
                }
                binding.minusBtn.setOnClickListener {
                    val newQuantity = (cartItem.quantity - 1).coerceAtLeast(0)
                    listener.updateQuantity(cartItem.productId, newQuantity)
                }
            } else {
                binding.quantityContainer.visibility = View.GONE
                binding.addToCartBtn.visibility = View.VISIBLE

                binding.addToCartBtn.setOnClickListener {
                    listener.addToCart(product)
                }
            }

            binding.root.setOnClickListener {
                listener.onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        val cartItem = getCartItemForProduct(product.id)
        holder.bind(product, cartItem)
    }

    override fun getItemCount(): Int = productList.size

    private fun getCartItemForProduct(productId: Int): CartData? {
        return cartDataMap[productId]
    }

    fun updateCartData(cartItems: List<CartData>) {
        cartDataMap = cartItems.associateBy { it.productId }
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun onItemClick(product: Product)
        fun addToCart(product: Product)
        fun updateQuantity(productId: Int, newQuantity: Int)
    }
}