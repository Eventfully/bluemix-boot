package org.eventfully.bluemix

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
class HomeController {

    @RequestMapping("/")
    String index() {
        return "Hello from Spring Boot Groovy@Bluemix!"
    }

}