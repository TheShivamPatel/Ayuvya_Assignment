package com.ayuvyaassignment.android.repository

import com.ayuvyaassignment.android.model.Product

class MainRepo {

    fun getData() : List<Product> {
        return getDummyProducts()
    }

    private fun getDummyProducts(): List<Product> {
        return listOf(
            Product(
                name = "Ashwagandha Capsules",
                price = 299.0,
                mrp = 399.0,
                rating = 4.7f,
                images = listOf(
                    "https://example.com/images/ashwagandha1.jpg",
                    "https://example.com/images/ashwagandha2.jpg"
                ),
                description = "Boost your energy and reduce stress with pure Ashwagandha capsules.",
                isFeatured = true
            ),
            Product(
                name = "Chyawanprash",
                price = 450.0,
                mrp = 550.0,
                rating = 4.5f,
                images = listOf(
                    "https://example.com/images/chyawanprash1.jpg",
                    "https://example.com/images/chyawanprash2.jpg"
                ),
                description = "Rich in antioxidants and immunity boosters for the whole family.",
                isFeatured = true
            ),
            Product(
                name = "Neem Face Wash",
                price = 120.0,
                mrp = 150.0,
                rating = 4.3f,
                images = listOf(
                    "https://example.com/images/neemfacewash1.jpg",
                    "https://example.com/images/neemfacewash2.jpg"
                ),
                description = "Cleanse your skin naturally with the goodness of neem.",
                isFeatured = false
            ),
            Product(
                name = "Brahmi Oil",
                price = 250.0,
                mrp = 300.0,
                rating = 4.6f,
                images = listOf(
                    "https://example.com/images/brahmi_oil1.jpg",
                    "https://example.com/images/brahmi_oil2.jpg"
                ),
                description = "Promote hair growth and reduce stress with this ancient Ayurvedic formula.",
                isFeatured = false
            ),
            Product(
                name = "Tulsi Drops",
                price = 199.0,
                mrp = 250.0,
                rating = 4.8f,
                images = listOf(
                    "https://example.com/images/tulsi_drops1.jpg",
                    "https://example.com/images/tulsi_drops2.jpg"
                ),
                description = "Improve your immunity with these pure and organic Tulsi drops.",
                isFeatured = true
            ),
            Product(
                name = "Shilajit Resin",
                price = 799.0,
                mrp = 999.0,
                rating = 4.9f,
                images = listOf(
                    "https://example.com/images/shilajit1.jpg",
                    "https://example.com/images/shilajit2.jpg"
                ),
                description = "Enhance vitality and stamina with authentic Shilajit resin.",
                isFeatured = true
            ),
            Product(
                name = "Herbal Tea",
                price = 150.0,
                mrp = 200.0,
                rating = 4.4f,
                images = listOf(
                    "https://example.com/images/herbal_tea1.jpg",
                    "https://example.com/images/herbal_tea2.jpg"
                ),
                description = "Relax your mind and boost digestion with this herbal tea blend.",
                isFeatured = false
            )
        )
    }
}
