package tobyspring.helloboot;

import java.util.Objects;

public class HelloController_bak {

	public String hello(String name){

		final SimpleHelloService simpleHelloService = new SimpleHelloService();

		return simpleHelloService.sayHello(Objects.requireNonNull(name,"널이다!!!!!!"));

	}

}
