package tobyspring.helloboot;

// Loose Coupling 관계를 만들어주기 위한 인터페이스
// 해당 인터페이스를 구현한 클래스라면
// HelloSpringController에서 사용 가능하다.
public interface HelloService {
	String sayHello(String name);
}
