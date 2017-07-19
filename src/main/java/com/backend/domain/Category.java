package com.backend.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category extends AbstractLongDomainEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String type;
}
