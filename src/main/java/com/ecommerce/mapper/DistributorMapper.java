package com.ecommerce.mapper;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Distributor entity and DTO conversion.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AddressMapper.class})
public interface DistributorMapper {

    // Distributor mappings
    @Mapping(target = "id", expression = "java(distributor.getId().toString())")
    @Mapping(target = "userId", expression = "java(distributor.getUser() != null ? distributor.getUser().getId().toString() : null)")
    @Mapping(target = "email", expression = "java(distributor.getUser() != null ? distributor.getUser().getEmail() : null)")
    @Mapping(target = "firstName", expression = "java(distributor.getUser() != null ? distributor.getUser().getFirstName() : null)")
    @Mapping(target = "lastName", expression = "java(distributor.getUser() != null ? distributor.getUser().getLastName() : null)")
    @Mapping(target = "phone", expression = "java(distributor.getUser() != null ? distributor.getUser().getPhone() : null)")
    @Mapping(target = "totalProducts", expression = "java(distributor.getDistributorProducts().size())")
    @Mapping(target = "totalOrders", expression = "java(distributor.getDistributorOrders().size())")
    @Mapping(target = "totalRevenue", expression = "java(calculateTotalRevenue(distributor))")
    @Mapping(target = "totalCommissions", expression = "java(calculateTotalCommissions(distributor))")
    @Mapping(target = "createdAt", expression = "java(distributor.getCreatedAt() != null ? distributor.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(distributor.getUpdatedAt() != null ? distributor.getUpdatedAt().toString() : null)")
    DistributorDTO toDTO(Distributor distributor);

    @Mapping(target = "id", expression = "java(product.getId().toString())")
    @Mapping(target = "distributorId", expression = "java(product.getDistributor() != null ? product.getDistributor().getId().toString() : null)")
    @Mapping(target = "distributorName", expression = "java(product.getDistributor() != null ? product.getDistributor().getCompanyName() : null)")
    @Mapping(target = "productId", expression = "java(product.getProduct() != null ? product.getProduct().getId().toString() : null)")
    @Mapping(target = "productName", expression = "java(product.getProduct() != null ? product.getProduct().getName() : null)")
    @Mapping(target = "productSku", expression = "java(product.getProduct() != null ? product.getProduct().getSku() : null)")
    @Mapping(target = "createdAt", expression = "java(product.getCreatedAt() != null ? product.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(product.getUpdatedAt() != null ? product.getUpdatedAt().toString() : null)")
    DistributorProductDTO toProductDTO(DistributorProduct product);

    @Mapping(target = "id", expression = "java(order.getId().toString())")
    @Mapping(target = "distributorId", expression = "java(order.getDistributor() != null ? order.getDistributor().getId().toString() : null)")
    @Mapping(target = "distributorName", expression = "java(order.getDistributor() != null ? order.getDistributor().getCompanyName() : null)")
    @Mapping(target = "orderId", expression = "java(order.getOrder() != null ? order.getOrder().getId().toString() : null)")
    @Mapping(target = "orderNumber", expression = "java(order.getOrder() != null ? order.getOrder().getOrderNumber() : null)")
    @Mapping(target = "shippingAddress", expression = "java(order.getShippingAddress() != null ? toAddressDTO(order.getShippingAddress()) : null)")
    @Mapping(target = "billingAddress", expression = "java(order.getBillingAddress() != null ? toAddressDTO(order.getBillingAddress()) : null)")
    @Mapping(target = "items", expression = "java(mapOrderItems(order.getItems()))")
    @Mapping(target = "createdAt", expression = "java(order.getCreatedAt() != null ? order.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(order.getUpdatedAt() != null ? order.getUpdatedAt().toString() : null)")
    DistributorOrderDTO toOrderDTO(DistributorOrder order);

    @Mapping(target = "id", expression = "java(item.getId().toString())")
    @Mapping(target = "productId", expression = "java(item.getProduct() != null ? item.getProduct().getId().toString() : null)")
    @Mapping(target = "productName", expression = "java(item.getProduct() != null ? item.getProduct().getName() : null)")
    @Mapping(target = "productSku", expression = "java(item.getProduct() != null ? item.getProduct().getSku() : null)")
    DistributorOrderItemDTO toOrderItemDTO(DistributorOrderItem item);

    @Mapping(target = "id", expression = "java(commission.getId().toString())")
    @Mapping(target = "distributorId", expression = "java(commission.getDistributor() != null ? commission.getDistributor().getId().toString() : null)")
    @Mapping(target = "distributorName", expression = "java(commission.getDistributor() != null ? commission.getDistributor().getCompanyName() : null)")
    @Mapping(target = "orderId", expression = "java(commission.getOrder() != null ? commission.getOrder().getId().toString() : null)")
    @Mapping(target = "orderNumber", expression = "java(commission.getOrder() != null ? commission.getOrder().getOrderNumber() : null)")
    @Mapping(target = "distributorOrderId", expression = "java(commission.getDistributorOrder() != null ? commission.getDistributorOrder().getId().toString() : null)")
    @Mapping(target = "distributorOrderNumber", expression = "java(commission.getDistributorOrder() != null ? commission.getDistributorOrder().getDistributorOrderNumber() : null)")
    @Mapping(target = "createdAt", expression = "java(commission.getCreatedAt() != null ? commission.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(commission.getUpdatedAt() != null ? commission.getUpdatedAt().toString() : null)")
    DistributorCommissionDTO toCommissionDTO(DistributorCommission commission);

