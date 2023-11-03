package com.hazelcast.rest.security;

import com.hazelcast.rest.constant.AccessType;

import java.net.InetAddress;

public interface AuthenticationContext {
    public AccessType getAccessType();

    public String getClusterName();

    public String getUsername();

    public String getPassword();

    public byte[] getToken();

    public InetAddress getRemoteAddress();
}
