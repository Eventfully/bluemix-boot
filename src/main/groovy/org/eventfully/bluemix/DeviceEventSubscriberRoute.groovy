package org.eventfully.bluemix

import groovy.json.JsonSlurper
import org.apache.camel.LoggingLevel
import org.apache.camel.builder.RouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class DeviceEventSubscriberRoute extends RouteBuilder {

    private final static String VCAP_SERVICES = '''
{
  "iotf-service" : [ {
    "name" : "bluemix-mqtt",
    "label" : "iotf-service",
    "plan" : "iotf-service-free",
    "credentials" : {
      "iotCredentialsIdentifier" : "a2g6k39sl6r5",
      "mqtt_host" : "abc.messaging.internetofthings.ibmcloud.com",
      "mqtt_u_port" : 1883,
      "mqtt_s_port" : 8883,
      "base_uri" : "https://internetofthings.ibmcloud.com:443/api/v0001",
      "org" : "yxjgdu",
      "apiKey" : "abc",
      "apiToken" : "abc"
    }
  } ]
}
'''

    @Override
    /**
     * Need to add streamCaching when logging body otherwise the body is consumed!!!
     * @see { h t t p : / / c a m e l . a p a c h e . o r g / s t r e a m - c a c h i n g . h t m l }
     */
    void configure() throws Exception {

        log.debug("VCAP_SERVICES: " + System.getenv('VCAP_SERVICES'))
        String vcapServices = System.getenv('VCAP_SERVICES') ?: VCAP_SERVICES

        def jsonSlurper = new JsonSlurper()
        def servicesJson = jsonSlurper.parseText(vcapServices)

        def creds = servicesJson?.'iotf-service'?.credentials.collectEntries()

        String mqtt_host = creds.mqtt_host
        String mqtt_u_port = creds.mqtt_u_port
        String apiKey = creds.apiKey
        String org = creds.org
        String apiToken = creds.apiToken

        def mqttClientId = "a:${org}:bluemix-boot"
        def mqttHost = "${mqtt_host}:${mqtt_u_port}"
        def mqttTopicName = 'iot-2/type/+/id/+/evt/+/fmt/json'
        def mqttUserName = apiKey
        def mqttPassword = apiToken

        def mqttURI = "mqtt:bluemix-boot?host=tcp://${mqttHost}&subscribeTopicName=${mqttTopicName}&userName=${mqttUserName}&password=RAW(${mqttPassword})&clientId=${mqttClientId}"

        from(mqttURI)
                .routeId("{{event.id}}")
                .streamCaching()
                .log(LoggingLevel.INFO, "org.eventfully.bluemix", 'Log inbound: ${body}')
                .convertBodyTo(String)
                .to("stream:out")

    }
}
