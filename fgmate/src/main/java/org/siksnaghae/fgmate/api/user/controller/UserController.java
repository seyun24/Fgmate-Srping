package org.siksnaghae.fgmate.api.user.controller;


import lombok.RequiredArgsConstructor;
import org.siksnaghae.fgmate.api.auth.model.AuthDto;
import org.siksnaghae.fgmate.api.auth.model.AuthReqDto;
import org.siksnaghae.fgmate.api.auth.model.TokenDto;
import org.siksnaghae.fgmate.api.auth.service.AuthService;
import org.siksnaghae.fgmate.api.product.service.ProductService;
import org.siksnaghae.fgmate.api.user.model.user.User;
import org.siksnaghae.fgmate.api.user.servcie.UserService;
import org.siksnaghae.fgmate.common.response.ApiResponse;
import org.siksnaghae.fgmate.common.response.BaseException;
import org.siksnaghae.fgmate.util.JwtUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("app/users")
public class UserController {


    private final UserService userService;
    private final AuthService authService;

    private final ProductService productService;

//    @GetMapping("/test")
//    public String getTest(@RequestParam String token) {
//        return authService.getKakaoProfile(token);
//    }

//    @GetMapping("/test")
//    public String getTest2(){
//        return "TEST";
//    }

    @PostMapping("/login")
    public ApiResponse<AuthDto> logIn(@RequestBody TokenDto tokenDto)  {
        try {
            AuthReqDto autReqDto;
            if (tokenDto.getAuthFg().equals("NAVER")){
                autReqDto =  authService.getNaverProfile(tokenDto.getToken());
            } else{
                autReqDto =  authService.getKakaoProfile(tokenDto.getToken());
            }
            AuthDto userInfo = userService.socialLogIn(autReqDto);

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

    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<String> putByUser(
            @RequestPart(value = "name") String name,
            @RequestPart(value = "file") MultipartFile file)
    {
        try {
            Long userId = JwtUtil.getUserId();
            String fileUrl = "";
            if (!file.isEmpty()) {
                fileUrl = productService.saveImg(file);
            }
            userService.saveProfile(name, userId, fileUrl);
            return new ApiResponse<>(fileUrl);
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

    @DeleteMapping
    public ApiResponse<String> deleteByUser() {
        try {
            Long userId = JwtUtil.getUserId();
            userService.deleteByUser(userId);
            return new ApiResponse<>(userId+" : 유저 삭제");
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

}
