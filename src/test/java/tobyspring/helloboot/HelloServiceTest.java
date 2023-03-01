package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HelloService 단위 테스트")
public class HelloServiceTest {

	@Test
	void simpleHelloService(){
		SimpleHelloService helloService = new SimpleHelloService(helloRepositoryStub);

		// 파라미터로 보낸 값이 정상적으로 리턴되는지 확인한다.
		String ret = helloService.sayHello("Test");

		Assertions.assertThat(ret).isEqualTo("Hello Test");
	}

	private HelloRepository helloRepositoryStub = new HelloRepository() {
		@Override
		public Hello findHello(String name) {
			return null;
		}

		@Override
		public void increaseCount(String name) {

		}
	};

	@DisplayName("HelloDecorator가 잘 동작하는지 확인")
	@Test
	void helloDecorator() {
		HelloDecorator helloDecorator = new HelloDecorator(name -> name);

		final String ret = helloDecorator.sayHello("Test");

		Assertions.assertThat(ret).isEqualTo("*Test*");
	}
}
