package tobyspring.helloboot;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
// 같은 인터페이스를 구현한 객체가 2개 일 때 먼저 주입받을 수 있다.
@Primary
public class HelloDecorator implements HelloService{

	private final HelloService helloService;

	// 나를 제외한 HelloService인 SimpleHelloService를 불러온다
	public HelloDecorator(HelloService helloService){
		this.helloService = helloService;
	}
	@Override
	public String sayHello(String name) {
		return "*" + helloService.sayHello(name) + "*";
	}
}
