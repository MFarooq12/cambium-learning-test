package com.cambium.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "First name is a mandatory field.")
    private String firstName;

    @NotBlank(message = "Last name is a mandatory field.")
    private String lastName;
}
