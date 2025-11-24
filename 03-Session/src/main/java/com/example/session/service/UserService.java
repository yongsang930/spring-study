package com.example.session.service;

import com.example.session.db.UserRopository;
import com.example.session.model.LoginRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRopository userRopository;

    public void login(LoginRequest loginRequest, HttpSession httpSession){
        var id = loginRequest.getId();
        var pwd = loginRequest.getPassword();

        var optinalUser = userRopository.findByName(id);

        if(optinalUser.isPresent()){
            var userDto = optinalUser.get();

            if(userDto.getPassword().equals(pwd)){
                // 세션 정보 저장
                httpSession.setAttribute("USER",userDto);
            }else{
                throw new RuntimeException("비밀번호 불일치");
            }
        }else {
            throw new RuntimeException("사용자를 찾을 수 없음");
        }

    }
}
