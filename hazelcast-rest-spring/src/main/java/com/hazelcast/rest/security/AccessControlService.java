package com.hazelcast.rest.security;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.security.auth.login.LoginException;
import java.net.InetAddress;
import java.security.Permission;

public interface AccessControlService {
    @Nonnull
    public String[] authenticate(@Nonnull AuthenticationContext ctx) throws LoginException;

    public boolean isPermissionGranted(@Nonnull Permission permission, @Nullable InetAddress remoteAddress,
                                       @Nonnull String... assignedRoles);

    public boolean isResourceAccessGranted(@Nonnull String resourcePath, @Nonnull String methodName,
                                           @Nullable InetAddress remoteAddress, @Nonnull String... assignedRoles);
}