    @Mapping(target = "id", expression = "java(inventory.getId().toString())")
    @Mapping(target = "distributorId", expression = "java(inventory.getDistributor() != null ? inventory.getDistributor().getId().toString() : null)")
    @Mapping(target = "distributorName", expression = "java(inventory.getDistributor() != null ? inventory.getDistributor().getCompanyName() : null)")
    @Mapping(target = "productId", expression = "java(inventory.getProduct() != null ? inventory.getProduct().getId().toString() : null)")
    @Mapping(target = "productName", expression = "java(inventory.getProduct() != null ? inventory.getProduct().getName() : null)")
    @Mapping(target = "productSku", expression = "java(inventory.getProduct() != null ? inventory.getProduct().getSku() : null)")
    @Mapping(target = "warehouseId", expression = "java(inventory.getWarehouse() != null ? inventory.getWarehouse().getId().toString() : null)")
    @Mapping(target = "warehouseName", expression = "java(inventory.getWarehouse() != null ? inventory.getWarehouse().getName() : null)")
    @Mapping(target = "createdAt", expression = "java(inventory.getCreatedAt() != null ? inventory.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(inventory.getUpdatedAt() != null ? inventory.getUpdatedAt().toString() : null)")
    DistributorInventoryDTO toInventoryDTO(DistributorInventory inventory);

    @Mapping(target = "id", expression = "java(customer.getId().toString())")
    @Mapping(target = "distributorId", expression = "java(customer.getDistributor() != null ? customer.getDistributor().getId().toString() : null)")
    @Mapping(target = "customerId", expression = "java(customer.getCustomerUser() != null ? customer.getCustomerUser().getId().toString() : null)")
    @Mapping(target = "createdAt", expression = "java(customer.getCreatedAt() != null ? customer.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(customer.getUpdatedAt() != null ? customer.getUpdatedAt().toString() : null)")
    DistributorCustomerDTO toCustomerDTO(DistributorCustomer customer);

    @Mapping(target = "id", expression = "java(warehouse.getId().toString())")
    @Mapping(target = "distributorId", expression = "java(warehouse.getDistributor() != null ? warehouse.getDistributor().getId().toString() : null)")
    @Mapping(target = "distributorName", expression = "java(warehouse.getDistributor() != null ? warehouse.getDistributor().getCompanyName() : null)")
    @Mapping(target = "totalProducts", expression = "java(warehouse.getInventories().size())")
    @Mapping(target = "createdAt", expression = "java(warehouse.getCreatedAt() != null ? warehouse.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(warehouse.getUpdatedAt() != null ? warehouse.getUpdatedAt().toString() : null)")
    WarehouseDTO toWarehouseDTO(Warehouse warehouse);

    @Mapping(target = "id", expression = "java(transfer.getId().toString())")
    @Mapping(target = "distributorId", expression = "java(transfer.getDistributor() != null ? transfer.getDistributor().getId().toString() : null)")
    @Mapping(target = "productId", expression = "java(transfer.getProduct() != null ? transfer.getProduct().getId().toString() : null)")
    @Mapping(target = "productName", expression = "java(transfer.getProduct() != null ? transfer.getProduct().getName() : null)")
    @Mapping(target = "fromWarehouseId", expression = "java(transfer.getFromWarehouse() != null ? transfer.getFromWarehouse().getId().toString() : null)")
    @Mapping(target = "fromWarehouseName", expression = "java(transfer.getFromWarehouse() != null ? transfer.getFromWarehouse().getName() : null)")
    @Mapping(target = "toWarehouseId", expression = "java(transfer.getToWarehouse() != null ? transfer.getToWarehouse().getId().toString() : null)")
    @Mapping(target = "toWarehouseName", expression = "java(transfer.getToWarehouse() != null ? transfer.getToWarehouse().getName() : null)")
    @Mapping(target = "createdAt", expression = "java(transfer.getCreatedAt() != null ? transfer.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(transfer.getUpdatedAt() != null ? transfer.getUpdatedAt().toString() : null)")
    InventoryTransferDTO toTransferDTO(InventoryTransfer transfer);

    @Named("toAddressDTO")
    AddressDTO toAddressDTO(Address address);

    default List<DistributorOrderItemDTO> mapOrderItems(List<DistributorOrderItem> items) {
        if (items == null) {
            return new java.util.ArrayList<>();
        }
        List<DistributorOrderItemDTO> result = new java.util.ArrayList<>();
        for (DistributorOrderItem item : items) {
            result.add(toOrderItemDTO(item));
        }
        return result;
    }

    default BigDecimal calculateTotalRevenue(Distributor distributor) {
        if (distributor.getDistributorOrders() == null) {
            return BigDecimal.ZERO;
        }
        return distributor.getDistributorOrders().stream()
                .filter(order -> order.getStatus() != DistributorOrderStatus.CANCELLED && order.getStatus() != DistributorOrderStatus.RETURNED)
                .map(DistributorOrder::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    default BigDecimal calculateTotalCommissions(Distributor distributor) {
        if (distributor.getCommissions() == null) {
            return BigDecimal.ZERO;
        }
        return distributor.getCommissions().stream()
                .filter(commission -> commission.getStatus() == CommissionStatus.PAID)
                .map(DistributorCommission::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
