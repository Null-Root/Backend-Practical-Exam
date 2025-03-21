package com.example.demo.controller;

import com.example.demo.model.dto.order.OrderRequestDTO;
import com.example.demo.model.dto.order.OrderResponseDTO;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {

    @Autowired
    private ProductService productService;

    @PostMapping("/order")
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @RequestBody OrderRequestDTO orderRequestDTO
    ) {
        boolean isOrderSuccess = productService.placeNewOrder(
                Long.parseLong(orderRequestDTO.getProduct_id()),
                Integer.parseInt(orderRequestDTO.getQuantity())
        );

        if (isOrderSuccess) {
            return ResponseEntity
                    .status(201)
                    .body(
                            OrderResponseDTO
                                    .builder()
                                    .message("You have successfully ordered this product")
                                    .build()
                    );
        }
        return ResponseEntity
                .status(400)
                .body(
                        OrderResponseDTO
                                .builder()
                                .message("Failed to order this product due to unavailability of the stock")
                                .build()
                );
    }
}
