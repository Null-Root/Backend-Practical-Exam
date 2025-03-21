package com.example.demo.model.dto.order;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private String product_id;
    private String quantity;
}
