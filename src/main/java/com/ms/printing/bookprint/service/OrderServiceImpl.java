package com.ms.printing.bookprint.service;

import com.ms.printing.bookprint.converters.DataModelMapper;
import com.ms.printing.bookprint.models.Order;
import com.ms.printing.bookprint.models.ShipmentTrackingDetails;
import com.ms.printing.bookprint.remote.PrintingServiceInvoker;
import com.ms.printing.bookprint.repositories.OrderRepository;
import com.ms.printing.bookprint.repositories.ShipmentRepository;
import com.ms.printing.bookprint.repositories.entities.OrderEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private DataModelMapper dataModelMapper;

    @Resource
    private PrintingServiceInvoker printingServiceInvoker;

    @Resource
    private ShipmentRepository shipmentRepository;

    @Override
    public Order getOrder(UUID orderIdUuid) {
        OrderEntity orderEntity = orderRepository.getOne(orderIdUuid);
        return dataModelMapper.map(orderEntity, Order.class);
    }

    @Override
    public List<Order> getOrdersForUser(UUID customerId) {
        List<OrderEntity> orderEntities = orderRepository.findAllByCustomerEntityIdOrderByModifiedOnAsc(customerId);
        if (CollectionUtils.isEmpty(orderEntities)) {
            return Collections.emptyList();
        }
        return orderEntities.stream()
                .map(oe -> dataModelMapper.map(oe, Order.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID orderIdUuid) {
        orderRepository.deleteById(orderIdUuid);
    }

    @Override
    public ShipmentTrackingDetails trackShipment(UUID orderIdUuid) {
        OrderEntity orderEntity = orderRepository.getOne(orderIdUuid);
        UUID shipmentId = orderEntity.getShipmentEntity().getId();
        ShipmentTrackingDetails shipmentTrackingDetails = printingServiceInvoker.trackShipment(shipmentId);
        orderEntity.getShipmentEntity().setShippingStatus(shipmentTrackingDetails.getStatus());
        shipmentRepository.saveAndFlush(orderEntity.getShipmentEntity());
        return shipmentTrackingDetails;
    }
}
