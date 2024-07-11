package org.example.chating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
@GetMapping("/addToCart")
public String addToCart(@RequestParam("productId") Long productId,
                        @RequestParam("productName") String productName,
                        @RequestParam("productType") String productType) {
    cartService.addToCart(productId, productType, productName);

    if (productType.equals("pizza")) {
        return "redirect:/pizzas";
    } if (productType.equals("salad")) {
        return "redirect:/salad";
    } else {
        return "redirect:/error"; // Handle other cases appropriately
    }
}
    @GetMapping("/viewCart")
    public String viewCart(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
       int totalItems = cartItems.stream().mapToInt(CartItem::getQuantity).sum();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalItems", totalItems);
        return "view-cart"; // Create view-cart.html to display cart items
    }
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("cartItemId") Long cartItemId) {
        cartService.deleteProduct(cartItemId);
        return "redirect:/viewCart";
    }
    @PostMapping("/reduceQuantity")
    public String reduceQuantity(@RequestParam("cartItemId") Long cartItemId) {
        cartService.reduceQuantity(cartItemId);
        return "redirect:/viewCart";
    }
}
