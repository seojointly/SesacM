package ex03_set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 생성자 (constructor)
@AllArgsConstructor
// Getters
@Getter
// Setters
@Setter
// toString()
@ToString
// equals() and hashCode()
@EqualsAndHashCode

public class Member {
  // 필드
  private String name;
  private int age;
}
