package net.stage.Server.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private LocalDate orderDate;
    private Character status;
    private int quantityProduct;
    private Long customerId;
    private Set<Long> productIds; // List of product IDs
}
