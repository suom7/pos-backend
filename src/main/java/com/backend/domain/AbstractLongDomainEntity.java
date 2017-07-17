package com.backend.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @Data is a Project Lombok annotation to autogenerate getters, setters, constructors, toString, hash, equals, and other things.
 *       It cuts down on the boilerplate.
 * @author dmiuser
 *
 */
@Data
public abstract class AbstractLongDomainEntity implements Serializable {
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_VERSION = "version";
    public static final String COLUMN_NAME_STATE = "state";
    private static final long serialVersionUID = 1982069997503175834L;

    /** unique id */
    private Long id;
    /** version number changes */
    @JsonIgnore
    private Long version;
    /** state */
    private Long state;
    /** created by */
    @JsonIgnore
    private String createdBy;
    /** created date */
    @JsonIgnore
    private Date createdDate;
    /** updated by */
    @JsonIgnore
    private String updatedBy;
    /** updated date */
    @JsonIgnore
    private Date updatedDate;
}
