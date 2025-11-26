package org.delivery.api.domain.token.converter;


import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.Errorcode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.model.TokenDto;

import java.util.Objects;

@RequiredArgsConstructor
@Converter
public class TokenConverter {


    public TokenResponse toResponse(TokenDto accesccToken, TokenDto refreshToken){

        Objects.requireNonNull(accesccToken,() -> {throw new ApiException(Errorcode.NULL_POINT);});
        Objects.requireNonNull(refreshToken,() -> {throw new ApiException(Errorcode.NULL_POINT);});

        return TokenResponse.builder()
                .accessToken(accesccToken.getToken())
                .accessTokenExpiredAt(accesccToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                .build();
    }

}
