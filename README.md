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

## Spring Boot Actuator
[Spring Reference: endpoints](http://docs.spring.io/spring-boot/docs/1.1.2.RELEASE/reference/htmlsingle/#production-ready-endpoints)

## Links
[Spring IO guide: convert jar to war](http://spring.io/guides/gs/convert-jar-to-war/)

[Cloudfoundry gradle plugin](https://github.com/cloudfoundry/cf-java-client/tree/master/cloudfoundry-gradle-plugin)