package net.stage.Server.Entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Utiliser Integer pour permettre la nullabilité si nécessaire

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

}
