package com.unitap.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EmailPasswordDto {

    private String email;
    private String password;

}
