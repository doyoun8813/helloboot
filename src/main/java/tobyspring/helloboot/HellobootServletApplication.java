package tobyspring.helloboot;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class HellobootServletApplication {

	public static void main(String[] args) {

		// 부트 설치시 내장형 tomcat API들도 같이 설치되었다. 해당 API를 사용한다.
		ServletWebServerFactory servletWebServerFactory = new TomcatServletWebServerFactory();

		WebServer webServer = servletWebServerFactory.getWebServer( servletContext -> {

			// 익명클래스인 ServletContextInitializer를 사용하여 servlet을 servletContainer에 추가
			// servlet 이름과 HTTP 프로토콜을 사용하는 Servlet인 HttpServlet 인스턴스를 상속받는다는 익명클래스 추가
			// 익명 클래스로 만드는 이유는 해당 객체가 변수에 담겨 다른곳에 사용될 일이 없으니..
			// frontController는 리퀘스트 요청을 받는 단일인입점으로 들어온 요청에 따른 작업을 다른 servlet 에게 위임하는 역할을 한다.
			servletContext.addServlet("frontController", new HttpServlet() {

				// frontController에서 작업을 위임할 servlet
				final HelloServletController helloController = new HelloServletController();

				// Servlet Lice Cycle
				// 클라이언트 리퀘스트 최초 요청시 1번만 실행되는 초기화 작업용 init 메서드
				@Override
				public void init() throws ServletException {
					super.init();
					System.out.println("init method");
				}

				// 리퀘스트 요청시 마다 호출되는 service 메서드 최초 인스턴스 생성시 항상 메모리에 띄워져 있고
				// 리퀘스트 요청마다 각자의 service 스레드가 작업을 담당하여 리스폰스 한다.
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

					System.out.println("service method");

					// 인증, 보안, 다국어, 공통 기능 처리하는 부분이 들어감

					// 요청 URI 값에 따른 작업 분기 처리
					if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {

						// query string으로 넘어온 파라미터 name 값을 String으로 binding
						String name = req.getParameter("name");

						// helloController에게 파라미터를 넘겨주고 작업을 위임하고 처리한 결과를 받아오는 곳
						final String retrn = helloController.hello(name);

						// enum 클래스, 상수를 사용한 코드값 세팅
						resp.setStatus(HttpStatus.OK.value());
						resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
						// Response Body에 결과값을 보낸다
						resp.getWriter().println(retrn);

					} else if(req.getRequestURI().equals("/user")) {

					} else {
						// 설정되지 않은 URI가 들어오면 404에러
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}

				} // end of service

				// 컨테이너 종료시 호출되는 메서드 종료시 반드시 진행되야 하는 작업을 서술한다.
				@Override
				public void destroy() {
					super.destroy();
					System.out.println("destroy method");
				}

				// 리퀘스트 요청을 어느 servlet이 담당하게 될지 정하는 Mapping 추가
			}).addMapping("/*");

		});// end of webServer

		// 웹서버 작동 시작
		webServer.start();

		try {

			// 웹서버 종료시 destroy 메서드 실행되는지 확인 하기 위해
			// 웹서버 작동 후 10초 뒤에 웹서버 종료 메서드 호출
			Thread.sleep(10000);
			webServer.stop();

		} catch (InterruptedException e) {

			System.out.println(e);

		}

	} // end of main

} // end of class
