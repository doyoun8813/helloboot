package tobyspring.helloboot;

import org.springframework.boot.SpringApplication;

import tobyspring.config.MySpringBootApplication;

@MySpringBootApplication
public class HellobootSpringApplication {

	public static void main(String[] args) {

		//MySpringApplication.run(HellobootSpringApplication.class, args);
		SpringApplication.run(HellobootSpringApplication.class, args);

	} // end of main

} // end of class
