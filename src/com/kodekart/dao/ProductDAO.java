package com.kodekart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kodekart.model.Product;
import com.kodekart.util.DBConnection;

public class ProductDAO {
	
	//Add product
	public boolean addProduct(Product p) {
	    boolean result = false;

	    try {
	        Connection con = DBConnection.getConnection();
	        String sql = "INSERT INTO products(name, category, price, quantity, description) VALUES(?,?,?,?,?)";
	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setString(1, p.getName());
	        ps.setString(2, p.getCategory());
	        ps.setDouble(3, p.getPrice());
	        ps.setInt(4, p.getQuantity());
	        ps.setString(5, p.getDescription());

	        if (ps.executeUpdate() > 0) {
	            result = true;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	//Update product
	public boolean updateProduct(Product p) {
	    boolean updated = false;

	    try {
	        Connection con = DBConnection.getConnection();
	        String sql = "UPDATE products SET name=?, category=?, price=?, quantity=?, description=? WHERE id=?";
	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setString(1, p.getName());
	        ps.setString(2, p.getCategory());
	        ps.setDouble(3, p.getPrice());
	        ps.setInt(4, p.getQuantity());
	        ps.setString(5, p.getDescription());
	        ps.setInt(6, p.getId());

	        if (ps.executeUpdate() > 0) {
	            updated = true;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return updated;
	}
	
	//Delete product
	public boolean deleteProduct(int id) {
	    boolean deleted = false;

	    try {
	        Connection con = DBConnection.getConnection();
	        String sql = "DELETE FROM products WHERE id=?";
	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, id);

	        if (ps.executeUpdate() > 0) {
	            deleted = true;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return deleted;
	}
	
	//View All products
	public List<Product> getAllProducts() {
	    List<Product> list = new ArrayList<>();

	    try {
	        Connection con = DBConnection.getConnection();
	        String sql = "SELECT * FROM products";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Product p = new Product();
	            p.setId(rs.getInt("id"));
	            p.setName(rs.getString("name"));
	            p.setCategory(rs.getString("category"));
	            p.setPrice(rs.getDouble("price"));
	            p.setQuantity(rs.getInt("quantity"));
	            p.setDescription(rs.getString("description"));
	            list.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	// Search by product name
	public List<Product> searchByName(String name) {
	    List<Product> list = new ArrayList<>();

	    try {
	        Connection con = DBConnection.getConnection();
	        String sql = "SELECT * FROM products WHERE name LIKE ?";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, "%" + name + "%");

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Product p = new Product();
	            p.setId(rs.getInt("id"));
	            p.setName(rs.getString("name"));
	            p.setCategory(rs.getString("category"));
	            p.setPrice(rs.getDouble("price"));
	            p.setQuantity(rs.getInt("quantity"));
	            p.setDescription(rs.getString("description"));
	            list.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	//Search by category
	public List<Product> searchByCategory(String category) {
	    List<Product> list = new ArrayList<>();

	    try {
	        Connection con = DBConnection.getConnection();
	        String sql = "SELECT * FROM products WHERE category LIKE ?";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, "%" + category + "%");

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Product p = new Product();
	            p.setId(rs.getInt("id"));
	            p.setName(rs.getString("name"));
	            p.setCategory(rs.getString("category"));
	            p.setPrice(rs.getDouble("price"));
	            p.setQuantity(rs.getInt("quantity"));
	            p.setDescription(rs.getString("description"));
	            list.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

}
