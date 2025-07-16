package com.unitap.dto.response;

public record CardResponse(
        String userId,
        String name,
        Boolean isPublic,
        Long views
) {
}
