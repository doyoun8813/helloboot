package tobyspring.helloboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@HellobootTest
public class HelloRepositoryTest {

	@Autowired JdbcTemplate jdbcTemplate;

	@Autowired HelloRepository helloRepository;

	@Test
	void findHelloFaild() {
		assertThat(helloRepository.findHello("Toby")).isNull();
	}

	@Test
	void increaseCount() {
		assertThat(helloRepository.countOf("Toby")).isEqualTo(0);

		helloRepository.increaseCount("Toby");
		assertThat(helloRepository.countOf("Toby")).isEqualTo(1);

		helloRepository.increaseCount("Toby");
		assertThat(helloRepository.countOf("Toby")).isEqualTo(2);
	}
}
