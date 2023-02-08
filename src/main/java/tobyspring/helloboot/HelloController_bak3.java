package tobyspring.helloboot;

import java.util.Objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// DispatcherServlet이 spring container에 등록된 bean들을 뒤져서 웹 요청 처리할 수 있는
// 맵핑정보를 가지고 있는 클래스를 찾아서 요청정보 추출. 담당 메소드 추출
@RequestMapping("/hello")
public class HelloController_bak3 {
	private final HelloService helloService;

	public HelloController_bak3(HelloService helloService){
		this.helloService = helloService;
	}

	// String으로 리턴 받으면 view 단에 뿌려줄 프론트 파일을 찾아서 리턴하는데 지금은 단순 문자열을 리턴받으니
	// @ResponseBody 나 @RestController 라고 명시해줘야 문자열 값으로만 리턴 받는다
	@GetMapping
	@ResponseBody
	public String hello(String name){

		return helloService.sayHello(Objects.requireNonNull(name,"널이다!!!!!!"));

	}

}
