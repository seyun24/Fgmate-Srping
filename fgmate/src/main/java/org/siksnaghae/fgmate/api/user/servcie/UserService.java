package org.siksnaghae.fgmate.api.user.servcie;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.siksnaghae.fgmate.api.auth.model.AuthDto;
import org.siksnaghae.fgmate.api.auth.model.AuthReqDto;
import org.siksnaghae.fgmate.api.user.model.user.User;
import org.siksnaghae.fgmate.api.user.repository.UserRepository;
import org.siksnaghae.fgmate.common.response.BaseException;
import org.siksnaghae.fgmate.util.JwtUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.siksnaghae.fgmate.common.response.BaseResponseStatus.DATABASE_ERROR;
import static org.siksnaghae.fgmate.common.response.BaseResponseStatus.KAKAO_REQUEST_FAIL;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> get(){
        return userRepository.findAll();
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public AuthDto socialLogIn(AuthReqDto kakaoDto) throws BaseException {
        String id =kakaoDto.getId();
        String email =kakaoDto.getEmail();
        try{
          if(userRepository.existsByInfoId(id)){ // 로그인
              Long userId = userRepository.findByInfoId(id).getUserId();
              String jwt = JwtUtil.createJwt(userId);
              return AuthDto.builder()
                      .userId(userId)
                      .jwt(jwt)
                      .loginInfo("1")
                      .build();
          } else { //회원가입
              User user = User.builder()
                      .infoId(id)
                      .email(email)
                      .build();
              Long userId = userRepository.save(user).getUserId();
              String jwt = JwtUtil.createJwt(userId);
              return AuthDto.builder()
                      .userId(userId)
                      .jwt(jwt)
                      .loginInfo("0")
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

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void saveProfile(String name, Long userId, String file) throws BaseException {
        try {
            User user = userRepository.findById(userId).orElse(null);
            user.setName(name);
            user.setProfileImg(file);
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

    public List<User> findAll(String name) throws BaseException {
        try {
            return userRepository.findByNameStartingWith(name);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<User> findTest() throws BaseException {
        try {
            return userRepository.findAll();
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteByUser(Long userId) throws BaseException {
        try {
            userRepository.deleteById(userId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public AuthReqDto callbackKakao(String token) throws BaseException{
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String email = "";
        String id = "";

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

                id = element.getAsJsonObject().get("id").getAsString();
                boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();

                if (hasEmail) {
                    email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
                }

                System.out.println("id: " + id);
                System.out.println("email: " + email);
                return AuthReqDto.builder()
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



}
