package org.delivery.api.domain.user.business;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.Errorcode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.service.UserService;
import org.delivery.db.user.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Business
public class UserBusiness {


    private final UserConverter userConverter;

    private final UserService userService;

    private final TokenBusiness tokenBusiness;

    /*
    * 사용자 가입처리 로직
    * 1. req -> entity
    * 2. entity -> save
    * 3. save entity > res
    * 4. return res
    * */
    public UserResponse register(UserRegisterRequest req) {

        var entity = userConverter.toEntity(req);
        var newEntity = userService.register(entity);
        return userConverter.toResponese(newEntity);

        /*
        return Optional.ofNullable(req)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponese)
                .orElseThrow(() -> new ApiException(Errorcode.NULL_POINT, "REQUEST NULL"));
        */

    }

    /*
    * 1. email, pwd로 사용자 체크
    * 2. user entitiy 로그인 확인
    * 3. token 생성
    * 4. token res
    * */
    public TokenResponse login(UserLoginRequest req) {
        // 사용자 없으면 throw
        var userEntity = userService.login(req.getEmail(), req.getPassword());

        var tokenRes = tokenBusiness.issueToken(userEntity);

        return tokenRes;
    }
}
