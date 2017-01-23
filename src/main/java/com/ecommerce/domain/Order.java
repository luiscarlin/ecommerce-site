package com.ecommerce.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private BigInteger total;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(name="product_order", joinColumns=@JoinColumn(name="order_id"), inverseJoinColumns=@JoinColumn(name="product_id"))
    private Set<Product> products;
}
