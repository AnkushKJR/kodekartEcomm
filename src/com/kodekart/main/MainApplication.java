package com.kodekart.main;

import java.util.List;
import java.util.Scanner;

import com.kodekart.dao.CartDAO;
import com.kodekart.dao.OrderDAO;
import com.kodekart.dao.ProductDAO;
import com.kodekart.dao.UserDAO;
import com.kodekart.model.CartItem;
import com.kodekart.model.Product;
import com.kodekart.model.User;

public class MainApplication {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        
        while (true) {
            System.out.println("===== KodeKart E-Commerce =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    register(sc, userDAO);
                    break;
                case 2:
                    login(sc, userDAO);
                    break;
                case 3:
                    System.out.println("Thank you for using KodeKart!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
		
	}
	
	private static void register(Scanner sc, UserDAO userDAO) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = new User(name, email, phone, password);
        boolean result = userDAO.registerUser(user);

        if (result) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed! Email may already exist.");
        }
    }
	
	private static void login(Scanner sc, UserDAO userDAO) {
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User loggedInUser = userDAO.loginUser(email, password);

        if (loggedInUser != null) {
            System.out.println("Login successful! Welcome " + loggedInUser.getName());
            
            if (loggedInUser.getEmail().equals("admin@kodekart.com")) {
                adminMenu(sc);
            } else {
                userMenu(sc, loggedInUser);
            }

        } else {
            System.out.println("Invalid email or password.");
        }
    }
	
	private static void adminMenu(Scanner sc) {

	    ProductDAO productDAO = new ProductDAO();

	    while (true) {	
	        System.out.println("===== Admin Menu =====");
	        System.out.println("1. Add Product");
	        System.out.println("2. Update Product");
	        System.out.println("3. Delete Product");
	        System.out.println("4. View All Products");
	        System.out.println("5. Logout");
	        System.out.print("Enter choice: ");

	        int choice = sc.nextInt();
	        sc.nextLine();

	        switch (choice) {
	            case 1:
	            	addProduct(sc, productDAO);
	                break;
	            case 2:
	            	updateProduct(sc, productDAO);
	                break;
	            case 3:
	            	deleteProduct(sc, productDAO);
	                break;
	            case 4:
	            	viewAllProducts(productDAO);
	                break;
	            case 5:
	                return;
	            default:
	                System.out.println("Invalid choice!");
	        }
	    }
	}
	
	private static void addProduct(Scanner sc, ProductDAO productDAO) {

	    System.out.print("Enter product name: ");
	    String name = sc.nextLine();

	    System.out.print("Enter category: ");
	    String category = sc.nextLine();

	    System.out.print("Enter price: ");
	    double price = sc.nextDouble();

	    System.out.print("Enter quantity: ");
	    int quantity = sc.nextInt();
	    sc.nextLine();

	    System.out.print("Enter description: ");
	    String description = sc.nextLine();

	    Product p = new Product(name, category, price, quantity, description);

	    if (productDAO.addProduct(p)) {
	        System.out.println("Product added successfully!");
	    } else {
	        System.out.println("Failed to add product.");
	    }
	}
	
	private static void updateProduct(Scanner sc, ProductDAO productDAO) {

	    System.out.print("Enter Product ID to update: ");
	    int id = sc.nextInt();
	    sc.nextLine();

	    System.out.print("Enter new name: ");
	    String name = sc.nextLine();

	    System.out.print("Enter new category: ");
	    String category = sc.nextLine();

	    System.out.print("Enter new price: ");
	    double price = sc.nextDouble();

	    System.out.print("Enter new quantity: ");
	    int quantity = sc.nextInt();
	    sc.nextLine();

	    System.out.print("Enter new description: ");
	    String description = sc.nextLine();

	    Product p = new Product(name, category, price, quantity, description);
	    p.setId(id);

	    if (productDAO.updateProduct(p)) {
	        System.out.println("Product updated successfully!");
	    } else {
	        System.out.println("Failed to update product.");
	    }
	}
	
	private static void deleteProduct(Scanner sc, ProductDAO productDAO) {
	    System.out.print("Enter Product ID to delete: ");
	    int id = sc.nextInt();

	    if (productDAO.deleteProduct(id)) {
	        System.out.println("Product deleted.");
	    } else {
	        System.out.println("Failed to delete. ID may not exist.");
	    }
	}
	
	private static void viewAllProducts(ProductDAO productDAO) {
	    List<Product> list = productDAO.getAllProducts();

	    System.out.println("\n===== PRODUCT LIST =====");
	    for (Product p : list) {
	        System.out.println("ID: " + p.getId());
	        System.out.println("Name: " + p.getName());
	        System.out.println("Category: " + p.getCategory());
	        System.out.println("Price: ₹" + p.getPrice());
	        System.out.println("Quantity: " + p.getQuantity());
	        System.out.println("Description: " + p.getDescription());
	        System.out.println("-----------------------------------");
	    }
	}
	
