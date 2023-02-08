package tobyspring.helloboot;

import java.util.Objects;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// API 요청에 대한 응답을 화면 통채로 보내는 것이 아닌
// 데이터를 보내는 RestController
//@RestController
public class HelloController {

	private final HelloService helloService;
	private final ApplicationContext applicationContext;

	public HelloController(HelloService helloService, ApplicationContext applicationContext) {

		this.helloService = helloService;
		this.applicationContext = applicationContext;

		System.out.println(this.applicationContext);

	}

	@GetMapping("/hello")
	public String hello(String name){

		return helloService.sayHello(Objects.requireNonNull(name,"널이다!!!!!!"));

	}

	//POST방식의 /hello url로 요청이 왔을 때 처리
	@PostMapping("/hello")
	public String hello(){

		return "Hello ";
	}

}
