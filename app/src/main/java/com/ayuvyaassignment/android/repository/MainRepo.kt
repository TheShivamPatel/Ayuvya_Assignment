package com.ayuvyaassignment.android.repository

import androidx.lifecycle.LiveData
import com.ayuvyaassignment.android.model.Product
import com.ayuvyaassignment.android.roomDb.CartDao
import com.ayuvyaassignment.android.roomDb.CartData

class MainRepo(private val cartDao: CartDao) {

    suspend fun addToCart(cartData: CartData) = cartDao.insert(cartData)

    suspend fun removeFromCart(cartData: CartData) = cartDao.delete(cartData)

    fun getDistinctProductCount() : LiveData<Int> = cartDao.getDistinctProductCount()

    suspend fun updateCartItemQuantity(productId: Int, quantity: Int) =
        cartDao.updateQuantity(productId, quantity)

    suspend fun deleteCartItemByProductId(productId: Int) =
        cartDao.deleteByProductId(productId)


    fun getAllProductFromCart() = cartDao.getAllCartItems()

    fun getData(): List<Product> {
        return getDummyProducts()
    }

    // This is the mock data of API
    private fun getDummyProducts(): List<Product> {
        return listOf(
            Product(
                id = 1,
                name = "Ashwagandha Capsules",
                price = 299.0,
                mrp = 399.0,
                rating = 4.7f,
                images = listOf(
                    "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fashwagandha_new_carousel_image_updated_for_dynamic_22nov__1.webp%3Fq%3D70&w=640&q=75",
                    "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fashwagandha_pack_of_2_new_image_25_nov.webp%3Fq%3D70&w=640&q=75",
                ),
                description = "Boost your energy and reduce stress with pure Ashwagandha capsules.",
                isFeatured = true
            ),
            Product(
                id = 2,
                name = "Chyawanprash",
                price = 450.0,
                mrp = 550.0,
                rating = 4.5f,
                images = listOf(
                    "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fbbf_carousel_1slide_6nov2024.webp&w=256&q=75"
                ),
                description = "Rich in antioxidants and immunity boosters for the whole family.",
                isFeatured = true
            ),
            Product(
                id = 3,
                name = "Ayuvya Ace-it - With 2-in-1 Ayurvedic Science for Better Absorption\n",
                price = 120.0,
                mrp = 150.0,
                rating = 4.3f,
                images = listOf(
                    "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2F0_ayuvya--ace-it_32_9cd8d.webp%3Fq%3D70&w=640&q=75",
                    "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2FACE_IT_CAROUSEL_SLIDE_800_X_800_5-03-2024.webp%3Fq%3D70&w=640&q=75"
                ),
                description = "Cleanse your skin naturally with the goodness of neem.",
                isFeatured = false
            ),
            Product(
                id = 4,
                name = "Brahmi Oil",
                price = 250.0,
                mrp = 300.0,
                rating = 4.6f,
                images = listOf(
                    "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fgold_serum_new_carousel_27july__1.webp%3Fq%3D70&w=640&q=75",
                    "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fgold_serum_new_carousel_27july__2.webp%3Fq%3D70&w=640&q=75"
                ),
                description = "Promote hair growth and reduce stress with this ancient Ayurvedic formula.",
                isFeatured = false
            ),
            Product(
                id = 5,
                name = "Tulsi Drops",
                price = 199.0,
                mrp = 250.0,
                rating = 4.8f,
                images = listOf(
                    "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2F0_ayuvya--ace-it_32_9cd8d.webp%3Fq%3D70&w=640&q=75",
                    "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fgold_serum_new_carousel_27july__2.webp%3Fq%3D70&w=640&q=75"
                ),
                description = "Improve your immunity with these pure and organic Tulsi drops.",
                isFeatured = true
            ),
            Product(
                id = 6,
                name = "Shilajit Resin",
                price = 799.0,
                mrp = 999.0,
                rating = 4.9f,
                images = listOf(
                    "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fbbf_carousel_1slide_6nov2024.webp&w=256&q=75"
                ),
                description = "Enhance vitality and stamina with authentic Shilajit resin.",
                isFeatured = true
            ),
            Product(
                id = 7,
                name = "Herbal Tea",
                price = 150.0,
                mrp = 200.0,
                rating = 4.4f,
                images = listOf(
                    "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fbbf_carousel_1slide_6nov2024.webp&w=256&q=75"
                ),
                description = "Relax your mind and boost digestion with this herbal tea blend.",
                isFeatured = false
            )
        )
    }
}
