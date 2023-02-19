package tobyspring.config.autoconfig;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import tobyspring.config.MyAutoConfiguration;

@MyAutoConfiguration
public class ServerPropertiesConfig {

	@Bean
	public ServerProperties serverProperties(Environment env) {

		// properties 소스의 property 이름과 ServerProperties 클래스의 게터 세터로 되어있는 field 이름,
		// 일치하는거 찾아서 바인딩해줌
		return Binder.get(env).bind("", ServerProperties.class).get();
	}

}
