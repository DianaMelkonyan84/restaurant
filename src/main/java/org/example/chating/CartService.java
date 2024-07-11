package org.example.chating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

public void addToCart(Long productId, String productType, String productName) {
    CartItem cartItem = cartItemRepository.findByProductIdAndType(productId, productType);
    if (cartItem == null) {
        cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(1); // Initial quantity
        cartItem.setType(productType);
        cartItem.setName(productName); // Set the specific product name
    } else {
        cartItem.setQuantity(cartItem.getQuantity() + 1); // Increase quantity
    }

    cartItemRepository.save(cartItem);
}


    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }
    public void deleteProduct(Long id) {
        cartItemRepository.deleteById(id);
    }
    public void reduceQuantity(Long cartItemId) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            int currentQuantity = cartItem.getQuantity();

            if (currentQuantity > 1) {
                cartItem.setQuantity(currentQuantity - 1);
               cartItemRepository.save(cartItem);
            } else if (currentQuantity == 1) {
               deleteProduct(cartItemId);
            }
        }
    }
}
