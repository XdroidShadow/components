package com.xdroid.spring.util.android.gradle;

public class TEST_Gradle {

    public void test() {
        /**

         plugins {
         id 'com.android.application'
         }
         apply plugin: 'kotlin-android'
         apply plugin: 'kotlin-android-extensions'
         apply plugin: 'kotlin-kapt'

         adbOptions {
         installOptions '-r', '-d', '-g'
         }

         修改apk名称
         android.applicationVariants.all { variant ->
         variant.outputs.all {
         outputFileName = "h3-aicard-${variant.baseName}.apk"
         }
         }

         sourceSets {

         }
         repositories {
         flatDir {
         dirs '/libs' // Module A的libs的目录地址
         }
         }
         lintOptions {
         checkReleaseBuilds false
         abortOnError false
         }
         }

         计算gradle打包时间
         def startT, endT
         afterEvaluate {
         project.tasks.each {
         switch (it.name) {
         case ['preBuild']:
         it.doLast {
         startT = System.currentTimeMillis()
         xDroidLog("buildStart / ${currentTime()}")
         }
         break
         case ['assembleBeta', 'assembleDebug', 'assemblRrelease']:
         it.doLast {
         endT = System.currentTimeMillis()
         xDroidLog("task ${it.name} finished")
         xDroidLog("buildFinished / ${currentTime()} / time consuming to build :${formatTime(endT - startT)}")
         }
         break
         }
         }
         }

         def formatTime(Long time) {
         int secends = time / 1000
         int minutes = secends / 60
         minutes < 1 ? ("${secends}s") : ("${minutes}m ${secends%60}s ")
         }

         获取git上的tag值
         def logGitInfo() {
         try {
         def xDroidOut = new ByteArrayOutputStream()
         exec {
         commandLine('git', 'describe', '--abbrev', '--tags')
         standardOutput = xDroidOut
         }
         xDroidLog("git information-tag: ${xDroidOut.toString().replace('\n', "  ")}")
         } catch (Exception e) {

         }
         }

         gradle.buildFinished {
         android.getBuildOutputs().each {
         //        xDroidLog(it)
         //        println()
         }
         logGitInfo()
         xDroidLog("build target: ${rootProject.ext.android.versionCode} / ${rootProject.ext.android.versionName}")
         xDroidLog("${rootProject.ext.releaseVersionDescription}\r\n")
         }

         def xDroidLog(Object info) {
         file("${getRootDir()}\\XDroidLog").withWriterAppend { writer ->
         writer.write("AICard: $info\r\n")
         }
         }

         def currentTime() {
         return new Date().format("yyyy-MM-dd hh:mm:ss")
         }



         */
    }
}
