package liu.york.oauth2.distribute.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndpointController {

    @RequestMapping("/order/call")
    public void call(){
        System.out.println("request coming ...");
    }

}