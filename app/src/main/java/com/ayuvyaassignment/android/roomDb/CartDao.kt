package com.ayuvyaassignment.android.roomDb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartData: CartData)

    @Delete
    suspend fun delete(cartData: CartData)

    @Query("UPDATE ayuvya_cart_table SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int)

    @Query("SELECT * FROM ayuvya_cart_table")
    fun getAllCartItems(): LiveData<List<CartData>>

    @Query("DELETE FROM ayuvya_cart_table WHERE productId = :productId")
    suspend fun deleteByProductId(productId: Int)

    @Query("SELECT COUNT(DISTINCT productId) FROM ayuvya_cart_table")
    fun getDistinctProductCount(): LiveData<Int>
}
