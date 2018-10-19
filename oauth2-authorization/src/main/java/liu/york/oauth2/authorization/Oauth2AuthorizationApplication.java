package liu.york.oauth2.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Oauth2AuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2AuthorizationApplication.class, args);
	}

	@RequestMapping("/call1")
	public void call1(){
		System.out.println("in coming ...");
	}

	@RequestMapping("/call2")
	public void call2(){
		System.out.println("in coming ...");
	}

}
