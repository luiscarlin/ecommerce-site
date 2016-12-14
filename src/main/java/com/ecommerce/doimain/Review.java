package com.ecommerce.doimain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
}
