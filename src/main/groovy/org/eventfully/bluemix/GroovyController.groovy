package org.eventfully.bluemix

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
class GroovyController {

    @RequestMapping("/groovy")
    String groovy() {
        return "Hello Groovy@Bluemix!"
    }

}