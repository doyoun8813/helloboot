package tobyspring.helloboot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@UnitTest
@interface FastUnitText {

}

// 어플리케이션 LifeCycle에서 annotaion이 유지되는 범위
// 컴파일 이후 런타임시에도 참조가능 주로 리플렉션이나 로깅에 사용
@Retention(RetentionPolicy.RUNTIME)
// Meta Annotaion으로 사용하기 위해 Annotation 타입에 적용 가능하도록 Target 정의
// 메소드 범위에서 사용
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
// custom Test annotation 사용을 위해 meta annotaion @Test 사용
@Test
@interface UnitTest {

}

public class HelloServiceTest {

	@FastUnitText
	void simpleHelloService(){
		SimpleHelloService helloService = new SimpleHelloService();

		String ret = helloService.sayHello("Test");

		Assertions.assertThat(ret).isEqualTo("Hello Test");
	}

	@UnitTest
	void helloDecorator() {
		HelloDecorator helloDecorator = new HelloDecorator(name -> name);

		final String ret = helloDecorator.sayHello("Test");

		Assertions.assertThat(ret).isEqualTo("*Test*");
	}
}
