# Enhanced Features Implementation Guide

## Overview
This document outlines the implementation of Google OAuth, PayPal integration, Seller Portal, and additional features.

## ✅ Completed (Entities & Repositories Created)

### 1. **Seller Portal Entities** (7 entities)
- `Seller` - Main seller entity with store information
- `SellerProduct` - Seller's product listings
- `SellerOrder` - Order tracking per seller
- `SellerOrderItem` - Order line items
- `SellerTier` - BASIC, SILVER, GOLD, PLATINUM, ENTERPRISE
- `SellerStatus` - PENDING, ACTIVE, SUSPENDED, REJECTED, INACTIVE
- `SellerOrderStatus` - Order workflow status

### 2. **Google OAuth Entities**
- `OAuth2UserEntity` - OAuth2 user information

### 3. **PayPal Integration Entities**
- `PayPalTransaction` - PayPal payment tracking

### 4. **Shipment Tracking Entities**
- `Shipment` - Order shipment tracking
- `ShipmentStatus` - PENDING, IN_TRANSIT, DELIVERED, etc.

### 5. **Email Service Entities**
- `EmailLog` - Email sending logs
- `EmailStatus` - PENDING, SENT, FAILED, BOUNCED

### 6. **Repositories Created** (7 repositories)
- `SellerRepository`
- `SellerProductRepository`
- `SellerOrderRepository`
- `ShipmentRepository`
- `OAuth2UserRepository`
- `PayPalTransactionRepository`
- `EmailLogRepository`

### 7. **Database Migration**
- `V5__enhanced_features.sql` - Complete schema for all new features

### 8. **Configuration Updated**
- `pom.xml` - Added OAuth2, PayPal, Mail, Apache POI dependencies
- `application.yml` - Added OAuth2, PayPal, Email configuration

## 📋 Next Steps for Complete Implementation

### A. **Google OAuth2 Implementation**

#### Create these files:
1. **Services:**
   - `OAuth2UserService.java` - Interface
   - `OAuth2UserServiceImpl.java` - Implementation
   - `CustomOAuth2UserService.java` - Custom user handling

2. **DTOs:**
   - `OAuth2UserInfo.java` - User info from OAuth provider
   - `GoogleOAuth2Response.java` - Google response DTO

3. **Controllers:**
   - `AuthController.java` - Add OAuth2 endpoints

4. **Configuration:**
   - `OAuth2Config.java` - OAuth2 configuration class

#### Example OAuth2 Service:
```java
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    
    private final OAuth2UserRepository oauth2UserRepository;
    private final UserRepository userRepository;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = userRequest.loadUser();
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oauth2User.getAttribute("sub");
        
        return oauth2UserRepository.findByProviderAndProviderId(provider, providerId)
            .orElseGet(() -> registerNewUser(userRequest, oauth2User));
    }
}
```

### B. **PayPal Integration**

#### Create these files:
1. **Services:**
   - `PayPalService.java` - Interface
   - `PayPalServiceImpl.java` - Implementation

2. **DTOs:**
   - `PayPalOrderRequest.java`
   - `PayPalOrderResponse.java`
   - `PayPalCaptureResponse.java`

3. **Controllers:**
   - `PaymentController.java` - PayPal payment endpoints

#### PayPal Service Example:
```java
@Service
@RequiredArgsConstructor
public class PayPalServiceImpl implements PayPalService {
    
    @Value("${paypal.client-id}")
    private String clientId;
    
    @Value("${paypal.client-secret}")
    private String clientSecret;
    
    @Value("${paypal.mode}")
    private String mode;
    
    public PayPalOrder createOrder(BigDecimal amount, String currency) {
        // Create PayPal order using SDK
    }
    
    public PayPalCapture captureOrder(String orderId) {
        // Capture payment
    }
}
```

### C. **Seller Portal Services**

#### Create these files:
1. **Services:**
   - `SellerService.java` - Interface
   - `SellerServiceImpl.java` - Implementation
   - `SellerDashboardService.java` - Seller analytics

2. **DTOs:** (15+ DTOs)
   - `SellerDTO.java`
   - `CreateSellerRequest.java`
   - `SellerProductDTO.java`
   - `SellerOrderDTO.java`
   - `SellerDashboardDTO.java`
   - `SellerAnalyticsDTO.java`