	private static void userMenu(Scanner sc, User user) {

	    ProductDAO productDAO = new ProductDAO();

	    while (true) {
	        System.out.println("\n===== USER MENU =====");
	        System.out.println("1. View All Products");
	        System.out.println("2. Search Product by Name");
	        System.out.println("3. Search Product by Category");
	        System.out.println("4. Add to Cart");
	        System.out.println("5. View Cart");
	        System.out.println("6. Remove Cart Item");
	        System.out.println("7. Place Order");
	        System.out.println("8. Logout");
	        System.out.print("Enter choice: ");

	        int choice = sc.nextInt();
	        sc.nextLine();

	        switch (choice) {
	            case 1:
	                viewAllProducts(productDAO);
	                break;

	            case 2:
	                System.out.print("Enter product name: ");
	                String name = sc.nextLine();
	                showProducts(productDAO.searchByName(name));
	                break;

	            case 3:
	                System.out.print("Enter category: ");
	                String category = sc.nextLine();
	                showProducts(productDAO.searchByCategory(category));
	                break;

	            case 4:
	            	System.out.println("Add to Cart");
	                addToCart(sc, user.getId());
	                break;

	            case 5:
	            	System.out.println("View Cart");
	                viewCart(user.getId());
	                break;

	            case 6:
	            	System.out.println("Remove Cart Item");
	                removeCartItem(sc);
	                break;
	            
	            case 7:
	                placeOrder(user.getId());
	                break;
	                
	            case 8:
	                return;

	            default:
	                System.out.println("Invalid choice!");
	        }
	    }
	}
	
	private static void showProducts(List<Product> list) {

	    if (list.isEmpty()) {
	        System.out.println("No products found.");
	        return;
	    }

	    System.out.println("\n===== PRODUCTS =====");

	    for (Product p : list) {
	        System.out.println("ID: " + p.getId());
	        System.out.println("Name: " + p.getName());
	        System.out.println("Category: " + p.getCategory());
	        System.out.println("Price: ₹" + p.getPrice());
	        System.out.println("Quantity: " + p.getQuantity());
	        System.out.println("Description: " + p.getDescription());
	        System.out.println("----------------------------------");
	    }
	}
	
	//Add to Cart
	private static void addToCart(Scanner sc, int userId) {

	    CartDAO cartDAO = new CartDAO();

	    System.out.print("Enter Product ID to add: ");
	    int productId = sc.nextInt();

	    System.out.print("Enter quantity: ");
	    int quantity = sc.nextInt();
	    sc.nextLine();

	    boolean result = cartDAO.addToCart(userId, productId, quantity);

	    if (result) {
	        System.out.println("Product added to cart!");
	    } else {
	        System.out.println("Failed to add. Check Product ID.");
	    }
	}
	
	//View Cart (with product details)
	private static void viewCart(int userId) {

	    CartDAO cartDAO = new CartDAO();
	    ProductDAO productDAO = new ProductDAO();

	    List<CartItem> list = cartDAO.getCartItems(userId);

	    if (list.isEmpty()) {
	        System.out.println("Your cart is empty.");
	        return;
	    }

	    System.out.println("\n===== YOUR CART =====");

	    double total = 0;

	    for (CartItem c : list) {

	        Product p = productDAO.getProductById(c.getProductId());  // we will create getProductById()

	        System.out.println("Cart ID: " + c.getId());
	        System.out.println("Product: " + p.getName());
	        System.out.println("Price: ₹" + p.getPrice());
	        System.out.println("Quantity: " + c.getQuantity());
	        System.out.println("----------------------------------");

	        total += p.getPrice() * c.getQuantity();
	    }

	    System.out.println("TOTAL AMOUNT: ₹" + total);
	}
	
	private static void removeCartItem(Scanner sc) {

	    CartDAO cartDAO = new CartDAO();

	    System.out.print("Enter Cart ID to remove: ");
	    int cartId = sc.nextInt();

	    boolean result = cartDAO.removeCartItem(cartId);

	    if (result) {
	        System.out.println("Item removed from cart.");
	    } else {
	        System.out.println("Failed to remove. Check Cart ID.");
	    }
	}
	
	private static void placeOrder(int userId) {
	    OrderDAO orderDAO = new OrderDAO();
	    int orderId = orderDAO.placeOrder(userId);

	    if (orderId > 0) {
	        System.out.println("Order placed successfully! Order ID: " + orderId);
	    } else {
	        System.out.println("Order failed! Cart may be empty.");
	    }
	}
	
}
