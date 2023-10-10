package org.siksnaghae.fgmate.api.auth.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.siksnaghae.fgmate.common.constant.Constant;
import org.siksnaghae.fgmate.util.ApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    public String getKakaoProfile(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        String response, email;
        Long id;

        try {
            response = ApiUtil.reqAPI(Constant.KAKAO_REQ_URL, "", headers, String.class, HttpMethod.POST);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(response);

            id = element.getAsJsonObject().get("id").getAsLong();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();

            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    public String getNaverProfile(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        String response = "";

        try {
            response = ApiUtil.reqAPI(Constant.NAVER_REQ_URL, "", headers, String.class, HttpMethod.GET);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response;
    }
}
