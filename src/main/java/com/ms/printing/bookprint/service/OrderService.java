package com.ms.printing.bookprint.service;

import com.ms.printing.bookprint.models.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order getOrder(UUID orderIdUuid);

    List<Order> getOrdersForUser(UUID customerId);
}
