package com.unitap.dto.response;

public record CardResponse(
        String id,
        String name,
        Boolean isPublic,
        Long views
) {
}
