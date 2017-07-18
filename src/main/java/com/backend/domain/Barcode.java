package com.backend.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Barcode implements Serializable {
    private static final long serialVersionUID = 1L;

    @CsvBindByName(column = "BarcodeId", required = true)
    private String barcodeId;

    @CsvBindByName(column = "Description", required = true)
    private String description;
    
    @CsvBindByName(column = "Category", required = true)
    private String category;
    
}
