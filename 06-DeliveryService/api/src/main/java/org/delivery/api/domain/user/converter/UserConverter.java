package org.delivery.api.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.Errorcode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class UserConverter {

    public UserEntity toEntity(UserRegisterRequest req){
        return Optional.ofNullable(req)
                .map(it -> {
                    return UserEntity.builder()
                            .name(req.getName())
                            .email(req.getEmail())
                            .address(req.getAddress())
                            .password(req.getPassword())
                            .build();
                }).orElseThrow(()-> new ApiException(Errorcode.NULL_POINT, "UserRegisterRequest NULL"));
    }

    public UserResponse toResponese(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    return UserResponse.builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .status(userEntity.getStatus())
                            .email(userEntity.getEmail())
                            .address(userEntity.getAddress())
                            .registeredAt(userEntity.getRegisteredAt())
                            .unregisteredAt(userEntity.getUnregisteredAt())
                            .lastLoginAt(userEntity.getLastLoginAt())
                            .build();
                }).orElseThrow(() -> new ApiException(Errorcode.NULL_POINT, "UserEntity NULL"));
    }

}
