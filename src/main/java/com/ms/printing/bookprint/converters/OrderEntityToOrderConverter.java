package com.ms.printing.bookprint.converters;

import com.ms.printing.bookprint.models.DeliveryDetails;
import com.ms.printing.bookprint.models.Order;
import com.ms.printing.bookprint.models.PaymentInfo;
import com.ms.printing.bookprint.models.Shipment;
import com.ms.printing.bookprint.repositories.entities.CustomerEntity;
import com.ms.printing.bookprint.repositories.entities.OrderEntity;
import com.ms.printing.bookprint.repositories.entities.PaymentDetailsEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrderEntityToOrderConverter extends AbstractConverter<OrderEntity, Order> {

    @Resource
    private ProductEntityToProductConverter productEntityToProductConverter;

    @Override
    public boolean canConvert(Object source, Class<?> clazz) {
        return source instanceof OrderEntity && clazz.isAssignableFrom(Order.class);
    }

    @Override
    public Order convert(OrderEntity orderEntity) {
        Order order = transform(orderEntity, Order.class);
        Shipment shipment = populateShipment(orderEntity);
        PaymentInfo paymentInfo = populatePaymentInfo(orderEntity);

        order.setOrderId(orderEntity.getId());
        order.setCustomerId(orderEntity.getCustomerEntity().getId());
        order.setPaymentInfo(paymentInfo);
        order.setProduct(productEntityToProductConverter.convert(orderEntity.getProductEntity()));
        order.setPurchasedOn(orderEntity.getCreatedOn());
        order.setShipment(shipment);
        order.setShippingCost(orderEntity.getShippingCost());

        return order;
    }

    private PaymentInfo populatePaymentInfo(OrderEntity orderEntity) {
        PaymentDetailsEntity paymentDetailsEntity = orderEntity.getPaymentDetailsEntity();
        return PaymentInfo.builder()
                .transactionId(paymentDetailsEntity.getTransactionId())
                .paymentStatus(paymentDetailsEntity.getPaymentStatus())
                .paymentMethod(paymentDetailsEntity.getPaymentMethod())
                .build();
    }

    private Shipment populateShipment(OrderEntity orderEntity) {
        return Shipment.builder()
                .shipmentId(orderEntity.getShipmentEntity().getId())
                .productId(orderEntity.getProductEntity().getId())
                .quantity(orderEntity.getQuantity())
                .status(orderEntity.getShipmentEntity().getShippingStatus())
                .lastUpdated(orderEntity.getShipmentEntity().getModifiedOn())
                .deliveryDetails(populateDeliveryDetails(orderEntity))
                .build();
    }

    private DeliveryDetails populateDeliveryDetails(OrderEntity orderEntity) {
        CustomerEntity customerEntity = orderEntity.getCustomerEntity();
        return DeliveryDetails.builder()
                .customerName(customerEntity.getFirstname() + " " + customerEntity.getLastname())
                .customerPhoneNo(customerEntity.getPhoneNumber())
                .address(customerEntity.getAddress())
                .pincode(customerEntity.getPincode())
                .build();
    }
}
