package com.czb.chezhubang.plugin.custom.build.extensions

import org.gradle.api.Action
import org.gradle.internal.reflect.Instantiator

class JiaGuConfig {

    String jiaGuUsername

    String jiaGuPassword

    String jiaGuFileLocalPath

    SigningConfig signingConfig

    public JiaGuConfig(Instantiator instantiator) {
        this.signingConfig = instantiator.newInstance(SigningConfig.class)
    }

    SigningConfig getSigningConfig() {
        return signingConfig
    }

    void signingConfig(Action<SigningConfig> action) {
        action.execute(signingConfig)
    }

    String getJiaGuUsername() {
        return jiaGuUsername
    }

    void setJiaGuUsername(String jiaGuUsername) {
        this.jiaGuUsername = jiaGuUsername
    }

    String getJiaGuPassword() {
        return jiaGuPassword
    }

    void setJiaGuPassword(String jiaGuPassword) {
        this.jiaGuPassword = jiaGuPassword
    }

    String getJiaGuFileLocalPath() {
        return jiaGuFileLocalPath
    }

    void setJiaGuFileLocalPath(String jiaGuFileLocalPath) {
        this.jiaGuFileLocalPath = jiaGuFileLocalPath
    }
}