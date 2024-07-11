package org.example.chating;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
//    CartItem findByProductId(Long productId);

    CartItem findByProductIdAndType(Long productId, String productType);

}
