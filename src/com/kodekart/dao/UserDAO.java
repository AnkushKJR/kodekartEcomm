package com.kodekart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kodekart.model.User;
import com.kodekart.util.DBConnection;

public class UserDAO {
	
	public boolean registerUser(User user) {
        
		boolean isRegistered = false;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO users(name, email, phone, password) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                isRegistered = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isRegistered;
    }
	
	public User loginUser(String email, String password) {
        User user = null;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setPassword(rs.getString("password"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

}
