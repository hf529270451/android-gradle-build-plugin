# gradle.build.plugin

## 1）添加插件依赖：
项目的build.gradle中 添加jcenter远程仓库


    buildscript {
         repositories {  
             maven {
                 url 'https://hfyd.bintray.com/plugin'	   
             }
         }
     }

项目的build.gradle中 添加依赖

    classpath 'com.czb.chezhubang:build-plugin:0.9.1'


app的build.gradle中

    apply plugin: 'com.czb.chezhubang.plugin.build'


## 2）配置：
项目的build.gradle中 android闭包中：


    android {
            ...
	    czbBuildConfig {
    	        uploadConfig {
        	    pgyerApiKey  //蒲公英apikey
        	    pgyerAppName   //蒲公英上传的app名字
        	    debugFilePath  //debug apk包全路径
        	    releaseFilePath  //release包全路径
        	    releaseJiaGuFilePath  //加固后的包全路径
    	        }

    	        jiaGuConfig {
        	    jiaGuUsername //360加固宝 username
        	    jiaGuPassword //360加固宝 password
        	    jiaGuFileLocalPath //360加固宝 jar路径

        	    signingConfig {
            	        storeFilePath //jks路径
            	        storePassword //密码
            	        keyAlias //别名
            	        keyPassword //别名密码
        	    }
                 }
	    }
    }


## 3）如何使用
#### <1> 打包

release 包 ：gradle → czb → Tasks → build → assembleRelease

debug 包 ：gradle → czb → Tasks → build → assembleDebug


#### <2>上传到蒲公英

jiagu包：gradle → czb → Tasks → custom build → uploadReleaseGjiaguApkToPgy

release 包 ：gradle → czb → Tasks → custom build → uploadReleaseApkToPgy

debug 包 ：gradle → czb → Tasks → custom build → uploadDebugApkToPgy

#### <3>自动化加固

gradle → czb → Tasks → custom build → jiaGuReleaseApk


#### <4>安装本地apk

gradle → czb → Tasks → custom build → adbInstallDebugApk

gradle → czb → Tasks → custom build → adbInstallReleaseApk

gradle → czb → Tasks → custom build → adbInstallJiaGuReleaseApk
