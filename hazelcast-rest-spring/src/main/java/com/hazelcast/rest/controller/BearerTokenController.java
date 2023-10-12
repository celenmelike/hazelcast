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

import com.hazelcast.rest.model.User;
import com.hazelcast.rest.service.BearerTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BearerTokenController {
    private final BearerTokenService bearerTokenService;

    public BearerTokenController(BearerTokenService bearerTokenService) {
        this.bearerTokenService = bearerTokenService;
    }

    @PostMapping(value = "/token")
    @Operation(summary = "Get bearer token",
            tags = {"Bearer Token Controller"},
            description = "Get bearer token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    ResponseEntity<String> getToken(
            @Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
                    schema = @Schema()) @RequestBody User user
    ) {
        String token = bearerTokenService.getJWTToken(user);
        return ResponseEntity.ok().body(token);
    }
}
