package net.stage.Server.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity  //classe c'est une entity JPA
@Table(name = "customers") //sepecification du non de table dans une BD
@Data
@AllArgsConstructor
@NoArgsConstructor
//Alerte :Entity-->Dto-->Repository-->Service-->ServiceImpl-->Controller
public class CustomerEntity {
    @Id //id c'est une cle primaire de cette entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) //la valeur de ID est generer automatiquement par la base de donnees
    private Long id;

    @Column(name="name",nullable = false)   //la valeur de ce champ ne peut pas etre NULL( personnalisation de mapping de colonne)
    private String name;

    @Column(name="email",nullable = false, unique = true)  //unique=true cad que cette valeur doit etre unique dans DB
    private String email;

    @Column(name="address",nullable = false)
    private String address;

    @Column(name="phoneNumber",nullable = false)
    private String phoneNumber;


    /*les annotations de lombook sont:
    @Getter @Setter @NoargsConstructor @argsConstructor @Builder
    @Data( combinaison de @Getter, @Setter, @ToString, @EqualsAndHashCode et @RequiredArgsConstructor)
    @EqualsAndHashcode
    @ToString
    @Builder
    @Value
    @NonNull @
     */

    /*
    Hibernate est un framework de mappage objet-relationnel (ORM) pour le langage de programmation Java. Il facilite l'interaction entre les applications Java et les bases de données relationnelles en mappant les objets Java (entités) sur les tables de la base de données.
     */
}