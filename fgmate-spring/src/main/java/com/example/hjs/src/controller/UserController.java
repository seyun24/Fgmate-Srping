package com.example.hjs.src.controller;



import com.example.hjs.config.BaseException;
import com.example.hjs.config.ApiResponse;


import com.example.hjs.src.domain.user.KakaoDto;
import com.example.hjs.src.domain.user.KakaoReqDto;
import com.example.hjs.src.domain.user.TokenDto;

import com.example.hjs.src.domain.user.User;
import com.example.hjs.src.service.UserService;
import com.example.hjs.utils.JwtService;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("app/users")
public class UserController {


    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/kakao-login")
    public ApiResponse<KakaoDto> logInKakao(@RequestBody TokenDto tokenDto)  {
        try {

            KakaoReqDto kakaoReq = userService.callbackKakao(tokenDto.getToken());
            KakaoDto userInfo = userService.kakaoLogIn(kakaoReq);

            return new ApiResponse<>(userInfo);
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping("/{name}")
    public ApiResponse<String> saveProfile(@PathVariable String name) {
        try {
            Long userId = jwtService.getUserId();
            userService.saveProfile(name, userId);
            return new ApiResponse<>("닉네임 설정 완료");
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @GetMapping
    public ApiResponse<User> findUser() {
        try {
            Long userId = jwtService.getUserId();
            return new ApiResponse<>(userService.findUser(userId).orElse(null));
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

}
