package org.eventfully.bluemix

import org.apache.camel.LoggingLevel
import org.apache.camel.builder.RouteBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
class MqttWeldingEventSubscriberRoute extends RouteBuilder {

    @Override
    /**
     * Need to add streamCaching when logging body otherwise the body is consumed!!!
     * @see { h t t p : / / c a m e l . a p a c h e . o r g / s t r e a m - c a c h i n g . h t m l }
     */
    void configure() throws Exception {

        def mqttClientId='a:xlemsm:bluemix-boot'
        def mqttHost='tcp://xlemsm.messaging.internetofthings.ibmcloud.com:1883'
        def mqttTopicName = 'iot-2/type/+/id/+/evt/+/fmt/json'
        def mqttUserName = 'a-xlemsm-alxzj0ia9c'
        def mqttPassword = 'dRArb!WYm-DYZJWayE'

        def mqttURI = "mqtt:bluemix-boot?host=${mqttHost}&subscribeTopicName=${mqttTopicName}&userName=${mqttUserName}&password=${mqttPassword}&clientId=${mqttClientId}"

        log.error "mqttURI: ${mqttURI}"

        from(mqttURI)
                .routeId("DeviceRoute")
                .streamCaching()
                .log(LoggingLevel.INFO, "org.eventfully.bluemix", 'Log inbound: ${body}')
                .convertBodyTo(String)
                .to("stream:out")

    }
}
