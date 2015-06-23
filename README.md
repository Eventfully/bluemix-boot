# bluemix-boot
Simple Spring Boot app with Bluemix configuration
Now with IoT (MQTT) service and Apache Camel.

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

## Using Internet of Things - IoT IBM Service
Add an Internet of Things IBM service and bind it directly when creating it
Add Service -> App: 'bluemix-boot' -> Service Name: 'bluemix-mqtt' -> Selected Plan 'Free' -> Create
The service should then both be created and bound to the bluemix-boot app.
The trick that took me forever to understand was to add the following section to the CF gradle plugin:
    services {
            'iotf-service' {
                label = 'iotf-service'
                plan = 'iotf-service-free'
                name = 'bluemix-mqtt'
                bind = true
            }
        }

This is taken from the credentials section on the service found in the dashboard.
The VCAP_SERVICES environment variable contains a JSON describing all bound services including their credentials.
This is used directly in the creation of the Camel route to avoid having to hard-code or configure the credentials.
The Spring Cloud might contain things that could be used for this instead but this was the simplest way to get it in place as of now.
Think it will be cleaner to put this in ApplicationConfig.groovy later.

## IoT simple groovy device
Read the docs how to add a device in the IoT console. Notice the syntax for allowed values in the topic string.
[Simple IoT groovy client](https://gist.github.com/magnuspalmer/bdc89e8f19ed76cf0799)

## Spring Boot Actuator
Seems like the Liberty buildpack adds its own management stuff, so Spring actuator might not be needed.
Unless more is more?
[Spring Reference: endpoints](http://docs.spring.io/spring-boot/docs/1.1.2.RELEASE/reference/htmlsingle/#production-ready-endpoints)

## Links
[Spring IO guide: convert jar to war](http://spring.io/guides/gs/convert-jar-to-war/)

[Cloudfoundry gradle plugin](https://github.com/cloudfoundry/cf-java-client/tree/master/cloudfoundry-gradle-plugin)
