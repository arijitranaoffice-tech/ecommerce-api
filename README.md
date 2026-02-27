# E-commerce REST API

Enterprise-grade e-commerce REST API built with Spring Boot 3.2 and PostgreSQL 16.

## Features

- 🔐 **Authentication & Authorization** - JWT-based authentication with role-based access control
- 📦 **Product Management** - Full CRUD operations for products with categories
- 🛒 **Shopping Cart** - Add, update, and remove items from cart
- 📋 **Order Management** - Complete order lifecycle from creation to delivery
- 👥 **User Management** - User registration, profile management, and addresses
- ⭐ **Product Reviews** - Customer reviews and ratings
- 📊 **Admin Dashboard** - Admin endpoints for managing the store
- 🔍 **Search & Filter** - Product search and filtering capabilities
- 📝 **API Documentation** - OpenAPI/Swagger documentation

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

## Support

For issues and questions, please create an issue in the GitHub repository.
