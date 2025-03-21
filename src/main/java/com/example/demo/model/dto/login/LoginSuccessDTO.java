package com.example.demo.model.dto.login;

import com.example.demo.model.dto.register.LoginResponseDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class LoginSuccessDTO extends LoginResponseDTO {
    private String access_token;
}
