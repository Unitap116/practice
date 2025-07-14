package com.unitap.dto.response;

import java.util.UUID;

public record CardResponse(
        UUID id,
        String name
) {
}
