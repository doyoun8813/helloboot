package tobyspring.config.autoconfig;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

import tobyspring.config.MyAutoConfiguration;

@MyAutoConfiguration
@Conditional(TomcatWebServerConfig.TomcatCondition.class)
public class TomcatWebServerConfig {

	@Bean("tomcatWebServerFactory")
	public ServletWebServerFactory servletWebServerFactory(){
		return new TomcatServletWebServerFactory();
	}

	// TomcatWebServerConfig를 Bean으로 등록시킬지 말지 결정하는 중첩 static class
	static class TomcatCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			// 클래스 이름으로 클래스 찾는 스프링이 제공하는 유틸리티 메서드
			return ClassUtils.isPresent("org.apache.catalina.startup.Tomcat", context.getClassLoader());
		}
	}
}
