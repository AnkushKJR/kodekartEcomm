package com.kodekart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kodekart.model.CartItem;
import com.kodekart.util.DBConnection;

public class CartDAO {
	
	// Add to cart [if already present increment the quantity]
	public boolean addToCart(int userId, int productId, int quantity) {
	    boolean result = false;

	    try {
	        Connection con = DBConnection.getConnection();

	        // check if item already exists
	        String checkSql = "SELECT * FROM cart WHERE user_id = ? AND product_id = ?";
	        PreparedStatement checkPs = con.prepareStatement(checkSql);
	        checkPs.setInt(1, userId);
	        checkPs.setInt(2, productId);

	        ResultSet rs = checkPs.executeQuery();

	        if (rs.next()) {
	            // update quantity
	            String updateSql = "UPDATE cart SET quantity = quantity + ? WHERE user_id = ? AND product_id = ?";
	            PreparedStatement updatePs = con.prepareStatement(updateSql);
	            updatePs.setInt(1, quantity);
	            updatePs.setInt(2, userId);
	            updatePs.setInt(3, productId);

	            if (updatePs.executeUpdate() > 0) {
	                result = true;
	            }

	        } else {
	            // insert new item
	            String insertSql = "INSERT INTO cart(user_id, product_id, quantity) VALUES (?, ?, ?)";
	            PreparedStatement insertPs = con.prepareStatement(insertSql);
	            insertPs.setInt(1, userId);
	            insertPs.setInt(2, productId);
	            insertPs.setInt(3, quantity);

	            if (insertPs.executeUpdate() > 0) {
	                result = true;
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	//View Cart Items
	public List<CartItem> getCartItems(int userId) {
	    List<CartItem> list = new ArrayList<>();

	    try {
	        Connection con = DBConnection.getConnection();

	        String sql = "SELECT * FROM cart WHERE user_id = ?";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, userId);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            CartItem c = new CartItem();
	            c.setId(rs.getInt("id"));
	            c.setUserId(rs.getInt("user_id"));
	            c.setProductId(rs.getInt("product_id"));
	            c.setQuantity(rs.getInt("quantity"));

	            list.add(c);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	// Remove Item From Cart
	public boolean removeCartItem(int cartId) {
	    boolean deleted = false;

	    try {
	        Connection con = DBConnection.getConnection();
	        String sql = "DELETE FROM cart WHERE id = ?";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, cartId);

	        if (ps.executeUpdate() > 0) {
	            deleted = true;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return deleted;
	}

}
