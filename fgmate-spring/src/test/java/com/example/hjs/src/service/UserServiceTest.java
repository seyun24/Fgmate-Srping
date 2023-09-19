package com.example.hjs.src.service;

import com.example.hjs.config.BaseException;
import com.example.hjs.config.BaseResponseStatus;
import com.example.hjs.src.dao.user.UserRepository;
import com.example.hjs.src.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static com.example.hjs.config.BaseResponseStatus.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    /*
    1. 회원인지 아닌지 확인한다 회원이 아니면 에러 발생
    2.
     */
    @Test
    void get() throws BaseException {
        try {

        }catch (DataAccessException dataAccessException){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Test
    void getTest() {

    }

    @Test
    void kakaoLogIn() {
    }

    @Test
    void saveProfile() {
    }

    @Test
    void getUserInfoRes() {
    }

    @Test
    void callbackKakao() {
    }


}

