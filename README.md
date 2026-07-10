# MyShoppy

MyShoppy is a full-featured Java Web Application built for E-commerce. It allows users to browse products, add them to their cart, securely checkout, and track their order history. It also features an Admin interface to manage products, categories, and orders.

## Features

*   **User Authentication**: Register, Login, and Logout functionality for users.
*   **Product Catalog**: Browse and view detailed information about available products.
*   **Shopping Cart**: Add, update, and remove items from the shopping cart.
*   **Checkout & Orders**: Secure checkout process and a page to view past order history.
*   **Admin Dashboard**: Dedicated admin interface to add new products, manage existing products, and view all customer orders.
*   **Database Integration**: Robust data management using MySQL to store users, products, categories, carts, and orders.

## Technologies Used

*   **Backend**: Java (JDK 21), Jakarta Servlets, JSTL
*   **Frontend**: JSP (JavaServer Pages), HTML, CSS
*   **Database**: MySQL
*   **Build Tool**: Maven (WAR packaging)
*   **Server**: Apache Tomcat (or any Jakarta EE compliant container)

## Prerequisites

Before you begin, ensure you have met the following requirements:

*   **Java Development Kit (JDK)**: Version 21 or later.
*   **Maven**: Installed and configured in your system.
*   **MySQL Server**: Running instance for the database.
*   **Apache Tomcat**: Version 10+ (for Jakarta EE support).

## Setup & Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Tonysunil/Shoppy.git
    cd MyShoppy
    ```

2.  **Database Configuration:**
    *   Create a MySQL database named `ecommerce_db`.
    *   Update the database credentials in `src/main/java/ecommerce/util/DBConnection.java`:
        ```java
        private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db";
        private static final String USER = "root";
        private static final String PASS = "your_mysql_password";
        ```
    *   *Note: You may need to create the required tables (users, products, categories, orders, cart) in your database before running the application. Check the `ecommerce/dao` classes for table structures.*

3.  **Build the Project:**
    ```bash
    mvn clean install
    ```

4.  **Deploy:**
    *   Deploy the generated `MyShoppy-0.0.1-SNAPSHOT.war` (located in the `target` directory) to your Apache Tomcat's `webapps` folder.
    *   Start Tomcat.

5.  **Access the Application:**
    *   Open your browser and navigate to `http://localhost:8080/MyShoppy-0.0.1-SNAPSHOT/` (URL may vary depending on your Tomcat configuration and deployment context).

## Project Structure

*   `src/main/java/ecommerce/dao`: Data Access Object classes for database interactions.
*   `src/main/java/ecommerce/model`: Java POJO classes representing database entities (User, Product, Order, etc.).
*   `src/main/java/ecommerce/servlet`: Jakarta Servlets handling HTTP requests and business logic.
*   `src/main/java/ecommerce/util`: Utility classes, primarily for database connection.
*   `src/main/webapp`: JSP files, CSS, images, and `web.xml` configuration.
