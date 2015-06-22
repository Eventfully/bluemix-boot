package org.eventfully.bluemix

import org.apache.camel.LoggingLevel
import org.apache.camel.builder.RouteBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
class DeviceEventSubscriberRoute extends RouteBuilder {

    @Override
    /**
     * Need to add streamCaching when logging body otherwise the body is consumed!!!
     * @see { h t t p : / / c a m e l . a p a c h e . o r g / s t r e a m - c a c h i n g . h t m l }
     */
    void configure() throws Exception {

        def mqttClientId = System.getenv('IOT_CLIENTID') ?: 'a:yxjgdu:bluemix-boot'
        def mqttHost = System.getenv('IOT_HOST') ?: 'tcp://yxjgdu.messaging.internetofthings.ibmcloud.com:1883'
        def mqttTopicName = 'iot-2/type/+/id/+/evt/+/fmt/json'
        def mqttUserName = System.getenv('IOT_USERNAME') ?: 'a-yxjgdu-paid3bqm3k'
        def mqttPassword = System.getenv('IOT_PASSWORD') ?: 'l1ykKxQn0x&n6j6M-K'

        log.debug("VCAP_SERVICES: " + System.getenv('VCAP_SERVICES'))

        def mqttURI = "mqtt:bluemix-boot?host=${mqttHost}&subscribeTopicName=${mqttTopicName}&userName=${mqttUserName}&password=RAW(${mqttPassword})&clientId=${mqttClientId}"

        from(mqttURI)
                .routeId("{{event.id}}")
                .streamCaching()
                .log(LoggingLevel.INFO, "org.eventfully.bluemix", 'Log inbound: ${body}')
                .convertBodyTo(String)
                .to("stream:out")

    }
}