3. **Controllers:**
   - `SellerController.java` - Seller management
   - `SellerDashboardController.java` - Seller analytics

### D. **Email Service**

#### Create these files:
1. **Services:**
   - `EmailService.java` - Interface
   - `EmailServiceImpl.java` - Implementation

2. **Templates:**
   - Email templates for order confirmation, password reset, etc.

#### Email Service Example:
```java
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    
    private final JavaMailSender mailSender;
    private final EmailLogRepository emailLogRepository;
    
    @Value("${app.email.from}")
    private String fromEmail;
    
    @Override
    public void sendEmail(String to, String subject, String body) {
        // Send email and log
    }
    
    @Override
    public void sendOrderConfirmation(Order order) {
        // Send order confirmation email
    }
}
```

### E. **Shipment Tracking Service**

#### Create these files:
1. **Services:**
   - `ShipmentService.java`
   - `ShipmentServiceImpl.java`

2. **DTOs:**
   - `ShipmentDTO.java`
   - `CreateShipmentRequest.java`
   - `UpdateShipmentRequest.java`

3. **Controllers:**
   - `ShipmentController.java`

### F. **Product Import/Export**

#### Create these files:
1. **Services:**
   - `ProductImportExportService.java`

2. **Utils:**
   - `ExcelHelper.java` - Excel file handling

3. **Controllers:**
   - Add endpoints to `ProductController` or create `ImportExportController`

## 🔧 Configuration Required

### 1. **Google OAuth2 Setup**
```yaml
# Get credentials from: https://console.cloud.google.com/
GOOGLE_CLIENT_ID=your-client-id.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=your-client-secret
GOOGLE_REDIRECT_URI=http://localhost:8080/api/v1/auth/oauth2/callback
```

### 2. **PayPal Setup**
```yaml
# Get credentials from: https://developer.paypal.com/
PAYPAL_CLIENT_ID=your-paypal-client-id
PAYPAL_CLIENT_SECRET=your-paypal-client-secret
PAYPAL_MODE=sandbox  # or 'live' for production
```

### 3. **Email Setup**
```yaml
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password
```

## 📊 API Endpoints to Add

### OAuth2 Endpoints
```
GET  /auth/oauth2/authorization/google
GET  /auth/oauth2/callback/google
POST /auth/register/oauth2
```

### PayPal Endpoints
```
POST /payments/paypal/create-order
POST /payments/paypal/capture-order
POST /payments/paypal/refund
GET  /payments/paypal/transaction/{id}
```

### Seller Endpoints
```
POST   /sellers/register
GET    /sellers/{id}
PUT    /sellers/{id}
GET    /sellers/my-products
POST   /sellers/products
PUT    /sellers/products/{id}
GET    /sellers/orders
GET    /sellers/dashboard
GET    /sellers/analytics
```

### Shipment Endpoints
```
POST   /shipments
GET    /shipments/{id}
GET    /shipments/track/{trackingNumber}
PUT    /shipments/{id}/status
GET    /orders/{orderId}/shipment
```

### Email Endpoints (Admin)
```
POST   /admin/email/send
GET    /admin/email/logs
GET    /admin/email/templates
```

## 🎯 Implementation Priority

1. **High Priority:**
   - Google OAuth2 authentication
   - Seller registration and onboarding
   - Basic email service

2. **Medium Priority:**
   - PayPal payment integration
   - Shipment tracking
   - Seller dashboard

3. **Lower Priority:**
   - Product import/export
   - Advanced analytics
   - Email templates

## 📁 Files Created Summary

- **13 Entity classes**
- **7 Repository interfaces**
- **1 Database migration** (V5)
- **Configuration updates** (pom.xml, application.yml)

## 🚀 Quick Start

1. Set up Google OAuth2 credentials
2. Set up PayPal developer account
3. Configure email SMTP settings
4. Run application (Flyway will apply V5 migration)
5. Implement services following the examples above

## 📝 Notes

- All entities follow the existing project patterns
- Repositories use Spring Data JPA conventions
- Configuration uses environment variables for security
- Database migration is idempotent and safe to run

## 🔗 Useful Links

- Google OAuth2: https://developers.google.com/identity/protocols/oauth2
- PayPal SDK: https://developer.paypal.com/docs/checkout/
- Spring OAuth2: https://docs.spring.io/spring-security/reference/servlet/oauth2/
- JavaMail: https://javaee.github.io/javamail/
