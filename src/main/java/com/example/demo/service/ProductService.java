package com.example.demo.service;

import com.example.demo.model.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void initProducts() {
        Product p1 = new Product("Burger", 250);
        Product p2 = new Product("Fries", 350);

        productRepository.saveAll(List.of(p1, p2));
    }

    public boolean placeNewOrder(long productId, int requestedQty) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        // If product doesn't exist, it is unavailable
        if (optionalProduct.isEmpty()) return false;

        // Get Qty and check if requested qty is valid
        Product product = optionalProduct.get();
        if (product.getAvailableStock() < requestedQty) return false;

        // If valid, reduce stock count
        product.setAvailableStock(
                product.getAvailableStock() - requestedQty
        );

        productRepository.save(product);
        return true;
    }
}
