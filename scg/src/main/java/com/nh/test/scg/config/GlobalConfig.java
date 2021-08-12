package com.nh.test.scg.config;

public class GlobalConfig {
    private String baseMessage;
    private boolean preLogger;
    private boolean postLogger;

    public String getBaseMessage() {
        return baseMessage;
    }

    public void setBaseMessage(String baseMessage) {
        this.baseMessage = baseMessage;
    }

    public boolean isPreLogger() {
        return preLogger;
    }

    public void setPreLogger(boolean preLogger) {
        this.preLogger = preLogger;
    }

    public boolean isPostLogger() {
        return postLogger;
    }

    public void setPostLogger(boolean postLogger) {
        this.postLogger = postLogger;
    }

    public GlobalConfig(String baseMessage, boolean preLogger, boolean postLogger) {
        this.baseMessage = baseMessage;
        this.preLogger = preLogger;
        this.postLogger = postLogger;
    }
}