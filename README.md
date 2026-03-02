# E-commerce REST API

Enterprise-grade e-commerce REST API built with Spring Boot 3.2 and PostgreSQL 16.

## Features

- 🔐 **Authentication & Authorization** - JWT-based authentication with role-based access control
- 📦 **Product Management** - Full CRUD operations for products with categories
- 🛒 **Shopping Cart** - Add, update, and remove items from cart
- 📋 **Order Management** - Complete order lifecycle from creation to delivery
- 👥 **User Management** - User registration, profile management, and addresses
- ⭐ **Product Reviews** - Customer reviews and ratings
- 🔍 **Search & Filter** - Product search and filtering capabilities
- 📝 **API Documentation** - OpenAPI/Swagger documentation
- 🏢 **Distributor Portal** - Complete B2B distribution management system
- 👨‍💼 **Admin Portal** - Comprehensive administration and analytics dashboard
- 🛍️ **User Portal** - Enhanced customer experience with wishlist and notifications

### Distributor Portal Features

- **Distributor Management** - Registration, approval workflow, territory assignment
- **Product Distribution** - Product assignments, bulk pricing, volume discounts
- **Order Management** - Distributor-specific orders, fulfillment tracking, shipping
- **Commission System** - Tier-based commissions, payout tracking, performance bonuses
- **Inventory Management** - Multi-warehouse stock, transfers, reorder points
- **Customer Management** - B2B customer relationships, credit limits, pricing tiers
- **Analytics Dashboard** - Sales metrics, inventory reports, commission tracking
- **Warehouse Management** - Multiple warehouses, stock locations, bin management

### Admin Portal Features

- **Dashboard Analytics** - Real-time overview of sales, orders, products, and customers
- **Sales Analytics** - Daily, weekly, monthly, and yearly sales tracking with trends
- **Product Analytics** - Inventory status, stock levels, category performance
- **Customer Analytics** - Customer count, retention rates, lifetime value
- **Order Analytics** - Order status breakdown, fulfillment rates, cancellation tracking
- **Banner Management** - Homepage and promotional banners with impression/click tracking
- **Coupon System** - Discount codes with percentage/fixed amounts, usage limits, validity periods
- **Activity Logs** - Complete audit trail of all admin actions
- **Reports Generation** - Sales reports, inventory reports, customer reports

### User Portal Features

- **Wishlist** - Save favorite products, prioritize items, quick add to cart
- **Notifications** - Order updates, shipping notifications, promotions, system messages
- **Payment Methods** - Save and manage multiple payment options, set default payment
- **Coupon Management** - Browse available coupons, validate and apply discount codes
- **Order Tracking** - Real-time order status updates
- **Product Reviews** - Rate and review purchased products

## Tech Stack

- **Java 21**
- **Spring Boot 3.2.3**
- **Spring Security** with JWT
- **Spring Data JPA**
- **PostgreSQL 16**
- **Flyway** for database migrations
- **MapStruct** for DTO mapping
- **Lombok** for reducing boilerplate
- **Maven** for dependency management

## Prerequisites

- Java 21 or higher
- PostgreSQL 16
- Maven 3.8+

## Database Setup

1. Create the PostgreSQL database:

```bash
createdb ecommerce_db
```

2. Update database credentials in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ecommerce_db
    username: postgres
    password: your_password
```

## Running the Application

1. Clone the repository:

```bash
git clone <repository-url>
cd ecommerce-api
```

2. Build the application:

```bash
mvn clean install
```

3. Run the application:

```bash
mvn spring-boot:run
```

4. Access the API:
   - **Base URL**: `http://localhost:8080/api/v1`
   - **Swagger UI**: `http://localhost:8080/api/v1/swagger-ui.html`
   - **API Docs**: `http://localhost:8080/api/v1/api-docs`

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | User login |
| POST | `/auth/refresh` | Refresh JWT token |
| POST | `/auth/logout` | User logout |

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/products` | Get all products |
| GET | `/products/{id}` | Get product by ID |
| GET | `/products/search` | Search products |
| POST | `/products` | Create product (Admin) |
| PUT | `/products/{id}` | Update product (Admin) |
| DELETE | `/products/{id}` | Delete product (Admin) |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/orders` | Get all orders (Admin) |
| GET | `/orders/{id}` | Get order by ID |
| GET | `/orders/my-orders` | Get user's orders |
| POST | `/orders` | Create new order |
| PATCH | `/orders/{id}/status` | Update order status (Admin) |
| POST | `/orders/{id}/cancel` | Cancel order |

