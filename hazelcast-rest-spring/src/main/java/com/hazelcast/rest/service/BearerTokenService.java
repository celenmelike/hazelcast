/*
 * Copyright (c) 2008-2023, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hazelcast.rest.service;

import com.hazelcast.rest.model.User;
import com.hazelcast.rest.security.TokenAuthenticationFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class BearerTokenService {

    private final int expirationTimeMillis = 900000;

    public String getJWTToken(String[] authorities, User user) {

        SecretKey secret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        TokenAuthenticationFilter.secret = secret;

        String token= Jwts
                .builder()
                .setId(user.getName())
                .claim("username", user.getName())
                .claim("authorities", String.join(", ", authorities))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(secret).compact();

        return "Bearer " + token;
    }
}
