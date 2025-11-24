package com.example.cookie.controller;

import com.example.cookie.db.UserRopository;
import com.example.cookie.model.UserDto;
import com.example.cookie.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserRopository userRopository;


    @GetMapping("/me")
    public UserDto me(HttpServletRequest httpServletRequest, @CookieValue(name="auth-cookie", required = false) String authCookie){
        log.info(authCookie);


        var opUserDto = userRopository.findById(authCookie);

        return opUserDto.get();

        /*
        var cookies = httpServletRequest.getCookies();

        if( cookies != null){
            for(Cookie cookie: cookies){
                log.info("key : {}, value : {} ", cookie.getName(), cookie.getValue());
            }
        }
        return null;
        */
    }
}
