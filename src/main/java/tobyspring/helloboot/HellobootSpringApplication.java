package tobyspring.helloboot;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import tobyspring.config.MySpringBootApplication;

@MySpringBootApplication
public class HellobootSpringApplication {

	private final JdbcTemplate jdbcTemplate;

	public HellobootSpringApplication(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostConstruct
	void init() {
		jdbcTemplate.execute("create table if not exists hello(name varchar (50) primary key, count int)");
	}

	public static void main(String[] args) {

		//MySpringApplication.run(HellobootSpringApplication.class, args);
		SpringApplication.run(HellobootSpringApplication.class, args);

	} // end of main

} // end of class
