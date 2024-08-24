package net.stage.Server.Dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;  // Utilisez Integer pour permettre la nullabilité si nécessaire

    private String name;

    private String description;

    private double price;

    private int stockQuantity;

    private Long categoryId;  // Utilisez Integer si vous souhaitez inclure l'ID de catégorie seulement

}

