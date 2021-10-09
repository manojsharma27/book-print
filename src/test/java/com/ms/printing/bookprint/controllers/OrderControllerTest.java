package com.ms.printing.bookprint.controllers;

import com.ms.printing.bookprint.models.Order;
import com.ms.printing.bookprint.models.ShipmentTrackingDetails;
import com.ms.printing.bookprint.models.dto.OperationResponse;
import com.ms.printing.bookprint.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderControllerTest {

    private static final UUID TEST_UUID = UUID.randomUUID();
    private static final String TEST_ID = TEST_UUID.toString();

    @InjectMocks private OrderController orderController;
    @Mock private OrderService orderService;
    @Mock private Order order;

    @Test
    public void testGetOrder() {
        when(orderService.getOrder(any(UUID.class))).thenReturn(order);
        ResponseEntity<Order> responseEntity = orderController.getOrder(TEST_ID);
        assertNotNull(responseEntity);
        assertEquals(order, responseEntity.getBody());
    }

    @Test
    public void testGetOrdersForUser() {
        when(orderService.getOrdersForUser(any(UUID.class))).thenReturn(Arrays.asList(order));
        ResponseEntity<List<Order>> responseEntity = orderController.getOrdersForUser(TEST_ID);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(order, responseEntity.getBody().get(0));
    }

    @Test
    public void testDelete() {
        doNothing().when(orderService).delete(any(UUID.class));
        ResponseEntity<OperationResponse> responseEntity = orderController.delete(TEST_ID);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().getMessage().contains("Deleted"));
    }

    @Test
    public void testTrackShipment() {
        when(orderService.trackShipment(any(UUID.class))).thenReturn(ShipmentTrackingDetails.builder().orderId(TEST_UUID).build());
        ResponseEntity<ShipmentTrackingDetails> responseEntity = orderController.trackShipment(TEST_ID);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(TEST_UUID, responseEntity.getBody().getOrderId());
    }
}