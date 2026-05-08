package com.example.demo.repository;

import com.example.demo.entity.CartItem; 
import com.example.demo.entity.User;

import jakarta.transaction.Transactional;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Integer> {
	
	// Find a cart item by User and Product entities
    Optional<CartItem> findByUserAndProduct(User user, Product product);

    // Find a cart item by userId and productId using a custom query
    @Query("SELECT c FROM CartItem c WHERE c.user.userId = :userId AND c.product.productId = :productId")
    Optional<CartItem> findByUserAndProduct(@Param("userId") int userId, @Param("productId") int productId);

    // Count total items in the cart for a specific user
    @Query("SELECT COALESCE(SUM(c.quantity), 0) FROM CartItem c WHERE c.user = :user")
    int countTotalItems(@Param("user") User user);

    // Find all cart items for a specific user
    List<CartItem> findAllByUser(User user);

    // Fetch cart items with product details for a specific user
    @Query("SELECT c FROM CartItem c JOIN FETCH c.product p LEFT JOIN FETCH ProductImage pi "
    		+ "ON p.productId = pi.product.productId WHERE c.user.userId = :userId")
    List<CartItem> findCartItemsWithProductDetails(@Param("userId") int userId);

    // Update the quantity of a specific cart item
    @Query("UPDATE CartItem c SET c.quantity = :quantity WHERE c.id = :cartItemId")
    void updateCartItemQuantity(@Param("cartItemId") int cartItemId, @Param("quantity") int quantity);
    
    //Delete cart items
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.user.userId = :userId AND c.product.productId = :productId")
    void deleteCartItem(int userId,int productId);

	//to delete all items in the cart or clear the cart
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.user.userId = :userId")
    void deleteAllCartItemsByUserId(int userId);
}
