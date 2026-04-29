package com.erric.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRequest {

    @NotBlank(message = "name is required")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}