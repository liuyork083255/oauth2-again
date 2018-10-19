package liu.york.oauth2.resource.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndpointController {

    @RequestMapping("/res1/call")
    public void call1(){
        System.out.println("res-1");
    }

    @RequestMapping("/res2/call")
    public void call2(){
        System.out.println("res-2");
    }

}