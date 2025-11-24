package com.example.cookie.service;

import com.example.cookie.db.UserRopository;
import com.example.cookie.model.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRopository userRopository;

    public void login(LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        var id = loginRequest.getId();
        var pwd = loginRequest.getPassword();


        var opUser = userRopository.findByName(id);

        if(opUser != null){
            var userDto = opUser.get();
            if(userDto.getPassword().equals(userDto.getPassword())){

                var cookie = new Cookie("auth-cookie", userDto.getId());
                cookie.setDomain("localhost");
                cookie.setPath("/");
                cookie.setHttpOnly(true);
//                cookie.setSecure(true); // https
                cookie.setMaxAge(-1); // 연결된 동안만 사용

                httpServletResponse.addCookie(cookie);
            }else{
                throw new RuntimeException("비밀번호 불일치");
            }
        }else{
            throw new RuntimeException("사용자 없음");
        }
    }
}
