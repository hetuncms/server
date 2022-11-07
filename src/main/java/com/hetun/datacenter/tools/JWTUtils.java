package com.hetun.datacenter.tools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.hetun.datacenter.bean.LoginBean;

public class JWTUtils {

    /**
     * 获取token
     *
     * @param u user
     * @return token
     */
    public static String getToken(LoginBean u) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            return JWT.create()
                    .withIssuer("auth0")
                    .withClaim("username", u.getUsername())
                    .withClaim("password", u.getPassword())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * 验证token合法性 成功返回token
     */
    public static DecodedJWT verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