### Cart
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/cart` | Get user's cart |
| POST | `/cart/items` | Add item to cart |
| PUT | `/cart/items/{id}` | Update cart item |
| DELETE | `/cart/items/{id}` | Remove item from cart |
| DELETE | `/cart/clear` | Clear cart |

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users/me` | Get current user |
| GET | `/users/{id}` | Get user by ID |
| PUT | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Delete user |

### Addresses
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/addresses/user/{userId}` | Get user's addresses |
| GET | `/addresses/{id}` | Get address by ID |
| POST | `/addresses` | Create address |
| PUT | `/addresses/{id}` | Update address |
| DELETE | `/addresses/{id}` | Delete address |
| PATCH | `/addresses/{id}/set-default` | Set default address |

### Distributors
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/distributors` | Create new distributor (Admin) |
| GET | `/distributors` | Get all distributors (Admin) |
| GET | `/distributors/{id}` | Get distributor by ID |
| GET | `/distributors/user/{userId}` | Get distributor by user ID |
| PUT | `/distributors/{id}` | Update distributor |
| POST | `/distributors/{id}/activate` | Activate distributor (Admin) |
| POST | `/distributors/{id}/deactivate` | Deactivate distributor (Admin) |
| DELETE | `/distributors/{id}` | Delete distributor (Admin) |
| POST | `/distributors/{distributorId}/products/{productId}` | Assign product to distributor (Admin) |
| GET | `/distributors/{distributorId}/products` | Get distributor products |
| PUT | `/distributors/products/{id}` | Update distributor product |
| DELETE | `/distributors/{distributorId}/products/{productId}` | Remove product from distributor (Admin) |
| POST | `/distributors/{distributorId}/orders` | Create distributor order |
| GET | `/distributors/{distributorId}/orders` | Get distributor orders |
| GET | `/distributors/orders/{id}` | Get distributor order by ID |
| POST | `/distributors/orders/{id}/approve` | Approve distributor order (Admin) |
| POST | `/distributors/orders/{id}/reject` | Reject distributor order (Admin) |
| POST | `/distributors/orders/{id}/ship` | Ship distributor order (Admin) |
| POST | `/distributors/orders/{id}/deliver` | Deliver distributor order (Admin) |
| POST | `/distributors/orders/{id}/cancel` | Cancel distributor order |
| GET | `/distributors/{distributorId}/commissions` | Get distributor commissions |
| POST | `/distributors/{distributorId}/commissions/calculate` | Calculate commission (Admin) |
| POST | `/distributors/commissions/{id}/pay` | Pay commission (Admin) |
| GET | `/distributors/{distributorId}/inventory` | Get distributor inventory |
| PUT | `/distributors/inventory/{id}` | Update distributor inventory |
| POST | `/distributors/{distributorId}/customers` | Add distributor customer |
| GET | `/distributors/{distributorId}/customers` | Get distributor customers |
| PUT | `/distributors/customers/{id}` | Update distributor customer |
| DELETE | `/distributors/{distributorId}/customers/{customerId}` | Remove distributor customer |
| POST | `/distributors/{distributorId}/warehouses` | Create warehouse |
| GET | `/distributors/{distributorId}/warehouses` | Get distributor warehouses |
| PUT | `/distributors/warehouses/{id}` | Update warehouse |
| POST | `/distributors/inventory/transfer` | Transfer inventory |

### Distributor Dashboard
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/distributors/dashboard/{distributorId}` | Get dashboard data |
| GET | `/distributors/dashboard/{distributorId}/summary` | Get distributor summary |
| GET | `/distributors/dashboard/{distributorId}/sales-metrics` | Get sales metrics |
| GET | `/distributors/dashboard/{distributorId}/inventory-metrics` | Get inventory metrics |
| GET | `/distributors/dashboard/{distributorId}/commission-metrics` | Get commission metrics |
| GET | `/distributors/dashboard/{distributorId}/recent-orders` | Get recent orders |
| GET | `/distributors/dashboard/{distributorId}/top-products` | Get top products |
| GET | `/distributors/dashboard/{distributorId}/low-stock` | Get low stock products |

### Admin Portal
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/dashboard` | Get complete dashboard data |
| GET | `/admin/dashboard/overview` | Get overview statistics |
| GET | `/admin/dashboard/sales` | Get sales analytics |
| GET | `/admin/dashboard/products` | Get product analytics |
| GET | `/admin/dashboard/customers` | Get customer analytics |
| GET | `/admin/dashboard/orders` | Get order analytics |
| POST | `/admin/banners` | Create banner |
| PUT | `/admin/banners/{id}` | Update banner |
| DELETE | `/admin/banners/{id}` | Delete banner |
| GET | `/admin/banners` | Get all banners |
| GET | `/admin/banners/{id}` | Get banner by ID |
| GET | `/admin/banners/active` | Get active banners |
| POST | `/admin/banners/{id}/impression` | Record banner impression |
| POST | `/admin/banners/{id}/click` | Record banner click |
| POST | `/admin/coupons` | Create coupon |
| PUT | `/admin/coupons/{id}` | Update coupon |
| DELETE | `/admin/coupons/{id}` | Delete coupon |
| GET | `/admin/coupons` | Get all coupons |
| GET | `/admin/coupons/{id}` | Get coupon by ID |
| POST | `/admin/coupons/deactivate-expired` | Deactivate expired coupons |
| GET | `/admin/activity-logs` | Get activity logs |
| GET | `/admin/activity-logs/user/{userId}` | Get user activity logs |
| GET | `/admin/activity-logs/range` | Get logs by date range |
| POST | `/admin/activity-logs` | Log admin activity |
| GET | `/admin/reports/sales` | Generate sales report |
| GET | `/admin/reports/inventory` | Generate inventory report |
| GET | `/admin/reports/customers` | Generate customer report |

