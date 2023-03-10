package tobyspring.study;

import static org.assertj.core.api.Assertions.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConditionalTest {

	@Test
	@DisplayName("true 컨디셔널")
	void conditional(){
		// 스프링 부트에서 제공하는 테스트 전용 ApplicationContext
		// 테스트 AssertJ 라이브러리 제공하는 유틸리티 클래스
		ApplicationContextRunner contextRunner = new ApplicationContextRunner();
		// 적용할 Configuration 넘기고 실행
		contextRunner.withUserConfiguration(Config1.class)
			.run(context -> {
				// Bean 존재 여부 체크
				assertThat(context).hasSingleBean(MyBean.class);
				assertThat(context).hasSingleBean(Config1.class);
			});
	}

	@Test
	@DisplayName("false 컨디셔널")
	void conditional2(){
		new ApplicationContextRunner().withUserConfiguration(Config2.class)
			.run(context -> {
				// Bean 존재하지 않는지 여부 체크
				assertThat(context).doesNotHaveBean(MyBean.class);
				assertThat(context).doesNotHaveBean(Config2.class);
			});
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Conditional(BooleanCondition.class)
	@interface BooleanConditional{
		boolean value();
	}

	// 조건에 따라 Bean을 등록하는 설정 클래스
	@Configuration
	@BooleanConditional(true)
	static class Config1 {
		@Bean
		MyBean myBean() {
			return new MyBean();
		}
	}

	// 조건에 따라 Bean을 등록하는 설정 클래스
	@Configuration
	@BooleanConditional(false)
	static class Config2 {
		@Bean
		MyBean myBean() {
			return new MyBean();
		}
	}

	// 테스트용 생성할 Bean
	static class MyBean {}

	static class BooleanCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			// Annotation에 붙은 속성값을 활용해서 컨디션의 조건을 결정하는 로직 구현
			Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
			assert annotationAttributes != null;
			return (Boolean)annotationAttributes.get("value");
		}
	}
}
