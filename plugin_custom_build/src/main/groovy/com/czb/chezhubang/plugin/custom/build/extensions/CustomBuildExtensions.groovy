package com.czb.chezhubang.plugin.custom.build.extensions

import org.gradle.api.Action
import org.gradle.internal.reflect.Instantiator

public class CustomBuildExtensions {

    private UploadConfig uploadConfig
    private JiaGuConfig jiaGuConfig

    public CustomBuildExtensions(Instantiator instantiator) {
        this.uploadConfig = instantiator.newInstance(UploadConfig.class)
        Object[] cons = new Object[1]
        cons[0] = instantiator
        this.jiaGuConfig = instantiator.newInstance(JiaGuConfig.class, cons)
    }

    public void uploadConfig(Action<UploadConfig> action) {
        action.execute(uploadConfig)
    }

    public void jiaGuConfig(Action<JiaGuConfig> action) {
        action.execute(jiaGuConfig)
    }

    public UploadConfig getUploadConfig() {
        return uploadConfig
    }

    public JiaGuConfig getJiaGuConfig() {
        return jiaGuConfig
    }
}