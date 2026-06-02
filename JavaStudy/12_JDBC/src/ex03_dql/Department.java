package ex03_dql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @Builder
  @ToString

public class Department {

  // 필드
  private int deptId; // 원래는 snake case -> camel case로 변경
  private String deptName;
  private String location;
}
