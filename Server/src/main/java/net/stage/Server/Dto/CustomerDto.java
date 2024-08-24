package net.stage.Server.Dto;

import lombok.*;

//n DTO (Data Transfer Object) est utilisé pour transférer des données entre différentes couches d'une application, souvent entre la couche de service et la couche de présentation.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private String address;
    private  String phoneNumber;
}
//dans entity et dto l'annotation @Data tjrs