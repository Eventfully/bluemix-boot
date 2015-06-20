# bluemix-boot
Simple Spring Boot app with Bluemix configuration

## Register and login
Register for a free 30 days account.

Region United Kingdom: `https://console.eu-gb.bluemix.net`

Region US South: `https://console.bluemix.net`

## Disable Spring Auto Reconfiguration Framework
For some reason I needed to disable the Spring auto reconfiguration:
`JBP_CONFIG_SPRINGAUTORECONFIGURATION` needs to be set to `[enabled: false]`

Otherwise I get:
`The application or context root for this request has not been found: /`

[IBM WebSphere Liberty Buildpack: ibm-websphere-liberty-buildpack/docs](https://github.com/cloudfoundry/ibm-websphere-liberty-buildpack/blob/master/docs/framework-spring-auto-reconfiguration.md)


## Add Bluemix CF in your ~/.gradle/gradle.properties
Put credentials in gradle home gradle.properties

    cfUsername=someone@example.org
    cfPassword=mySuperSecretPassw0rd
    cfOrganization=someone@example.org
    cfSpace=dev

## Build and deploy
Use the gradle wrapper (gradlew) and you don't even need to install Gradle.

Note that push does not build, so you need to build then push using: `gradlew build cfPush`

    :cfPush
    Updating application bluemix-boot
    Uploading file C:\\dev\\bluemix-boot\\build\\libs\\bluemix-boot.war
    Starting bluemix-boot
    -----> Downloaded app package (17M)
    -----> Downloaded app buildpack cache (1.2M)
    -----> Liberty Buildpack Version: v1.19-20150608-1717
    -----> Retrieving IBM 1.7.1_sr3ifx-20150501 JRE (ibm-java-jre-7.1-3.0-pxa6470_27sr3ifx-20150501_01-cloud.tgz) ... (0.0s)

             Expanding JRE to .java ... (1.0s)
    -----> Retrieving App Management 1.5.0_20150608-1243 (app-mgmt_v1.5-20150608-1243.zip) ... (0.0s)
             Expanding App Management to .app-management (1.0s)
    -----> Liberty buildpack is done creating the droplet
    -----> Uploading droplet (82M)

    Checking status of bluemix-boot
    1 of 1 instances running (1 running)
    Application bluemix-boot is available at http://bluemix-boot.eu-gb.mybluemix.net

    BUILD SUCCESSFUL

    Total time: 3 mins 33.94 secs

## Spring Boot Actuator
[Spring Reference: endpoints](http://docs.spring.io/spring-boot/docs/1.1.2.RELEASE/reference/htmlsingle/#production-ready-endpoints)

## Links
[Spring IO guide: convert jar to war](http://spring.io/guides/gs/convert-jar-to-war/)

[Cloudfoundry gradle plugin](https://github.com/cloudfoundry/cf-java-client/tree/master/cloudfoundry-gradle-plugin)