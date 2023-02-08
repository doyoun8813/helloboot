package tobyspring.helloboot;

import java.util.Objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSpringController {
	private final HelloService helloService;

	public HelloSpringController(HelloService helloService) {
		this.helloService = helloService;
	}

	@GetMapping("/hello")
	public String hello(String name) {
		return helloService.sayHello(Objects.requireNonNull(name, "널이다!!!!!!"));
	}
}
