package tobyspring.helloboot;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import tobyspring.config.MySpringBootApplication;

@MySpringBootApplication
public class HellobootSpringApplication {

	// 스프링 부트를 시작하고 난 뒤에 실행되는 메서드
	// 환경 변수로 설정된 속성 값 사용
	// 1. JVM에 전달되어 VM 동작방식 및 시스템 속성 정의
	// 2. 외부환경 변수 속성
	// 3. application.properties에 선언된 속성
	@Bean
	ApplicationRunner applicationRunner(Environment env) {
		return args -> {
			System.out.println("ApplicationRunner args : " + args);
			String name = env.getProperty("my.name");
			System.out.println("my.name : "  + name);
		};
	}

	@Bean
	CommandLineRunner commandLineRunner(Environment env){
		return args -> {
			System.out.println("commandLineRunner args : " + args);
			String name = env.getProperty("my.name");
			System.out.println("my.name : "  + name);
		};
	}


	public static void main(String[] args) {

		//MySpringApplication.run(HellobootSpringApplication.class, args);
		SpringApplication.run(HellobootSpringApplication.class, args);

	} // end of main

} // end of class