### User Portal
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/user/wishlist` | Get user's wishlist |
| POST | `/user/wishlist/add` | Add product to wishlist |
| DELETE | `/user/wishlist/remove` | Remove product from wishlist |
| DELETE | `/user/wishlist/clear` | Clear entire wishlist |
| GET | `/user/notifications` | Get user notifications |
| GET | `/user/notifications/unread` | Get unread notifications |
| GET | `/user/notifications/unread-count` | Get unread count |
| POST | `/user/notifications/{id}/read` | Mark notification as read |
| POST | `/user/notifications/read-all` | Mark all as read |
| POST | `/user/payment-methods` | Add payment method |
| PUT | `/user/payment-methods/{id}` | Update payment method |
| DELETE | `/user/payment-methods/{id}` | Remove payment method |
| GET | `/user/payment-methods` | Get all payment methods |
| GET | `/user/payment-methods/default` | Get default payment method |
| POST | `/user/payment-methods/{id}/set-default` | Set default payment method |
| POST | `/user/coupons/validate` | Validate coupon code |
| GET | `/user/coupons/available` | Get available coupons |

## Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_HOST` | Database host | `localhost` |
| `DB_PORT` | Database port | `5432` |
| `DB_NAME` | Database name | `ecommerce_db` |
| `DB_USERNAME` | Database username | `postgres` |
| `DB_PASSWORD` | Database password | `postgres` |
| `JWT_SECRET` | JWT secret key | (generate your own) |

### Profiles

- `dev` - Development profile with debug logging
- `prod` - Production profile with optimized settings

Run with a specific profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Security

The API uses JWT (JSON Web Tokens) for authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

### User Roles

- `CUSTOMER` - Regular customer with shopping capabilities
- `ADMIN` - Administrator with full access to all endpoints
- `SELLER` - Seller with product management access
- `SUPPORT` - Support staff with limited access
- `DISTRIBUTOR` - Distribution partner with B2B ordering and management capabilities

## Testing

Run tests with:

```bash
mvn test
```

## Project Structure

```
ecommerce-api/
├── src/main/java/com/ecommerce/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST controllers
│   ├── dto/             # Data Transfer Objects
│   ├── entity/          # JPA entities
│   ├── exception/       # Exception handling
│   ├── mapper/          # MapStruct mappers
│   ├── repository/      # Spring Data repositories
│   ├── security/        # Security components
│   └── service/         # Business logic
├── src/main/resources/
│   ├── db/migration/    # Flyway migrations
│   └── application.yml  # Application configuration
└── pom.xml              # Maven configuration
```

## License

This project is licensed under the MIT License.

## Project Statistics

- **Total Entities**: 32+
- **Total DTOs**: 50+
- **Total Repositories**: 23
- **Total API Endpoints**: 100+
- **Database Tables**: 30+
- **Flyway Migrations**: 4

## Recent Updates

### v1.1.0 - Admin & User Portal (Latest)
- ✅ Admin Dashboard with comprehensive analytics
- ✅ Banner Management for promotions
- ✅ Coupon/Discount System
- ✅ Admin Activity Logs for audit trail
- ✅ Report Generation (Sales, Inventory, Customers)
- ✅ User Wishlist functionality
- ✅ Notification System
- ✅ Payment Method Management
- ✅ Enhanced User Portal experience

### v1.0.0 - Distributor Portal
- ✅ Complete B2B distribution management
- ✅ Multi-tier distributor system
- ✅ Commission tracking
- ✅ Multi-warehouse inventory
- ✅ Distributor analytics dashboard

## Support

For issues and questions, please create an issue in the GitHub repository.
