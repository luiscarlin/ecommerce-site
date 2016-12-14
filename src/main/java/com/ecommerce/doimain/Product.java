package com.ecommerce.doimain;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private Byte[] image;
    private Double price;
    private String description;
    private String shortDescription;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<Review> reviews = new HashSet<Review>();

    @ManyToOne
    private User user;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private Set<Cart> carts = new HashSet<Cart>();
}
