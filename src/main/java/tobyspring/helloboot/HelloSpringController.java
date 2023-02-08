package tobyspring.helloboot;

import java.util.Objects;

public class HelloSpringController {

	private final HelloService helloService;

	// Constructor Injection
	public HelloSpringController(HelloService helloService) {
		this.helloService = helloService;
	}

	public String hello(String name){
		return helloService.sayHello(Objects.requireNonNull(name,"널이다!!!!!!"));
	}

}
