📦 KodeKart – Console-based E-Commerce Application (Java + JDBC + MySQL)

KodeKart is a menu-driven e-commerce application built using Core Java, JDBC, and MySQL, following a clean modular architecture (DAO + Model + UI layers).

🚀 Features

👤 User Module

    Register & Login

    Browse all products

    Search by name & category

    Add items to cart

    View cart with total amount

    Remove items from cart

    Place order

    View order history

🛠 Admin Module

    Add product

    Update product

    Delete product

    View all products

🔗 Tech Stack

    Java (Core Java, OOPS)

    MySQL (Relational Database)

    JDBC (Connectivity & SQL operations)

    DAO Pattern

    Menu-driven Console UI

📁 Project Structure
    src/
    ├── model/      # All POJO classes (User, Product, CartItem, Order, OrderItem)
    ├── dao/        # All DAO classes (UserDAO, ProductDAO, CartDAO, OrderDAO)
    ├── main/       # MainApplication with menu-driven UI
    └── util/       # Utility classes (DBConnection)

🧠 Key Concepts Implemented

    JDBC (PreparedStatement, ResultSet, DriverManager)

    DAO Pattern for clean separation of logic

    Transaction Management for order placement

    Auto-increment primary keys

    Multi-table SQL operations (orders + order_items)

    Clean modular design with separate layers

    Console UI with clear menus for users & admin

📝 How to Run

    Import the project into Eclipse or IntelliJ.

    Add the MySQL Connector JAR (mysql-connector-j-<version>.jar) to the project’s classpath.

    Update database credentials inside DBConnection.java.

    Create MySQL database and tables (users, products, cart, orders, order_items).

    Run MainApplication.java.

    Register as user OR login as admin:

    email: admin@kodekart.com
    password: admin123

📚 Future Enhancements

    Role-based admin authentication

    Password encryption (BCrypt)

    Product ratings & reviews

    Wishlist / favorites feature

    Logging & exception handling improvements