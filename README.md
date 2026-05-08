# SalesSavvy E-Commerce Platform

**SalesSavvy** is a modern, full-stack E-Commerce web application that provides a comprehensive and secure shopping experience. It features separate portals for both customers and administrators, with secure authentication, payment gateway integration, and efficient product and order management.

## 🚀 Features

### For Customers:
*   **User Authentication**: Secure Registration and Login with OTP (One-Time Password) email verification.
*   **Password Management**: Easy to use Forgot & Reset Password functionality.
*   **Product Browsing**: View products with detailed descriptions and images, categorized for easy navigation.
*   **Shopping Cart**: Add, remove, and manage items in the shopping cart.
*   **Order Management**: Place orders securely and view past order history.
*   **Secure Payments**: Integrated with Razorpay for seamless and secure checkout experiences.

### For Administrators:
*   **Admin Dashboard**: A dedicated interface for managing the platform.
*   **Secure Admin Access**: Restricted login for administrative users.
*   **Product & Category Management**: Add, update, or remove products and categories.
*   **Order Tracking**: View and manage customer orders across the platform.

## 🛠️ Technology Stack

### Frontend
*   **Framework**: React.js (Bootstrapped with Vite)
*   **Routing**: React Router DOM
*   **Styling**: CSS (Custom styling for an intuitive UI)
*   **Build Tool**: Vite
*   **Linter**: ESLint

### Backend
*   **Framework**: Spring Boot (Java 17)
*   **Database**: MySQL
*   **ORM**: Hibernate / Spring Data JPA
*   **Security**: Spring Security combined with JWT (JSON Web Tokens) for stateless authentication.
*   **Email Service**: Spring Boot Mail (Configured for Gmail SMTP to handle OTPs and alerts).
*   **Payments Integration**: Razorpay Java SDK

## 📁 Project Structure

The repository contains the following main components organized into a structured layout:

```text
SalesSavvyProject/
├── salessavy_Frontend/        # React + Vite Frontend
│   ├── public/                # Static assets
│   ├── src/                   # React components, pages, and context
│   ├── package.json           # Frontend dependencies
│   └── vite.config.js         # Vite configuration
├── salessavy_Backend/         # Spring Boot Backend
│   └── salessavvy/
│       ├── src/               # Java source code and resources
│       │   └── main/
│       │       ├── java/      # Controllers, Services, Models, Repositories
│       │       └── resources/ # application.properties
│       ├── pom.xml            # Maven dependencies
│       └── mvnw               # Maven wrapper
└── SalesSavyProjectDB.sql     # Database schema & sample data
```

## ⚙️ Prerequisites

Before you begin, ensure you have met the following requirements:
*   **Node.js** (v18+ recommended)
*   **Java 17** Development Kit (JDK)
*   **MySQL Server** running locally or remotely
*   **Maven** (Optional, the project includes a `mvnw` wrapper)

## 💻 Installation & Setup

### 1. Database Setup
1. Open your MySQL server and create a new database named `salessavvy`.
2. Import the provided database dump file `SalesSavyProjectDB.sql` located in the root of the project to set up the necessary tables and initial data.
3. Navigate to the backend resources folder: `salessavy_Backend/salessavvy/src/main/resources/application.properties`.
4. Update the database credentials to match your local setup:
   ```properties
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   ```

### 2. Backend Configuration & Startup
1. **Email Service Configuration**:
   The app uses Gmail to send OTPs. You will need an App Password from your Google Account. Update the `application.properties`:
   ```properties
   spring.mail.username=your_email@gmail.com
   spring.mail.password=your_app_password
   ```
2. **Payment Gateway (Razorpay)**:
   Add your Razorpay Test keys to `application.properties`:
   ```properties
   razorpay.key_id=your_razorpay_key_id
   razorpay.key_secret=your_razorpay_key_secret
   ```
3. **Run the Backend**:
   Open a terminal, navigate to the backend directory, and start the Spring Boot app:
   ```bash
   cd salessavy_Backend/salessavvy
   ./mvnw spring-boot:run
   ```
   *The backend server will run on `http://localhost:9090`.*

### 3. Frontend Setup & Startup

**To create the React project from scratch (if starting anew):**
```bash
# CREATE REACT PROJECT 
npm install -g create-vite  
create-vite salessavy_Frontend --template react  
```

**If you are setting up the existing project:**
1. Open a new terminal and navigate to the frontend directory:
   ```bash
   cd salessavy_Frontend
   ```
2. Install the required npm packages:
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```
   *The frontend application will be available at `http://localhost:5173`.*

## 📖 Usage

1. Open `http://localhost:5173` in your web browser.
2. **Customer Login / Flow**: Customers can access the application, log in, or register directly from the **home page**. Verify your account using the OTP sent to your configured email address. Once logged in, you can browse products, add them to your cart, and simulate a checkout process using Razorpay.
3. **Admin Login / Flow**: To log in as an administrator, you need to append `/admin` to the home page URL (e.g., `http://localhost:5173/admin`). This will navigate you to the dedicated Admin Login and Dashboard for managing the store's inventory and orders.

## 🤝 Contributing
Contributions, issues, and feature requests are welcome! Feel free to check the issues page if you want to contribute.

## 📝 License
This project is open-source and available under the [MIT License](LICENSE).
