package com.technosdev.entities.response;

import com.technosdev.entities.CurrentUserEntity;

public record LoginResponse(String token , CurrentUserEntity currentUserEntity) {
}
