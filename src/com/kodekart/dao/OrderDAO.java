package com.kodekart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import com.kodekart.model.CartItem;
import com.kodekart.model.Order;
import com.kodekart.model.OrderItem;
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
	
	public List<Order> getOrdersByUser(int userId) {

	    List<Order> list = new ArrayList<>();

	    try {
	        Connection con = DBConnection.getConnection();

	        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY id DESC";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, userId);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Order o = new Order();
	            o.setId(rs.getInt("id"));
	            o.setUserId(rs.getInt("user_id"));
	            o.setTotalAmount(rs.getDouble("total_amount"));

	            list.add(o);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	public List<OrderItem> getOrderItems(int orderId) {

	    List<OrderItem> list = new ArrayList<>();

	    try {
	        Connection con = DBConnection.getConnection();

	        String sql = "SELECT * FROM order_items WHERE order_id = ?";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, orderId);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            OrderItem item = new OrderItem();
	            item.setId(rs.getInt("id"));
	            item.setOrderId(rs.getInt("order_id"));
	            item.setProductId(rs.getInt("product_id"));
	            item.setQuantity(rs.getInt("quantity"));
	            item.setPrice(rs.getDouble("price"));

	            list.add(item);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

}
