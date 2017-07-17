package com.backend.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
}
