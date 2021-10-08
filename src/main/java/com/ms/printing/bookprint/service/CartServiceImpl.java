package com.ms.printing.bookprint.service;

import com.ms.printing.bookprint.converters.DataModelMapper;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

@Transactional
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartRepository cartRepository;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private CartProductMappingRepository cartProductMappingRepository;

    @Resource
    private DataModelMapper dataModelMapper;

    @Override
    public CartDto getCart(UUID cartId) {
        CartEntity cartEntity = cartRepository.getOne(cartId);
        Cart cart = dataModelMapper.map(cartEntity, Cart.class);
        return dataModelMapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto getCartForUser(UUID customerId) {
        CartEntity cartEntity = cartRepository.findFirstByCustomerEntityIdAndCheckedOutFalseOrderByModifiedOnDesc(customerId);
        if (Objects.isNull(cartEntity)) {
            CustomerEntity customerEntity = customerRepository.getOne(customerId);
            cartEntity = CartEntity.builder()
                    .id(UUID.randomUUID())
                    .customerEntity(customerEntity)
                    .checkedOut(false)
                    .build();
            cartRepository.saveAndFlush(cartEntity);
        }
        Cart cart = dataModelMapper.map(cartEntity, Cart.class);
        return dataModelMapper.map(cart, CartDto.class);
    }

    @Override
    public UUID addProduct(UUID cartId, Product product) {
        CartEntity cartEntity = cartRepository.getOne(cartId);
        ProductEntity productEntity;
        if (Objects.isNull(product.getId())) {
            product.setId(UUID.randomUUID());
            productEntity = dataModelMapper.map(product, ProductEntity.class);
            productRepository.saveAndFlush(productEntity);
        } else {
            productEntity = productRepository.getOne(product.getId());
        }

        CartProductMappingEntity mappingEntity = cartProductMappingRepository.findFirstByCartEntityIdAndProductEntityId(cartId, productEntity.getId());
        if (Objects.isNull(mappingEntity)) {
            mappingEntity = CartProductMappingEntity.builder().cartEntity(cartEntity).productEntity(productEntity).build();
        }
        mappingEntity.setQuantity(mappingEntity.getQuantity() + 1);
        cartProductMappingRepository.saveAndFlush(mappingEntity);
        return productEntity.getId();
    }

    @Override
    public void removeProduct(UUID cartId, UUID productId) {
        cartProductMappingRepository.deleteByCartEntityIdAndProductEntityId(cartId, productId);
    }

    @Override
    public void clear(UUID cartId) {
        cartProductMappingRepository.deleteAllByCartEntityId(cartId);
        CartEntity cartEntity = cartRepository.getOne(cartId);
        cartEntity.setPrice(0);
        cartRepository.saveAndFlush(cartEntity);
    }

    @Override
    public void checkout(UUID cartId) {

    }

    @Override
    public int updateQuantity(UUID cartId, UUID productId, int quantity) {
        CartProductMappingEntity mappingEntity = cartProductMappingRepository.findFirstByCartEntityIdAndProductEntityId(cartId, productId);
        if (quantity <= 0) {
            removeProduct(cartId, productId);
            return 0;
        }

        mappingEntity.setQuantity(quantity);
        cartProductMappingRepository.saveAndFlush(mappingEntity);
        return quantity;
    }

    @Override
    public void deleteCart(UUID cartId) {
        cartProductMappingRepository.deleteAllByCartEntityId(cartId);
        cartRepository.deleteById(cartId);
    }

}
