package tobyspring.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(Config1.class);
		ac.refresh();

		MyBean bean = ac.getBean(MyBean.class);

	}

	@Test
	@DisplayName("false 컨디셔널")
	void conditional2(){
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(Config2.class);
		ac.refresh();

		MyBean bean = ac.getBean(MyBean.class);

	}

	@Configuration
	@Conditional(TrueCondition.class)
	static class Config1 {
		@Bean
		MyBean myBean() {
			return new MyBean();
		}

	}

	@Configuration
	@Conditional(FalseCondition.class)
	static class Config2 {
		@Bean
		MyBean myBean() {
			return new MyBean();
		}

	}

	static class MyBean {

	}

	static class TrueCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return true;
		}
	}

	static class FalseCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return false;
		}
	}
}
