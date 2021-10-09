package com.ms.printing.bookprint.service;

import com.ms.printing.bookprint.converters.DataModelMapper;
import com.ms.printing.bookprint.enums.BookType;
import com.ms.printing.bookprint.enums.ProductType;
import com.ms.printing.bookprint.models.Book;
import com.ms.printing.bookprint.models.Cart;
import com.ms.printing.bookprint.models.Product;
import com.ms.printing.bookprint.models.dto.CartDto;
import com.ms.printing.bookprint.repositories.CartProductMappingRepository;
import com.ms.printing.bookprint.repositories.CartRepository;
import com.ms.printing.bookprint.repositories.CustomerRepository;
import com.ms.printing.bookprint.repositories.ProductRepository;
import com.ms.printing.bookprint.repositories.entities.CartEntity;
import com.ms.printing.bookprint.repositories.entities.CartProductMappingEntity;
import com.ms.printing.bookprint.repositories.entities.CustomerEntity;
import com.ms.printing.bookprint.repositories.entities.ProductEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CartServiceImplTest {

    private static final UUID TEST_UUID = UUID.randomUUID();

    @InjectMocks
    private CartServiceImpl cartService;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CartProductMappingRepository cartProductMappingRepository;
    @Mock
    private DataModelMapper dataModelMapper;
    @Mock
    private Cart cart;
    @Mock
    private CartDto cartDto;
    @Mock
    private CartEntity cartEntity;
    @Mock
    private CustomerEntity customerEntity;
    @Mock
    private Book product;
    @Mock
    private ProductEntity productEntity;
    @Mock
    private CartProductMappingEntity mappingEntity;

    @Test
    public void testGetCartDm() {
        when(cartRepository.getOne(any(UUID.class))).thenReturn(cartEntity);
        when(dataModelMapper.map(any(CartEntity.class), eq(Cart.class))).thenReturn(cart);
        Cart cartDm = cartService.getCartDm(TEST_UUID);
        assertNotNull(cartDm);
        assertEquals(cart, cartDm);
    }

    @Test
    public void testGetCart() {
        when(cartRepository.getOne(any(UUID.class))).thenReturn(cartEntity);
        when(dataModelMapper.map(any(CartEntity.class), eq(Cart.class))).thenReturn(cart);
        when(dataModelMapper.map(any(Cart.class), eq(CartDto.class))).thenReturn(cartDto);

        CartDto result = cartService.getCart(TEST_UUID);
        assertNotNull(result);
        assertEquals(result, cartDto);
    }

    @Test
    public void testGetCartForUser() {
        when(cartRepository.findFirstByCustomerEntityIdAndCheckedOutFalseOrderByModifiedOnDesc(any(UUID.class))).thenReturn(null);
        when(customerRepository.getOne(any(UUID.class))).thenReturn(customerEntity);
        ArgumentCaptor<CartEntity> captor = ArgumentCaptor.forClass(CartEntity.class);
        when(cartRepository.saveAndFlush(captor.capture())).thenReturn(null);

        when(dataModelMapper.map(any(CartEntity.class), eq(Cart.class))).thenReturn(cart);
        when(dataModelMapper.map(any(Cart.class), eq(CartDto.class))).thenReturn(cartDto);

        CartDto result = cartService.getCartForUser(TEST_UUID);
        assertNotNull(result);
        assertEquals(cartDto, result);
        CartEntity captured = captor.getValue();
        assertNotNull(captured);
        assertNotNull(captured.getId());
        assertEquals(customerEntity, captured.getCustomerEntity());
        assertFalse(captured.isCheckedOut());
    }

    @Test
    public void testAddProduct() {
        when(cartRepository.getOne(any(UUID.class))).thenReturn(cartEntity);
        doNothing().when(product).setId(any(UUID.class));
        when(dataModelMapper.map(any(Product.class), eq(ProductEntity.class))).thenReturn(productEntity);
        ArgumentCaptor<ProductEntity> productCaptor = ArgumentCaptor.forClass(ProductEntity.class);
        when(productRepository.saveAndFlush(productCaptor.capture())).thenReturn(null);

        when(cartProductMappingRepository.findFirstByCartEntityIdAndProductEntityId(any(UUID.class), any(UUID.class))).thenReturn(null);
        ArgumentCaptor<CartProductMappingEntity> mappingCaptor = ArgumentCaptor.forClass(CartProductMappingEntity.class);
        when(cartProductMappingRepository.saveAndFlush(mappingCaptor.capture())).thenReturn(null);
        when(productEntity.getId()).thenReturn(TEST_UUID);

        UUID resultUuid = cartService.addProduct(TEST_UUID, product);
        assertNotNull(resultUuid);
        assertNotNull(productCaptor.getValue());
        assertNotNull(productCaptor.getValue().getId());
        assertEquals(TEST_UUID, resultUuid);
        assertNotNull(mappingCaptor.getValue());
        assertEquals(1, mappingCaptor.getValue().getQuantity());
    }

    @Test
    public void testRemoveProduct() {
        doNothing().when(cartProductMappingRepository).deleteByCartEntityIdAndProductEntityId(any(UUID.class), any(UUID.class));
        cartService.removeProduct(TEST_UUID, TEST_UUID);
    }

    @Test
    public void testClear() {
        doNothing().when(cartProductMappingRepository).deleteAllByCartEntityId(any(UUID.class));
        when(cartRepository.getOne(any(UUID.class))).thenReturn(cartEntity);
        doNothing().when(cartEntity).setPrice(anyDouble());
        when(cartRepository.saveAndFlush(any(CartEntity.class))).thenReturn(null);

        cartService.clear(TEST_UUID);
    }

    @Test
    public void testUpdateQuantity() {
        when(cartProductMappingRepository.findFirstByCartEntityIdAndProductEntityId(any(UUID.class), any(UUID.class))).thenReturn(mappingEntity);
        doNothing().when(mappingEntity).setQuantity(anyInt());
        when(cartProductMappingRepository.saveAndFlush(any(CartProductMappingEntity.class))).thenReturn(null);
        int quantity = cartService.updateQuantity(TEST_UUID, TEST_UUID, 5);
        assertEquals(5, quantity);
    }

    @Test
    public void testUpdateQuantityWithNegativeValue() {
        doNothing().when(cartProductMappingRepository).deleteByCartEntityIdAndProductEntityId(any(UUID.class), any(UUID.class));
        int quantity = cartService.updateQuantity(TEST_UUID, TEST_UUID, -1);
        assertEquals(0, quantity);
        verify(cartProductMappingRepository, never()).findFirstByCartEntityIdAndProductEntityId(any(UUID.class), any(UUID.class));
        verify(cartProductMappingRepository, never()).saveAndFlush(any(CartProductMappingEntity.class));
    }

    @Test
    public void testDeleteCart() {
        cartService.deleteCart(TEST_UUID);
        verify(cartProductMappingRepository).deleteAllByCartEntityId(eq(TEST_UUID));
        verify(cartRepository).deleteById(eq(TEST_UUID));
    }

    @Test
    public void testUpdateBookTypesHappyPath() {
        Map<String, BookType> bookTypesMap = new HashMap<>();
        bookTypesMap.put(TEST_UUID.toString(), BookType.BOTH);
        when(productRepository.getOne(any(UUID.class))).thenReturn(productEntity);
        when(product.getBookType()).thenReturn(BookType.E_BOOK);
        when(product.getProductType()).thenReturn(ProductType.BOOK);
        when(dataModelMapper.map(any(ProductEntity.class), eq(Product.class))).thenReturn(product);
        when(dataModelMapper.map(any(Product.class), eq(ProductEntity.class))).thenReturn(productEntity);
        when(productRepository.saveAndFlush(any(ProductEntity.class))).thenReturn(null);

        cartService.updateBookTypes(TEST_UUID, bookTypesMap);
        verify(product).setBookType(eq(BookType.BOTH));
    }

    @Test
    public void testUpdateCheckoutStatus() {
        when(cartRepository.getOne(any(UUID.class))).thenReturn(cartEntity);
        when(cartRepository.saveAndFlush(any(CartEntity.class))).thenReturn(null);

        cartService.updateCheckoutStatus(TEST_UUID, true);
        verify(cartEntity).setCheckedOut(eq(true));
    }
}