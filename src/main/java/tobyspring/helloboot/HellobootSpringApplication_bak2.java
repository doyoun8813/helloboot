package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class HellobootSpringApplication_bak2 {

	public static void main(String[] args) {

		// spring container 생성
		final GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
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
			// Spring 의 DispatcherServlet을 통해 URI 맵핑,요청 값 바인딩 해당하는 Bean에게 작업 전달 등 작업 위임
			// DispatcherServlet에게 사용중인 spring container 알려줘야 작업 시킬 Bean들을 찾아낼 수 있다.
			servletContext.addServlet("dispatcherServlet", new DispatcherServlet(applicationContext)
			).addMapping("/*");

		});// end of webServer

		// 웹서버 작동 시작
		webServer.start();

	} // end of main

} // end of class
