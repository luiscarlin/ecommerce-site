package com.ecommerce.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private Integer rating;
    private Date date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;
}
