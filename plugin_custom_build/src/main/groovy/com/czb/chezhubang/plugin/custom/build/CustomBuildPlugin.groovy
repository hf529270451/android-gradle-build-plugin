package com.czb.chezhubang.plugin.custom.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger
import org.gradle.internal.reflect.Instantiator
import com.czb.chezhubang.plugin.custom.build.extensions.CustomBuildExtensions
import org.gradle.invocation.DefaultGradle

/**
 * Created by hfyd on 2018/6/1.
 */
class CustomBuildPlugin implements Plugin<Project> {

    static final def TAG = "customBuild"
    Logger logger

    def static error(msg) {
        println ":${TAG}:-${msg}"
    }

    @Override
    void apply(Project project) {
        logger = project.getLogger()
        Instantiator instantiator = ((DefaultGradle) project.getGradle()).getServices().get(Instantiator.class)
        Object[] cons = new Object[1];
        cons[0] = instantiator
        project.extensions.create("czbBuildConfig", CustomBuildExtensions.class,
                cons)

        project.task('uploadDebugApkToPgy') {
            group 'customBuild'

            doLast {
                println ':customBuild:uploadDebugApkToPgy start'
                def outputFile = project.czbBuildConfig.uploadConfig.debugFilePath
                def apiKey = project.czbBuildConfig.uploadConfig.pgyerApiKey
                def appName = project.czbBuildConfig.uploadConfig.pgyerAppName
                def des = "DebugApk${project.czbBuildConfig.uploadConfig.pgyerDescription}"

                if (!new File(outputFile).exists()) {
                    error "file not found"
                    return
                }

                if (apiKey == null) {
                    error "apiKey must not null"
                    return
                }

                uploadApk(outputFile, apiKey, appName, des)
            }
        }

        project.task('uploadReleaseApkToPgy') {
            group 'customBuild'

            doLast {
                println ':customBuild:uploadReleaseApkToPgy start'
                def outputFile = project.czbBuildConfig.uploadConfig.releaseFilePath
                def apiKey = project.czbBuildConfig.uploadConfig.pgyerApiKey
                def appName = project.czbBuildConfig.uploadConfig.pgyerAppName
                def des = "ReleaseApk${project.czbBuildConfig.uploadConfig.pgyerDescription}"
                if (!new File(outputFile).exists()) {
                    error "file not found"
                    return
                }

                if (apiKey == null) {
                    error "apiKey must not null"
                    return
                }

                uploadApk(outputFile, apiKey, appName, des)
            }
        }

        project.task('uploadJiaGuReleaseApkToPgy') {
            group 'customBuild'

            doLast {
                println ':customBuild:uploadJiaGuReleaseApkToPgy start'
                def outputFile = project.czbBuildConfig.uploadConfig.releaseJiaGuFilePath
                def apiKey = project.czbBuildConfig.uploadConfig.pgyerApiKey
                def appName = project.czbBuildConfig.uploadConfig.pgyerAppName
                def des = "JiaGuReleaseApk${project.czbBuildConfig.uploadConfig.pgyerDescription}"
                if (!new File(outputFile).exists()) {
                    error "file not found"
                    return
                }

                if (apiKey == null) {
                    error "apiKey must not null"
                    return
                }

                uploadApk(outputFile, apiKey, appName, des)
            }
        }

        project.task('jiaGuReleaseApk') {
            group 'customBuild'

            doLast {
                println ':customBuild:jiaGuReleaseApk start'
                def outputFile = project.czbBuildConfig.uploadConfig.releaseFilePath

                if (!new File(outputFile).exists()) {
                    error "apk not found"
                    return
                }

                jiaGuApk(outputFile, project.czbBuildConfig.jiaGuConfig)
            }
        }

        project.task('adbInstallDebugApk') {
            group 'customBuild'

            doLast {
                println ':customBuild:adbInstallDebugApk start'
                def debugFilePath = project.czbBuildConfig.uploadConfig.debugFilePath;
                if (!new File(debugFilePath).exists()) {
                    error "adbInstallDebugApk debugApk not found"
                    return
                }
                Utils.executeCmd("adb install ${debugFilePath}")
            }
        }

        project.task('adbInstallReleaseApk') {
            group 'customBuild'

            doLast {
                println ':customBuild:adbInstallReleaseApk start'
                def releaseFilePath = project.czbBuildConfig.uploadConfig.releaseFilePath;
                if (!new File(releaseFilePath).exists()) {
                    error "adbInstallReleaseApk releaseApk not found"
                    return
                }
                Utils.executeCmd("adb install ${releaseFilePath}")
            }
        }

        project.task('adbInstallJiaGuReleaseApk') {
            group 'customBuild'

            doLast {
                println ':customBuild:adbInstallJiaGuReleaseApk start'
                def jiaguReleaseFilePath = project.czbBuildConfig.uploadConfig.releaseJiaGuFilePath;
                if (!new File(jiaguReleaseFilePath).exists()) {
                    error "adbInstallJiaGuReleaseApk JiaGuReleaseApk not found"
                    return
                }
                Utils.executeCmd("adb install ${jiaguReleaseFilePath}")
            }
        }
    }

