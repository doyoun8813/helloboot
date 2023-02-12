package tobyspring.helloboot;

import org.springframework.stereotype.Service;

@Service
//@Primary
public class SimpleHelloService implements HelloService {

	@Override
	public String sayHello(String name){
		return "Hello " + name;
	}

}
