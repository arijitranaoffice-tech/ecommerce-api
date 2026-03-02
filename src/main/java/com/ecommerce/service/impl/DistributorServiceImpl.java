package com.ecommerce.service.impl;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.DistributorMapper;
import com.ecommerce.repository.*;
import com.ecommerce.service.DistributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of DistributorService.
 */
@Service
@RequiredArgsConstructor
public class DistributorServiceImpl implements DistributorService {

    private final DistributorRepository distributorRepository;
    private final DistributorProductRepository distributorProductRepository;
    private final DistributorOrderRepository distributorOrderRepository;
    private final DistributorCommissionRepository distributorCommissionRepository;
    private final DistributorInventoryRepository distributorInventoryRepository;
    private final DistributorCustomerRepository distributorCustomerRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryTransferRepository inventoryTransferRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final DistributorMapper distributorMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public DistributorDTO createDistributor(CreateDistributorRequest request) {
        if (distributorRepository.existsByUserEmail(request.getEmail())) {
            throw new RuntimeException("User with this email is already a distributor");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .role(UserRole.DISTRIBUTOR)
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(user);

        String distributorCode = "DIST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Distributor distributor = Distributor.builder()
                .user(user)
                .distributorCode(distributorCode)
                .companyName(request.getCompanyName())
                .businessLicenseNumber(request.getBusinessLicenseNumber())
                .taxId(request.getTaxId())
                .businessAddress(request.getBusinessAddress())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .commissionRate(request.getCommissionRate())
                .tier(request.getTier())
                .status(DistributorStatus.PENDING)
                .creditLimit(request.getCreditLimit() != null ? request.getCreditLimit() : BigDecimal.valueOf(10000))
                .currentBalance(BigDecimal.ZERO)
                .contractStartDate(request.getContractStartDate())
                .contractEndDate(request.getContractEndDate())
                .territoryDescription(request.getTerritoryDescription())
                .territories(request.getTerritories() != null ? request.getTerritories() : List.of())
                .build();

        distributor = distributorRepository.save(distributor);
        return distributorMapper.toDTO(distributor);
    }

    @Override
    @Transactional
    public DistributorDTO updateDistributor(UUID distributorId, UpdateDistributorRequest request) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor", distributorId));

        if (request.getCompanyName() != null) {
            distributor.setCompanyName(request.getCompanyName());
        }
        if (request.getBusinessLicenseNumber() != null) {
            distributor.setBusinessLicenseNumber(request.getBusinessLicenseNumber());
        }
        if (request.getTaxId() != null) {
            distributor.setTaxId(request.getTaxId());
        }
        if (request.getBusinessAddress() != null) {
            distributor.setBusinessAddress(request.getBusinessAddress());
        }
        if (request.getCity() != null) {
            distributor.setCity(request.getCity());
        }
        if (request.getState() != null) {
            distributor.setState(request.getState());
        }
        if (request.getPostalCode() != null) {
            distributor.setPostalCode(request.getPostalCode());
        }
        if (request.getCountry() != null) {
            distributor.setCountry(request.getCountry());
        }
        if (request.getPhone() != null) {
            distributor.getUser().setPhone(request.getPhone());
        }
        if (request.getCommissionRate() != null) {
            distributor.setCommissionRate(request.getCommissionRate());
        }
        if (request.getTier() != null) {
            distributor.setTier(request.getTier());
        }
        if (request.getStatus() != null) {
            distributor.setStatus(request.getStatus());
        }
        if (request.getCreditLimit() != null) {
            distributor.setCreditLimit(request.getCreditLimit());
        }
        if (request.getContractStartDate() != null) {
            distributor.setContractStartDate(request.getContractStartDate());
        }
        if (request.getContractEndDate() != null) {
            distributor.setContractEndDate(request.getContractEndDate());
        }
        if (request.getTerritoryDescription() != null) {
            distributor.setTerritoryDescription(request.getTerritoryDescription());
        }
        if (request.getTerritories() != null) {
            distributor.setTerritories(request.getTerritories());
        }

        userRepository.save(distributor.getUser());
        distributor = distributorRepository.save(distributor);
        return distributorMapper.toDTO(distributor);
    }

