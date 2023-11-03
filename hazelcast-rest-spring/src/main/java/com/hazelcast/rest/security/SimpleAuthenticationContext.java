package com.hazelcast.rest.security;

import com.hazelcast.rest.constant.AccessType;

import java.net.InetAddress;

public class SimpleAuthenticationContext implements AuthenticationContext {

    private final AccessType accessType;
    private final String clusterName;
    private final String username;
    private final String password;
    private final byte[] token;
    private final InetAddress remoteAddress;

    private SimpleAuthenticationContext(Builder builder) {
        this.accessType = builder.accessType;
        this.clusterName = builder.clusterName;
        this.username = builder.username;
        this.password = builder.password;
        this.token = builder.token;
        this.remoteAddress = builder.remoteAddress;
    }


    public AccessType getAccessType() {
        return accessType;
    }


    public String getClusterName() {
        return clusterName;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public byte[] getToken() {
        return token;
    }


    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private AccessType accessType;
        private String clusterName;
        private String username;
        private String password;
        private byte[] token;
        private InetAddress remoteAddress;

        private Builder() {
        }

        public Builder withAccessType(AccessType accessType) {
            this.accessType = accessType;
            return this;
        }

        public Builder withClusterName(String clusterName) {
            this.clusterName = clusterName;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withToken(byte[] token) {
            this.token = token;
            return this;
        }

        public Builder withRemoteAddress(InetAddress remoteAddress) {
            this.remoteAddress = remoteAddress;
            return this;
        }

        public SimpleAuthenticationContext build() {
            return new SimpleAuthenticationContext(this);
        }
    }

}
