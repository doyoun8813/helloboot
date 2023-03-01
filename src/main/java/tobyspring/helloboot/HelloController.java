package tobyspring.helloboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// API 요청에 대한 응답을 화면 통채로 보내는 것이 아닌
// 데이터를 보내는 RestController
@Controller
@RestController
public class HelloController {

	private final HelloService helloService;

	public HelloController(HelloService helloService) {
		this.helloService = helloService;
	}

	@GetMapping("/hello")
	public String hello(String name){

		if(name == null || name.trim().length() == 0) throw new IllegalArgumentException();

		return helloService.sayHello(name);

	}

	@GetMapping("/count")
	public String count(String name){
		return "name : " + helloService.countOf(name);
	}

}
