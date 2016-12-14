package com.ecommerce.doimain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue
    private Long id;
    private Integer quantity;
    private Date dateAdded;
}
