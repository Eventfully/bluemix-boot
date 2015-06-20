package org.eventfully.bluemix;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class JavaController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/java")
    public String java() {
        return "Greetings from Spring Boot Java@Bluemix!";
    }

}