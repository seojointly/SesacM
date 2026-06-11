# CGLIB(Code Generation Library)와 스프링 프록시(Proxy) 완벽 정리

## 1. CGLIB는 프록시를 만들어주는 라이브러리인가요?
**네, 정확합니다.** **CGLIB(Code Generation Library)**는 자바에서 **프록시(Proxy) 객체를 동적으로 생성해 주는 대표적인 오픈소스 라이브러리**입니다.

스프링 프레임워크(Spring Framework)는 개발자가 직접 프록시 패턴을 복잡하게 구현하지 않아도, 내부적으로 이 CGLIB를 활용하여 핵심 기능(AOP, 트랜잭션 등)을 처리할 프록시 객체를 자동으로 생성해 줍니다.

---

## 2. CGLIB의 프록시 생성 방식: '상속'
자바 생태계에서 동적 프록시를 만드는 방법은 크게 두 가지로 나뉩니다.

| 구분 | JDK 동적 프록시 (JDK Dynamic Proxy) | CGLIB (Code Generation Library) |
| :--- | :--- | :--- |
| **기반 기술** | 자바 리플렉션 API (Java Reflection) | 바이트코드 생성 (ASM 라이브러리) |
| **대상 조건** | 반드시 **인터페이스(Interface)**가 필요함 | 인터페이스가 없는 **일반 구체 클래스**도 가능 |
| **작동 원리** | 인터페이스를 구현하는 프록시 객체 생성 | 대상 클래스를 **상속(Inheritance)**받는 하위 객체 생성 |

> 💡 **CGLIB의 핵심 원리**
> CGLIB는 런타임(프로그램 실행 중)에 개발자가 작성한 `UserService` 클래스를 상속받는 **가짜 자식 클래스**를 바이트코드 수준에서 직접 만들어냅니다. 스프링은 실제 원본 객체 대신 이 가짜 자식 클래스(프록시)를 스프링 빈(Bean)으로 등록하여 사용합니다.

---

## 3. 스프링 부트가 CGLIB를 선택한 이유
과거 스프링 프레임워크는 인터페이스가 있으면 JDK 동적 프록시를, 인터페이스가 없으면 CGLIB를 혼용하여 사용했습니다. 하지만 **스프링 부트(Spring Boot) 2.x 버전부터는 인터페이스 유무와 상관없이 CGLIB를 기본(Default) 프록시 생성 엔진으로 사용**합니다.

* **인터페이스 강제성 제거:** 개발자가 단순한 비즈니스 로직을 짤 때 억지로 인터페이스를 만들지 않아도 `@Transactional`이나 AOP 기능이 완벽하게 동작합니다.
* **성능적 우수성:** 과거 CGLIB는 생성자 중복 호출, 예외 발생 등의 한계가 있었으나 스프링 프레임워크가 이를 모두 개선(Objenesis 라이브러리 도입 등)하면서, 현재는 메서드 호출 속도 면에서 JDK 동적 프록시보다 우수한 성능을 보여줍니다.

---

## 4. 내 코드에서 CGLIB 동작 확인하기
스프링이 정말로 CGLIB 프록시를 만들었는지 확인하는 가장 좋은 방법은 주입받은 빈(Bean)의 클래스명을 출력해 보는 것입니다.

```java
@RestController
public class TestController {
    
    @Autowired
    private UserService userService; // @Transactional이 붙은 서비스 클래스 가정

    @GetMapping("/check")
    public void check() {
        // 객체의 실제 클래스명 출력
        System.out.println(userService.getClass().getName());
    }
}
```

### 출력 결과 예시
만약 해당 클래스에 프록시가 적용되어 있다면, 원래의 패키지·클래스명 뒤에 다음과 같은 접미사가 붙습니다.

```Plaintext
com.example.demo.service.UserService$$EnhancerBySpringCGLIB$$7a8b9c1d
```
의미: "이 객체는 원래 UserService가 아니라, CGLIB가 UserService를 상속받아서 동적으로 만들어낸 프록시 객체입니다."라는 뜻입니다.

## 5. CGLIB 프록시 사용 시 주의할 점 (한계)
CGLIB는 '상속'을 기반으로 하기 때문에 자바의 상속 규칙을 그대로 따릅니다.

final 클래스 사용 불가: final 클래스는 상속이 불가능하므로 CGLIB 프록시를 만들 수 없습니다.

final 메서드 오버라이딩 불가: final 메서드는 자식 클래스에서 재정의할 수 없으므로, 프록시를 통한 부가 기능(트랜잭션 등)이 주입되지 않고 원본 메서드가 그대로 실행됩니다.

private 메서드 접근 불가: 프록시는 외부에서 메서드 호출을 가로채야 하므로, 오버라이딩이 불가능한 private 메서드에는 프록시 메커니즘이 적용되지 않습니다.

📌 결론
CGLIB는 스프링이 프록시 패턴을 구현하기 위해 사용하는 핵심 동적 바이트코드 생성 라이브러리가 맞습니다. 스프링 부트는 이를 활용해 인터페이스 없이도 구체 클래스를 상속받는 가짜 객체(프록시)를 만들어 우리 대신 복잡한 부가 기능을 완벽하게 처리해 주고 있습니다.