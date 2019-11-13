package com.qlearsite.eng.opentracking.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import io.micronaut.validation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ConfigurationProperties("module")
//@Context
//@Validated
public class ModuleConfigProperties {

    @NotEmpty
    private String id;
    @NotNull
    private Security security;
    @NotNull
    private Notification notification;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @ConfigurationProperties("security")
    public static class Security {

        @NotEmpty
        private String publicKeyPath;
        @NotNull
        private Debug debug;

        public String getPublicKeyPath() {
            return publicKeyPath;
        }

        public void setPublicKeyPath(String publicKeyPath) {
            this.publicKeyPath = publicKeyPath;
        }

        public Debug getDebug() {
            return debug;
        }

        public void setDebug(Debug debug) {
            this.debug = debug;
        }

        @ConfigurationProperties("debug")
        public static class Debug {

            private boolean enabled;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }
    }

    @ConfigurationProperties("notification")
    public static class Notification {

        @NotNull
        private RabbitMq rabbitmq;

        public RabbitMq getRabbitmq() {
            return rabbitmq;
        }

        public void setRabbitmq(RabbitMq rabbitmq) {
            this.rabbitmq = rabbitmq;
        }

        @ConfigurationProperties("rabbitmq")
        public static class RabbitMq {

            @NotEmpty
            private String host;
            private int port;
            private String username;
            private String password;
            private Exchange exchange;

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public Exchange getExchange() {
                return exchange;
            }

            public void setExchange(Exchange exchange) {
                this.exchange = exchange;
            }

            @ConfigurationProperties("exchange")
            public static class Exchange {
                @NotEmpty
                private String name;
                @NotEmpty
                private String type;
                private boolean durable;
                private boolean autoDelete;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public boolean isDurable() {
                    return durable;
                }

                public void setDurable(boolean durable) {
                    this.durable = durable;
                }

                public boolean isAutoDelete() {
                    return autoDelete;
                }

                public void setAutoDelete(boolean autoDelete) {
                    this.autoDelete = autoDelete;
                }
            }
        }
    }
}