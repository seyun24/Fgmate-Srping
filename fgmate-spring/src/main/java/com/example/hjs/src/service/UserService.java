package com.example.hjs.src.service;


import com.example.hjs.config.BaseException;
import com.example.hjs.src.dao.user.UserRepository;
import com.example.hjs.src.domain.user.*;
import com.example.hjs.utils.JwtService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.example.hjs.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.hjs.config.BaseResponseStatus.KAKAO_REQUEST_FAIL;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public List<User> get(){
        return userRepository.findAll();
    }

    public List<UserDto> getTest(){
        return userRepository.getTest();
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public KakaoDto kakaoLogIn(KakaoReqDto kakaoDto) throws BaseException {
        Long id =kakaoDto.getId();
        String email =kakaoDto.getEmail();
        try{
          if(userRepository.existsByInfoId(id)){ // 로그인
              Long userId = userRepository.findByInfoId(id).getUserId();
              String jwt = jwtService.createJwt(userId);
              return KakaoDto.builder()
                      .userId(userId)
                      .jwt(jwt)
                      .loginInfo(1)
                      .build();
          } else { //회원가입
              User user = User.builder()
                      .infoId(id)
                      .email(email)
                      .build();
              Long userId = userRepository.save(user).getUserId();
              String jwt = jwtService.createJwt(userId);
              return KakaoDto.builder()
                      .userId(userId)
                      .jwt(jwt)
                      .loginInfo(1)
                      .build();
          }
        }catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void saveProfile(String name, Long userId) throws BaseException {
        try {
            User user = userRepository.findById(userId).orElse(null);
            user.setName(name);
            userRepository.save(user);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Optional<User> findUser(Long userId) throws BaseException {
        try {
            return userRepository.findById(userId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public KakaoReqDto callbackKakao(String token) throws BaseException{
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String email = "";
        long id = 0;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(reqURL, HttpMethod.POST, entity, String.class);
            HttpStatus statusCode = response.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                String responseBody = response.getBody();

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(responseBody);

                id = element.getAsJsonObject().get("id").getAsLong();
                boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();

                if (hasEmail) {
                    email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
                }

                System.out.println("id: " + id);
                System.out.println("email: " + email);
                return KakaoReqDto.builder()
                        .id(id)
                        .email(email)
                        .build();
            } else {
                System.out.println("Unexpected response: " + statusCode);
                return null;
            }
        } catch (RestClientException exception) {
            throw new BaseException(KAKAO_REQUEST_FAIL);
        }
    }


    public boolean checkUser(Long id) throws BaseException{
        try {
            return userRepository.existsById(id);
        } catch (DataAccessException exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void logIn() throws BaseException{
        try {
            /*
            로그인 로직
             */
        } catch (DataAccessException exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
