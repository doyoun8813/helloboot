package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HelloController 단위 테스트")
public class HelloControllerTest {

	@Test
	void helloController(){
		
		// Anonymous 클래스 만들어서 바로 서비스 사용
		// 테스트에서 사용하는 수동 DI
		HelloController helloController = new HelloController(name -> name);

		final String ret = helloController.hello("Test");

		// return 받은 값이 "Test" 인지 확인
		Assertions.assertThat(ret).isEqualTo("Test");

	}

	@DisplayName("HelloController에 null이 들어갈 경우")
	@Test 
	void failsHelloController(){

		HelloController helloController = new HelloController(name -> name);

		// 예외 검증 기능 사용 파라미터 값이 null이 들어가면 IllegalArgumentException 나면 성공
		Assertions.assertThatThrownBy(() -> {
			helloController.hello(null);
		}).isInstanceOf(IllegalArgumentException.class);

		// null String이 들어갈 경우 IllegalArgumentException 나면 성공
		Assertions.assertThatThrownBy(() -> {
			helloController.hello("");
		}).isInstanceOf(IllegalArgumentException.class);

	}
}
