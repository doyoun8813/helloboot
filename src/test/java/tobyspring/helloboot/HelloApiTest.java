package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@DisplayName("API 테스트")
public class HelloApiTest {

	@DisplayName("메롱")
	@Test
	void helloApi(){

		// REST 방식으로 개발한 API의 테스트를 최적화하기 위해 만들어진 클래스
		// Http 요청 후 데이터를 응답받을 수 있는 템플릿 객체로 ResponseEntity와 함께 자주 사용한다.
		TestRestTemplate rest = new TestRestTemplate();

		// 스프링에서 제공하는 클래스 중 HttpEntitiy 클래스가 존재하는데
		// Http req/res 이루어질 때 Http 헤더와 바디를 포함하는 클래스
		// ResponseEntity는 HttpEntity를 상속 받는다.
		// HttpRequest에 대한 응답 데이터를 가지고 HttpStatus, Header, Body를 포함한다.
		final ResponseEntity<String> res =
			rest.getForEntity("http://localhost:9090/app/hello?name={name}", String.class, "Spring");

		// 응답 검증 3가지 확인
		// status code 200
		// 응답 HttpStatus code가 enum 이니 단순 int가 아닌 해당 객체와 비교해야 함
		Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		// header(cotent-type) text/plain
		// header 전체를 담은 컬렉션 리턴되어 첫번째 헤더 컨텐트 타입의 text/plain 인지 확인
		Assertions.assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);
		// body hello Spring
		Assertions.assertThat(res.getBody()).isEqualTo("*Hello Spring*");

	}

	@DisplayName("API 실패했을 경우 테스트")
	@Test
	void failsHelloApi(){

		TestRestTemplate rest = new TestRestTemplate();

		// 파라미터 값을 넘기지 않았을 때 IllegalArgumentException 난다
		final ResponseEntity<String> res = rest.getForEntity("http://localhost:8080/hello?name=", String.class);

		// status code 500이 일어나면 성공
		Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
