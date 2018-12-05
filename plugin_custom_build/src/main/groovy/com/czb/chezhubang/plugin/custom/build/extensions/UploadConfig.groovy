package com.czb.chezhubang.plugin.custom.build.extensions

class UploadConfig {

    String debugFilePath = ""

    String releaseFilePath = ""

    String releaseJiaGuFilePath = ""

    String pgyerApiKey = ""

    String pgyerAppName = ""

    String getDebugFilePath() {
        return debugFilePath
    }

    void setDebugFilePath(String debugFilePath) {
        this.debugFilePath = debugFilePath
    }

    String getReleaseFilePath() {
        return releaseFilePath
    }

    void setReleaseFilePath(String releaseFilePath) {
        this.releaseFilePath = releaseFilePath
    }

    String getReleaseJiaGuFilePath() {
        return releaseJiaGuFilePath
    }

    void setReleaseJiaGuFilePath(String releaseJiaGuFilePath) {
        this.releaseJiaGuFilePath = releaseJiaGuFilePath
    }

    String getPgyerApiKey() {
        return pgyerApiKey
    }

    void setPgyerApiKey(String pgyerApiKey) {
        this.pgyerApiKey = pgyerApiKey
    }

    String getPgyerAppName() {
        return pgyerAppName
    }

    void setPgyerAppName(String pgyerAppName) {
        this.pgyerAppName = pgyerAppName
    }
}