package org.siksnaghae.fgmate.api.user.controller;


import lombok.RequiredArgsConstructor;
import org.siksnaghae.fgmate.api.auth.model.KakaoDto;
import org.siksnaghae.fgmate.api.auth.model.KakaoReqDto;
import org.siksnaghae.fgmate.api.auth.model.TokenDto;
import org.siksnaghae.fgmate.api.auth.service.AuthService;
import org.siksnaghae.fgmate.api.user.model.user.User;
import org.siksnaghae.fgmate.api.user.servcie.UserService;
import org.siksnaghae.fgmate.common.response.ApiResponse;
import org.siksnaghae.fgmate.common.response.BaseException;
import org.siksnaghae.fgmate.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("app/users")
public class UserController {


    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/test")
    public String getTest(@RequestParam String token) {
        return authService.getKakaoProfile(token);
    }

//    @GetMapping("/test")
//    public String getTest2(){
//        return "TEST";
//    }

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
            Long userId = JwtUtil.getUserId();
            userService.saveProfile(name, userId);
            return new ApiResponse<>("닉네임 설정 완료");
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @GetMapping
    public ApiResponse<User> findUser() {
        try {
            Long userId = JwtUtil.getUserId();
            return new ApiResponse<>(userService.findUser(userId).orElse(null));
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/list")
    public ApiResponse<List<User>> findAllUser(@RequestParam String name) {
        try {
            List<User> list = userService.findAll(name);
            return new ApiResponse<>(list);
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/all")
    public ApiResponse<List<User>> findAll() {
        try {
            List<User> list = userService.findTest();
            return new ApiResponse<>(list);
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

}