    void jiaGuApk(appFilePath, jiaGuConfig) {
        println '360jiagu begin'
        println "appFilePath=" + appFilePath
        println "storeFilePath=${jiaGuConfig.signingConfig.storeFilePath}"

        def cmdBase = "java -jar ${jiaGuConfig.jiaGuFileLocalPath}"

        def cmdLogin = " ${cmdBase}  -login ${jiaGuConfig.jiaGuUsername} ${jiaGuConfig.jiaGuPassword}"

        /* def cmdImportsign = cmdBase + ' -importsign '
         +jiaGuConfig.signingConfig.storeFilePath + ' '
         +jiaGuConfig.signingConfig.storePassword + ' '
         +jiaGuConfig.signingConfig.keyAlias + ' '
         +jiaGuConfig.signingConfig.keyPassword*/

        def cmdImportsign = "${cmdBase} -importsign ${jiaGuConfig.signingConfig.storeFilePath}" +
                " ${jiaGuConfig.signingConfig.storePassword} ${jiaGuConfig.signingConfig.keyAlias}" +
                " ${jiaGuConfig.signingConfig.keyPassword}";

        println "cmdImportsign ${cmdImportsign}"
        def currFile = new File(".")
        def outputFilePath = currFile.getCanonicalPath() + "/apk"
        def cmdJiagu = cmdBase + ' -jiagu ' + appFilePath + ' ' + outputFilePath + ' -autosign  -automulpkg'


        println "executeCmd ${cmdImportsign}"
        println "executeCmd ${cmdJiagu}"
        Utils.executeCmd(cmdLogin)
        Utils.executeCmd(cmdImportsign)
        println cmdJiagu
        Utils.executeCmd(cmdJiagu)

        println "360jiagu end"
    }


    void uploadApk(outputFile, pgyerApiKey, pgyerAppName, pgyerDes) {
        if (outputFile != null) {

            def uploadCommand = "curl " +
                    " -F file=@${outputFile} " +
                    " -F _api_key=${pgyerApiKey} " +
                    " -F buildName=${pgyerAppName} " +
                    " -F buildUpdateDescription=${pgyerDes} " +
                    "https://www.pgyer.com/apiv2/app/upload"

            println uploadCommand
            Process p = uploadCommand.execute()
            p.waitFor()
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()))

            BufferedReader error = new BufferedReader(new InputStreamReader(
                    p.getErrorStream()))
            String line = null
            logger.log(LogLevel.ERROR, "upload result :")
            while ((line = error.readLine()) != null) {
                logger.log(LogLevel.ERROR, line)
            }

            while ((line = reader.readLine()) != null) {
                logger.log(LogLevel.ERROR, line)
                def startStr = "\"buildShortcutUrl\":\""
                int start = line.indexOf(startStr) + startStr.length()
                int end = line.indexOf("\"", start)
                if (start > 0 && end > 0) {
                    String sUrl = line.substring(start, end)
                    logger.log(LogLevel.ERROR, "pgyer url : https://www.pgyer.com/${sUrl}")
                }
            }
            reader.close()
            error.close()
        }
    }

}