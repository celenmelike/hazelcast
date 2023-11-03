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
package com.hazelcast.rest.controller;

import com.hazelcast.rest.constant.Paths;
import com.hazelcast.rest.model.StatusCodeAndMessage;
import com.hazelcast.rest.model.User;
import com.hazelcast.rest.constant.AccessType;
import com.hazelcast.rest.security.SimpleAuthenticationContext;
import com.hazelcast.rest.service.BearerTokenService;
import com.hazelcast.rest.service.AccessControlServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

@RestController
public class BearerTokenController {
    private final AccessControlServiceImpl accessControlServiceImpl;
    private final BearerTokenService bearerTokenService;

    public BearerTokenController(AccessControlServiceImpl accessControlServiceImpl,
                                 BearerTokenService bearerTokenService) {
        this.accessControlServiceImpl = accessControlServiceImpl;
        this.bearerTokenService = bearerTokenService;
    }

    @PostMapping(value = Paths.V1_TOKEN_BASE_PATH)
    @Operation(summary = "Get bearer token",
            tags = {"Bearer Token Controller"},
            description = "Get bearer token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StatusCodeAndMessage.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StatusCodeAndMessage.class)
                    ))
            })
    ResponseEntity<?> getToken(
            @Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
                    schema = @Schema()) @RequestBody User user
    ) {
        SimpleAuthenticationContext authenticationContext = SimpleAuthenticationContext.builder().
        withAccessType(AccessType.REST)
                .withClusterName("dev")
                .withUsername(user.getName())
                .withPassword(user.getPassword())
                .build();
        try {
            String[] authorities = accessControlServiceImpl.authenticate(authenticationContext);
            String token = bearerTokenService.getJWTToken(authorities, user);
            return ResponseEntity.ok().body(token);
        } catch (RuntimeException | LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StatusCodeAndMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
        }
    }
}
