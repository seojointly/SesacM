package ex05_interface;

public interface Shape {
  public double GetPerimeter(); // 둘레 길이 구하기 (public abstract 생략 가능), 근데 public은 보통 많이 적음.
  double GetArea(); // 넓이 구하기
}
/* */
// public abstract class shape {
//   public abstract double getPerimeter(); // 둘레 길이 구하기
//   public abstract double getArea(); //넓이 구하기

// }
