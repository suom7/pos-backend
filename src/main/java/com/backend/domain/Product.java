package com.backend.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product extends AbstractLongDomainEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long categoryId;
    private String name;
    private String code;
    private Integer qty;
    private String description;
    private Double price1;
    private Double price2;
    private Integer currentType;
    private String image;
    
}
