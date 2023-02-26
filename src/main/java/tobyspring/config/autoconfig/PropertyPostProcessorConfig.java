package tobyspring.config.autoconfig;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;

import tobyspring.config.MyAutoConfiguration;
import tobyspring.config.MyConfigurationProperties;

@MyAutoConfiguration
public class PropertyPostProcessorConfig {

	// 객체 생생 후 스프링 컨테이너에 빈등록전 빈을 원하는대로 조작할 수 있는 기능 제공 BeanPostProcessor
	// ServerProperties 객체가 생성되면 properties 파일과 바인딩 하기 위해
	@Bean
	BeanPostProcessor postProcessor(Environment env) {
		return new BeanPostProcessor() {
			// 객체 생성 후, 초기화 작업 이후에 후처리를 진행한다.
			// 모든 빈 오브젝트가 만들어질 떄 실행 됨
			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

				// 바인딩 작업은 MyConfigurationProperties애노테이션을 가지고 있는 클래스일 때 바인딩함
				MyConfigurationProperties annotaion = AnnotationUtils.findAnnotation(bean.getClass(), MyConfigurationProperties.class);

				if(annotaion == null) return bean;

				// 모든 애노테이션 정보를 맵으로 받아서 "prefix" 설정 정보를 가져온다.
				Map<String, Object> attrs = AnnotationUtils.getAnnotationAttributes(annotaion);
				String prefix = (String)attrs.get("prefix");

				// Binder를 사용하여 예외사항 고려한 코드
				// 바인딩할 프로퍼티가 하나도 없으면 bean 오브젝트 만듦
				return Binder.get(env).bindOrCreate(prefix, bean.getClass());
			}
		};
	}
}
