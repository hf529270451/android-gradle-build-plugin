package com.czb.chezhubang.plugin.custom.build.extensions

class SigningConfig {

    public String storeFilePath

    String storePassword

    String keyAlias

    String keyPassword

    String getStoreFilePath() {
        return storeFilePath
    }

    void setStoreFilePath(String storeFilePath) {
        this.storeFilePath = storeFilePath
    }

    String getStorePassword() {
        return storePassword
    }

    void setStorePassword(String storePassword) {
        this.storePassword = storePassword
    }

    String getKeyAlias() {
        return keyAlias
    }

    void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias
    }

    String getKeyPassword() {
        return keyPassword
    }

    void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword
    }
}