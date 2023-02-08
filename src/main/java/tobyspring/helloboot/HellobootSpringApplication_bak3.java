package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

// 구성 정보를 가진 클래스 스프링 컨테이너가 Bean 구성정보가 있다는걸 인지
@Configuration
@ComponentScan
public class HellobootSpringApplication_bak3 {

	public static void main(String[] args) {

		// Annotaion 기반의 spring container로 변경
		final AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(){

			// GenericWebApplicationContext을 상속한 익명 클래스

			@Override
			protected void onRefresh() {
				super.onRefresh();

				// 부트 설치시 내장형 tomcat API들도 같이 설치되었다. 해당 API를 사용한다.
				ServletWebServerFactory servletWebServerFactory = new TomcatServletWebServerFactory();

				WebServer webServer = servletWebServerFactory.getWebServer( servletContext -> {

					// 익명클래스인 ServletContextInitializer를 사용하여 servlet을 servletContainer에 추가
					// Spring 의 DispatcherServlet을 통해 URI 맵핑,요청 값 바인딩 해당하는 Bean에게 작업 전달 등 작업 위임
					// DispatcherServlet에게 사용중인 spring container 알려줘야 작업 시킬 Bean들을 찾아낼 수 있다.
					// 참조하기 전 변수를 사용 할 수 없으니 자기자신이 is-a GenericWebApplicationContext인 this 키워드 사용
					servletContext.addServlet("dispatcherServlet", new DispatcherServlet(this)
					).addMapping("/*");

				});// end of webServer

				// 웹서버 작동 시작
				webServer.start();
			}
		};
		// spring container에 Bean 등록 방법
		applicationContext.register(HellobootSpringApplication_bak3.class);
		applicationContext.refresh();

	} // end of main

} // end of class
