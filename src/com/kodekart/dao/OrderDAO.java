package com.kodekart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Statement;

import com.kodekart.model.CartItem;
import com.kodekart.model.Product;
import com.kodekart.util.DBConnection;

public class OrderDAO {
	
	public int placeOrder(int userId) {
	    int orderId = -1;

	    try {
	        Connection con = DBConnection.getConnection();
	        con.setAutoCommit(false);   // Start transaction

	        CartDAO cartDAO = new CartDAO();
	        ProductDAO productDAO = new ProductDAO();

	        List<CartItem> cartItems = cartDAO.getCartItems(userId);

	        if (cartItems.isEmpty()) {
	            System.out.println("Cart is empty.");
	            return -1;
	        }

	        double total = 0;

	        for (CartItem c : cartItems) {
	            Product p = productDAO.getProductById(c.getProductId());
	            total += p.getPrice() * c.getQuantity();
	        }

	        // Insert order
	        String orderSql = "INSERT INTO orders(user_id, total_amount) VALUES(?,?)";
	        PreparedStatement orderPs = con.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);

	        orderPs.setInt(1, userId);
	        orderPs.setDouble(2, total);
	        orderPs.executeUpdate();

	        ResultSet rs = orderPs.getGeneratedKeys();
	        if (rs.next()) {
	            orderId = rs.getInt(1);
	        }

	        // Insert order items
	        String itemSql = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES(?,?,?,?)";
	        PreparedStatement itemPs = con.prepareStatement(itemSql);

	        for (CartItem c : cartItems) {
	            Product p = productDAO.getProductById(c.getProductId());

	            itemPs.setInt(1, orderId);
	            itemPs.setInt(2, p.getId());
	            itemPs.setInt(3, c.getQuantity());
	            itemPs.setDouble(4, p.getPrice());
	            itemPs.executeUpdate();

	            // Reduce stock
	            productDAO.reduceStock(p.getId(), c.getQuantity());
	        }

	        // Clear cart
	        cartDAO.clearCart(userId);

	        con.commit();   // End transaction

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return orderId;
	}
	
	

}
