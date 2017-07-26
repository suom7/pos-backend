package com.backend.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemConfig extends AbstractLongDomainEntity implements Serializable {
    private static final long serialVersionUID = -6566281457537863924L;
    private Long id;
    private String companyName;
    private String companyAddress;
    private String companyTel;
    private String companyEmail;
    private String companyLogo;
    private Double companyCurrencyRate;
    private String companyRule;
    private String companyOtherInfo;
}
