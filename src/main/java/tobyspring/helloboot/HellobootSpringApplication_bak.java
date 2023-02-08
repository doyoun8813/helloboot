package tobyspring.helloboot;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class HellobootSpringApplication_bak {

	public static void main(String[] args) {

		// spring container 생성
		final GenericApplicationContext applicationContext = new GenericApplicationContext();
		// spring container에 Bean 등록 방법
		applicationContext.registerBean(HelloSpringController.class);
		// HelloSpringController 생성자에 HelloService 타입이 매개변수로 되어 있으면
		// 컨테이너 내부에 HelloService 인터페이스 구현한 Bean을 찾아서 주입
		applicationContext.registerBean(SimpleHelloService.class);
		// spring container 초기화 이 때 등록한 Bean을 만든다.
		applicationContext.refresh();

		// 부트 설치시 내장형 tomcat API들도 같이 설치되었다. 해당 API를 사용한다.
		ServletWebServerFactory servletWebServerFactory = new TomcatServletWebServerFactory();

		WebServer webServer = servletWebServerFactory.getWebServer( servletContext -> {

			// 익명클래스인 ServletContextInitializer를 사용하여 servlet을 servletContainer에 추가
			// servlet 이름과 HTTP 프로토콜을 사용하는 Servlet인 HttpServlet 인스턴스를 상속받는다는 익명클래스 추가
			// 익명 클래스로 만드는 이유는 해당 객체가 변수에 담겨 다른곳에 사용될 일이 없으니..
			// frontController는 리퀘스트 요청을 받는 단일인입점으로 들어온 요청에 따른 작업을 다른 servlet 에게 위임하는 역할을 한다.
			servletContext.addServlet("frontController", new HttpServlet() {

				// 리퀘스트 요청시 마다 호출되는 service 메서드 최초 인스턴스 생성시 항상 메모리에 띄워져 있고
				// 리퀘스트 요청마다 각자의 service 스레드가 작업을 담당하여 리스폰스 한다.
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

					// 인증, 보안, 다국어, 공통 기능 처리하는 부분이 들어감

					// 요청 URI 값에 따른 작업 분기 처리
					if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {

						// query string으로 넘어온 파라미터 name 값을 String으로 binding
						String name = req.getParameter("name");

						// 사용하고 싶은 Bean의 참조값을 받아옴
						final HelloSpringController helloController = applicationContext.getBean(HelloSpringController.class);
						// query string으로 넘어온 파라미터 name 값을 String으로 binding
						final String retrn = helloController.hello(name);

						// enum 클래스, 상수를 사용한 코드값 세팅
						// resp.setStatus(HttpStatus.OK.value()); zero configuration
						// resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE); 아래처럼 변경 가능
						resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
						// Response Body에 결과값을 보낸다
						resp.getWriter().println(retrn);

					}  else {
						// 설정되지 않은 URI가 들어오면 404에러
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}

				} // end of service

				// 리퀘스트 요청을 어느 servlet이 담당하게 될지 정하는 Mapping 추가
			}).addMapping("/*");

		});// end of webServer

		// 웹서버 작동 시작
		webServer.start();

	} // end of main

} // end of class
