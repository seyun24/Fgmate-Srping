package com.example.hjs.src.controller;

import com.example.hjs.config.BaseException;
import com.example.hjs.config.ApiResponse;
import com.example.hjs.src.domain.refrigerator.RefrigeratorDto;
import com.example.hjs.src.service.RefrigeratorService;
import com.example.hjs.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/refris")
@RequiredArgsConstructor
public class RefrigeratorController {

    private final JwtService jwtService;
    private final RefrigeratorService refrigeratorService;

    @PostMapping("/{refriName}")
    public ApiResponse<Long> saveRefrigerator(@PathVariable String refriName) {
        try {
            Long userId = jwtService.getUserId();
            Long refrigerator = refrigeratorService.saveRefrigerator(refriName, userId);
            return new ApiResponse<>(refrigerator);
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("")
    public ApiResponse<List<RefrigeratorDto>> findAllRefrigerators() {
        try {
            Long userId = jwtService.getUserId();
            return new ApiResponse<>(refrigeratorService.findAllRefrigerators(userId));
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping("/{name}/{refriId}")
    public ApiResponse<String> modifyRefrigerator(@PathVariable String name, @PathVariable long refriId) {
        try {
            refrigeratorService.modifyRefrigerator(name,refriId);
            return new ApiResponse<>("냉장고 수정 완료");
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    //삭제 추가예정
    @DeleteMapping("/{refriId}")
    public ApiResponse<String> deleteRefrigerator(@PathVariable Long refriId) {
        try {
            refrigeratorService.deleteRefrigerator(refriId);
            return new ApiResponse<>("냉장고 삭제 완료");
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("/{refriId}/{userId}")
    public ApiResponse<String> inviteGroup(@PathVariable Long refriId, @PathVariable Long userId) {
        try {
            refrigeratorService.inviteGroup(refriId, userId);
            return new ApiResponse<>("가입 완료");
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @DeleteMapping("/{refriId}/{userId}")
    public ApiResponse<String> outGroup(@PathVariable Long refriId, @PathVariable Long userId) {
        try {
            refrigeratorService.deleteGroupUser(refriId,userId);
            return new ApiResponse<>("탈퇴 완료");
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }
}
