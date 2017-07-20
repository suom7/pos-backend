package com.backend.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String type;
    private List<Data> data;

    public Field(String name, String type) {
        this.name = name;
        this.type = type;
    }

}