    @Override
    public DistributorDTO getDistributorById(UUID distributorId) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor", distributorId));
        return distributorMapper.toDTO(distributor);
    }

    @Override
    public DistributorDTO getDistributorByUserId(UUID userId) {
        Distributor distributor = distributorRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor for user", userId));
        return distributorMapper.toDTO(distributor);
    }

    @Override
    public DistributorDTO getDistributorByEmail(String email) {
        Distributor distributor = distributorRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor with email", email));
        return distributorMapper.toDTO(distributor);
    }

    @Override
    public Page<DistributorDTO> getAllDistributors(Pageable pageable) {
        return distributorRepository.findAll(pageable).map(distributorMapper::toDTO);
    }

    @Override
    public Page<DistributorDTO> searchDistributors(String keyword, Pageable pageable) {
        return distributorRepository.searchDistributors(keyword, pageable).map(distributorMapper::toDTO);
    }

    @Override
    @Transactional
    public void deactivateDistributor(UUID distributorId) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor", distributorId));
        distributor.setStatus(DistributorStatus.INACTIVE);
        distributorRepository.save(distributor);
    }

    @Override
    @Transactional
    public void activateDistributor(UUID distributorId) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor", distributorId));
        distributor.setStatus(DistributorStatus.ACTIVE);
        distributorRepository.save(distributor);
    }

    @Override
    @Transactional
    public void deleteDistributor(UUID distributorId) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor", distributorId));
        distributorRepository.delete(distributor);
    }

    @Override
    @Transactional
    public DistributorProductDTO assignProductToDistributor(UUID distributorId, UUID productId, DistributorProductRequest request) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor", distributorId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        if (distributorProductRepository.existsByDistributorIdAndProductId(distributorId, productId)) {
            throw new RuntimeException("Product is already assigned to this distributor");
        }

        DistributorProduct distributorProduct = DistributorProduct.builder()
                .distributor(distributor)
                .product(product)
                .distributorPrice(request.getDistributorPrice())
                .minimumOrderQuantity(request.getMinimumOrderQuantity())
                .maximumOrderQuantity(request.getMaximumOrderQuantity())
                .stockAllocation(request.getStockAllocation())
                .reservedStock(request.getReservedStock())
                .active(request.getActive())
                .validFrom(request.getValidFrom())
                .validUntil(request.getValidUntil())
                .notes(request.getNotes())
                .tier1Discount(request.getTier1Discount())
                .tier2Discount(request.getTier2Discount())
                .tier3Discount(request.getTier3Discount())
                .tier1Threshold(request.getTier1Threshold())
                .tier2Threshold(request.getTier2Threshold())
                .tier3Threshold(request.getTier3Threshold())
                .build();

        distributorProduct = distributorProductRepository.save(distributorProduct);
        return distributorMapper.toProductDTO(distributorProduct);
    }

    @Override
    @Transactional
    public DistributorProductDTO updateDistributorProduct(UUID distributorProductId, DistributorProductRequest request) {
        DistributorProduct distributorProduct = distributorProductRepository.findById(distributorProductId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorProduct", distributorProductId));

        if (request.getDistributorPrice() != null) {
            distributorProduct.setDistributorPrice(request.getDistributorPrice());
        }
        if (request.getMinimumOrderQuantity() != null) {
            distributorProduct.setMinimumOrderQuantity(request.getMinimumOrderQuantity());
        }
        if (request.getMaximumOrderQuantity() != null) {
            distributorProduct.setMaximumOrderQuantity(request.getMaximumOrderQuantity());
        }
        if (request.getStockAllocation() != null) {
            distributorProduct.setStockAllocation(request.getStockAllocation());
        }
        if (request.getReservedStock() != null) {
            distributorProduct.setReservedStock(request.getReservedStock());
        }
        if (request.getActive() != null) {
            distributorProduct.setActive(request.getActive());
        }
        if (request.getValidFrom() != null) {
            distributorProduct.setValidFrom(request.getValidFrom());
        }
        if (request.getValidUntil() != null) {
            distributorProduct.setValidUntil(request.getValidUntil());
        }
        if (request.getNotes() != null) {
            distributorProduct.setNotes(request.getNotes());
        }

        distributorProduct = distributorProductRepository.save(distributorProduct);
        return distributorMapper.toProductDTO(distributorProduct);
    }

    @Override
    @Transactional
    public void removeProductFromDistributor(UUID distributorId, UUID productId) {
        DistributorProduct distributorProduct = distributorProductRepository
                .findByDistributorIdAndProductId(distributorId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorProduct", productId));
        distributorProductRepository.delete(distributorProduct);
    }

    @Override
    public Page<DistributorProductDTO> getDistributorProducts(UUID distributorId, Pageable pageable) {
        return distributorProductRepository.findByDistributorId(distributorId, pageable)
                .map(distributorMapper::toProductDTO);
    }

    @Override
    @Transactional
    public DistributorOrderDTO createDistributorOrder(UUID distributorId, CreateDistributorOrderRequest request) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor", distributorId));

        String orderNumber = "DO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        DistributorOrder distributorOrder = DistributorOrder.builder()
                .distributor(distributor)
                .distributorOrderNumber(orderNumber)
                .status(DistributorOrderStatus.PENDING)
                .subtotal(BigDecimal.ZERO)
                .taxAmount(BigDecimal.ZERO)
                .shippingAmount(BigDecimal.ZERO)
                .discountAmount(BigDecimal.ZERO)
                .totalAmount(BigDecimal.ZERO)
                .commissionAmount(BigDecimal.ZERO)
                .paymentStatus(PaymentStatus.PENDING)
                .paymentMethod(request.getPaymentMethod())
                .distributorNote(request.getDistributorNote())
                .isFulfillmentOrder(request.isFulfillmentOrder())
                .build();

        if (request.getShippingAddressId() != null) {
            Address shippingAddress = addressRepository.findById(request.getShippingAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address", request.getShippingAddressId()));
            distributorOrder.setShippingAddress(shippingAddress);
        }

        if (request.getBillingAddressId() != null) {
            Address billingAddress = addressRepository.findById(request.getBillingAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address", request.getBillingAddressId()));
            distributorOrder.setBillingAddress(billingAddress);
        }

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", itemRequest.getProductId()));

            DistributorProduct distributorProduct = distributorProductRepository
                    .findActiveByDistributorAndProduct(distributorId, itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not available for this distributor"));

            DistributorOrderItem orderItem = DistributorOrderItem.builder()
                    .product(product)
                    .distributorProduct(distributorProduct)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(distributorProduct.getDistributorPrice())
                    .discountPercent(BigDecimal.ZERO)
                    .taxRate(BigDecimal.ZERO)
                    .totalPrice(distributorProduct.getDistributorPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())))
                    .build();

            distributorOrder.addOrderItem(orderItem);
        }

        distributorOrder.calculateTotals();
        distributorOrder = distributorOrderRepository.save(distributorOrder);
        return distributorMapper.toOrderDTO(distributorOrder);
    }

    @Override
    public DistributorOrderDTO getDistributorOrderById(UUID orderId) {
        DistributorOrder order = distributorOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorOrder", orderId));
        return distributorMapper.toOrderDTO(order);
    }

    @Override
    public Page<DistributorOrderDTO> getDistributorOrders(UUID distributorId, Pageable pageable) {
        return distributorOrderRepository.findByDistributorId(distributorId, pageable)
                .map(distributorMapper::toOrderDTO);
    }

    @Override
    @Transactional
    public DistributorOrderDTO approveDistributorOrder(UUID orderId) {
        DistributorOrder order = distributorOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorOrder", orderId));
        order.setStatus(DistributorOrderStatus.APPROVED);
        order.setApprovedAt(LocalDateTime.now());
        order = distributorOrderRepository.save(order);
        return distributorMapper.toOrderDTO(order);
    }

    @Override
    @Transactional
    public DistributorOrderDTO rejectDistributorOrder(UUID orderId, String reason) {
        DistributorOrder order = distributorOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorOrder", orderId));
        order.setStatus(DistributorOrderStatus.REJECTED);
        order.setRejectionReason(reason);
        order.setCancelledAt(LocalDateTime.now());
        order = distributorOrderRepository.save(order);
        return distributorMapper.toOrderDTO(order);
    }

    @Override
    @Transactional
    public DistributorOrderDTO shipDistributorOrder(UUID orderId, String trackingNumber, String carrier) {
        DistributorOrder order = distributorOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorOrder", orderId));
        order.setStatus(DistributorOrderStatus.SHIPPED);
        order.setShippedAt(LocalDateTime.now());
        order.setTrackingNumber(trackingNumber);
        order.setCarrier(carrier);
        order = distributorOrderRepository.save(order);
        return distributorMapper.toOrderDTO(order);
    }

    @Override
    @Transactional
    public DistributorOrderDTO deliverDistributorOrder(UUID orderId) {
        DistributorOrder order = distributorOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorOrder", orderId));
        order.setStatus(DistributorOrderStatus.DELIVERED);
        order.setDeliveredAt(LocalDateTime.now());
        order = distributorOrderRepository.save(order);
        return distributorMapper.toOrderDTO(order);
    }

    @Override
    @Transactional
    public DistributorOrderDTO cancelDistributorOrder(UUID orderId, String reason) {
        DistributorOrder order = distributorOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorOrder", orderId));
        order.setStatus(DistributorOrderStatus.CANCELLED);
        order.setCancellationReason(reason);
        order.setCancelledAt(LocalDateTime.now());
        order = distributorOrderRepository.save(order);
        return distributorMapper.toOrderDTO(order);
    }

    @Override
    @Transactional
    public DistributorCommissionDTO calculateCommission(UUID distributorId, UUID orderId) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor", distributorId));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        BigDecimal commissionAmount = order.getTotalAmount().multiply(distributor.getCommissionRate().divide(BigDecimal.valueOf(100)));

        DistributorCommission commission = DistributorCommission.builder()
                .distributor(distributor)
                .order(order)
                .amount(commissionAmount)
                .rate(distributor.getCommissionRate())
                .type(CommissionType.SALE)
                .status(CommissionStatus.PENDING)
                .calculatedAt(LocalDateTime.now())
                .build();

        commission = distributorCommissionRepository.save(commission);
        return distributorMapper.toCommissionDTO(commission);
    }

    @Override
    public Page<DistributorCommissionDTO> getDistributorCommissions(UUID distributorId, Pageable pageable) {
        return distributorCommissionRepository.findByDistributorId(distributorId, pageable)
                .map(distributorMapper::toCommissionDTO);
    }

    @Override
    @Transactional
    public DistributorCommissionDTO payCommission(UUID commissionId) {
        DistributorCommission commission = distributorCommissionRepository.findById(commissionId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorCommission", commissionId));
        commission.setStatus(CommissionStatus.PAID);
        commission.setPaidAt(LocalDateTime.now());
        commission.setPaid(true);
        commission.setPaymentReference("PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        commission = distributorCommissionRepository.save(commission);
        return distributorMapper.toCommissionDTO(commission);
    }

    @Override
    public DistributorInventoryDTO getDistributorInventory(UUID distributorId, UUID productId) {
        DistributorInventory inventory = distributorInventoryRepository
                .findByDistributorIdAndProductId(distributorId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorInventory", productId));
        return distributorMapper.toInventoryDTO(inventory);
    }

    @Override
    public Page<DistributorInventoryDTO> getDistributorInventories(UUID distributorId, Pageable pageable) {
        return distributorInventoryRepository.findByDistributorId(distributorId, pageable)
                .map(distributorMapper::toInventoryDTO);
    }

    @Override
    @Transactional
    public DistributorInventoryDTO updateDistributorInventory(UUID inventoryId, DistributorInventoryUpdateRequest request) {
        DistributorInventory inventory = distributorInventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorInventory", inventoryId));

        if (request.getQuantityOnHand() != null) {
            inventory.setQuantityOnHand(request.getQuantityOnHand());
        }
        if (request.getQuantityReserved() != null) {
            inventory.setQuantityReserved(request.getQuantityReserved());
        }
        if (request.getQuantityInTransit() != null) {
            inventory.setQuantityInTransit(request.getQuantityInTransit());
        }
        if (request.getReorderPoint() != null) {
            inventory.setReorderPoint(request.getReorderPoint());
        }
        if (request.getReorderQuantity() != null) {
            inventory.setReorderQuantity(request.getReorderQuantity());
        }
        if (request.getLocation() != null) {
            inventory.setLocation(request.getLocation());
        }
        if (request.getBin() != null) {
            inventory.setBin(request.getBin());
        }
        if (request.getNotes() != null) {
            inventory.setNotes(request.getNotes());
        }

        inventory.setLastRestockedAt(LocalDateTime.now());
        inventory = distributorInventoryRepository.save(inventory);
        return distributorMapper.toInventoryDTO(inventory);
    }

    @Override
    @Transactional
    public DistributorCustomerDTO addDistributorCustomer(UUID distributorId, CreateDistributorCustomerRequest request) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor", distributorId));

        User customerUser = null;
        if (request.getCustomerUserId() != null) {
            customerUser = userRepository.findById(request.getCustomerUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", request.getCustomerUserId()));
        }

        DistributorCustomer customer = DistributorCustomer.builder()
                .distributor(distributor)
                .customerUser(customerUser)
                .companyName(request.getCompanyName())
                .contactPerson(request.getContactPerson())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .tier(request.getTier())
                .creditLimit(request.getCreditLimit())
                .currentBalance(BigDecimal.ZERO)
                .status(CustomerStatus.ACTIVE)
                .build();

        customer = distributorCustomerRepository.save(customer);
        return distributorMapper.toCustomerDTO(customer);
    }

    @Override
    @Transactional
    public DistributorCustomerDTO updateDistributorCustomer(UUID customerId, UpdateDistributorCustomerRequest request) {
        DistributorCustomer customer = distributorCustomerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorCustomer", customerId));

        if (request.getContactPerson() != null) {
            customer.setContactPerson(request.getContactPerson());
        }
        if (request.getPhone() != null) {
            customer.setPhone(request.getPhone());
        }
        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }
        if (request.getCity() != null) {
            customer.setCity(request.getCity());
        }
        if (request.getState() != null) {
            customer.setState(request.getState());
        }
        if (request.getPostalCode() != null) {
            customer.setPostalCode(request.getPostalCode());
        }
        if (request.getCountry() != null) {
            customer.setCountry(request.getCountry());
        }
        if (request.getTier() != null) {
            customer.setTier(request.getTier());
        }
        if (request.getStatus() != null) {
            customer.setStatus(request.getStatus());
        }
        if (request.getCreditLimit() != null) {
            customer.setCreditLimit(request.getCreditLimit());
        }
        if (request.getNotes() != null) {
            customer.setNotes(request.getNotes());
        }

        customer = distributorCustomerRepository.save(customer);
        return distributorMapper.toCustomerDTO(customer);
    }

    @Override
    public Page<DistributorCustomerDTO> getDistributorCustomers(UUID distributorId, Pageable pageable) {
        return distributorCustomerRepository.findByDistributorId(distributorId, pageable)
                .map(distributorMapper::toCustomerDTO);
    }

    @Override
    @Transactional
    public void removeDistributorCustomer(UUID distributorId, UUID customerId) {
        DistributorCustomer customer = distributorCustomerRepository
                .findByDistributorAndCustomer(distributorId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("DistributorCustomer", customerId));
        distributorCustomerRepository.delete(customer);
    }

    @Override
    @Transactional
    public WarehouseDTO createWarehouse(UUID distributorId, CreateWarehouseRequest request) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor", distributorId));

        if (warehouseRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Warehouse with this code already exists");
        }

        Warehouse warehouse = Warehouse.builder()
                .distributor(distributor)
                .name(request.getName())
                .code(request.getCode())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .phone(request.getPhone())
                .email(request.getEmail())
                .description(request.getDescription())
                .isActive(true)
                .build();

        warehouse = warehouseRepository.save(warehouse);
        return distributorMapper.toWarehouseDTO(warehouse);
    }

    @Override
    @Transactional
    public WarehouseDTO updateWarehouse(UUID warehouseId, UpdateWarehouseRequest request) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", warehouseId));

        if (request.getAddress() != null) {
            warehouse.setAddress(request.getAddress());
        }
        if (request.getCity() != null) {
            warehouse.setCity(request.getCity());
        }
        if (request.getState() != null) {
            warehouse.setState(request.getState());
        }
        if (request.getPostalCode() != null) {
            warehouse.setPostalCode(request.getPostalCode());
        }
        if (request.getCountry() != null) {
            warehouse.setCountry(request.getCountry());
        }
        if (request.getPhone() != null) {
            warehouse.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            warehouse.setEmail(request.getEmail());
        }
        if (request.getDescription() != null) {
            warehouse.setDescription(request.getDescription());
        }
        if (request.getIsActive() != null) {
            warehouse.setActive(request.getIsActive());
        }

        warehouse = warehouseRepository.save(warehouse);
        return distributorMapper.toWarehouseDTO(warehouse);
    }

    @Override
    public Page<WarehouseDTO> getDistributorWarehouses(UUID distributorId, Pageable pageable) {
        return warehouseRepository.findByDistributorId(distributorId, pageable)
                .map(distributorMapper::toWarehouseDTO);
    }

    @Override
    @Transactional
    public InventoryTransferDTO transferInventory(InventoryTransferRequest request) {
        Distributor distributor = distributorRepository.findById(request.getDistributorId())
                .orElseThrow(() -> new ResourceNotFoundException("Distributor", request.getDistributorId()));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", request.getProductId()));

        Warehouse fromWarehouse = null;
        if (request.getFromWarehouseId() != null) {
            fromWarehouse = warehouseRepository.findById(request.getFromWarehouseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Warehouse", request.getFromWarehouseId()));
        }

        Warehouse toWarehouse = warehouseRepository.findById(request.getToWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", request.getToWarehouseId()));

        InventoryTransfer transfer = InventoryTransfer.builder()
                .distributor(distributor)
                .product(product)
                .fromWarehouse(fromWarehouse)
                .toWarehouse(toWarehouse)
                .quantity(request.getQuantity())
                .status(TransferStatus.PENDING)
                .reason(request.getReason())
                .notes(request.getNotes())
                .build();

        transfer = inventoryTransferRepository.save(transfer);
        return distributorMapper.toTransferDTO(transfer);
    }
}
