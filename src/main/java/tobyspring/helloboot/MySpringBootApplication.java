package tobyspring.helloboot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import tobyspring.config.authconfig.DispatcherServletConfig;
import tobyspring.config.authconfig.TomcatWebServerConfig;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration
@ComponentScan
@Import({DispatcherServletConfig.class, TomcatWebServerConfig.class})
public @interface MySpringBootApplication {

}
