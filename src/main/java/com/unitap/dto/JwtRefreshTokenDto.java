package com.unitap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class JwtRefreshTokenDto {

    private String token;
    private Instant expiresAt;

}

