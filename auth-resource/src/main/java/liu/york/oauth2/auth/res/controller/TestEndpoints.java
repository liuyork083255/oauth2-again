package liu.york.oauth2.auth.res.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEndpoints {
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        System.out.println("product id : " + id);
        return "product id : " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        System.out.println("order id : " + id);
        return "order id : " + id;
    }
}