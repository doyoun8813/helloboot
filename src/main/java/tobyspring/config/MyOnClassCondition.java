package tobyspring.config;

import java.util.Map;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

public class MyOnClassCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Map<String, Object> attrs = metadata.getAnnotationAttributes(ConditionalMyOnClass.class.getName());
		String value = (String)attrs.get("value");
		// 파라미터로 넘어오는 클래스가 프로젝트에 존재하면 true 리턴 아니면 false 리턴
		return ClassUtils.isPresent(value, context.getClassLoader());
	}
}
