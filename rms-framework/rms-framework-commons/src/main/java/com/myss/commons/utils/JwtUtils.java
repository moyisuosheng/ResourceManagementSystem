package com.myss.commons.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.myss.commons.model.vo.AuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
public class JwtUtils {

    /**
     * JWT 实用程序
     */
    private JwtUtils() {
        throw new IllegalStateException("JwtUtils class");
    }

    /**
     * token加密密钥
     */
    private static final String TOKEN_ENCRYPT_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";


    /**
     * 创建token
     *
     * @param claimMaps 索赔地图
     * @param expire    到期
     * @return {@link String}
     */
    public static String createToken(Map<String, Object> claimMaps, int expire) {
        return Jwts.builder().addClaims(claimMaps).setExpiration(DateTime.now().offset(DateField.MINUTE, expire).toJdkDate()).signWith(SignatureAlgorithm.HS256, generalKey()).compact();
    }

    /**
     * 创建token
     *
     * @param claimMaps 索赔地图
     * @return {@link String}
     */
    public static String createToken(Map<String, Object> claimMaps) {
        return Jwts.builder().addClaims(claimMaps).signWith(SignatureAlgorithm.HS256, generalKey()).compact();
    }

    /**

     * 由字符串生成加密key
     *
     * @return {@link SecretKey}
     */
    public static SecretKey generalKey() {
        String inputString = System.getenv(TOKEN_ENCRYPT_KEY);
        byte[] encodedKey = Base64.getEncoder().encode(inputString.getBytes());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 验证token
     *
     * @param token 令 牌
     * @return boolean
     */
    public static boolean verifyToken(String token) {
        if (StrUtil.isBlank(token)) {
            return false;
        }
        try {
            parserToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析token
     *
     * @param token 令 牌
     * @return {@link Jws}<{@link Claims}>
     */
    private static Jws<Claims> parserToken(String token) {
        if (token.contains("Bearer ")) {
            token = token.replace("Bearer ", "");
        }
        return Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token);
    }


    /**
     * 获取token中的用户信息
     *
     * @param token 令 牌
     * @return {@link AuthInfo}
     */
    public static AuthInfo getInfoFromToken(String token) {
        Jws<Claims> claimsJws = parserToken(token);
        Claims body = claimsJws.getBody();
        AuthInfo authInfo = new AuthInfo();
        authInfo.setUid(body.get("userId", Long.class));
        authInfo.setNickname(body.get("nickname", String.class));
        authInfo.setCompanyId(body.get("companyId", Long.class));
        authInfo.setCompanyName(body.get("companyName", String.class));
        return authInfo;
    }
}