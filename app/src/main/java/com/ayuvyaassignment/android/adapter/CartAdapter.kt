package com.ayuvyaassignment.android.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayuvyaassignment.android.databinding.CartItemBinding
import com.ayuvyaassignment.android.databinding.ProductItemBinding
import com.ayuvyaassignment.android.model.Product
import com.ayuvyaassignment.android.roomDb.CartData
import com.bumptech.glide.Glide

class CartAdapter(
    private var cartList: List<CartData>,
    private val listener: OnItemClick,
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CartData) {
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
                .load(product.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.productImg)

            binding.deleteBtn.setOnClickListener {
                listener.removeFromCart(product)
            }

            binding.minusBtn.setOnClickListener {
                if (product.quantity > 1) {
                    val newQuantity = product.quantity - 1
                    listener.updateQuantity(product.productId, newQuantity)
                }
            }

            binding.plusBtn.setOnClickListener {
                val newQuantity = product.quantity + 1
                listener.updateQuantity(product.productId, newQuantity)
            }

            binding.quantityTv.text = product.quantity.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int = cartList.size

    fun updateCartData(newCartList: List<CartData>) {
        cartList = newCartList
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun removeFromCart(cart: CartData)
        fun updateQuantity(productId: Int, newQuantity: Int)
    }
}