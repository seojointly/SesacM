package ex05_interface;

// 인터페이스 Shape의 구현체

public class Rectangle implements Shape{

  // 필드
  private double width; //너비
  private double height; // 높이

  // 생성자
  public Rectangle(double width, double height) {
    this.width = width;
    this.height = height;

  }

  // 메서드
  @Override
  public double GetArea() { // 대문자 G
    return width * height;
  }

  @Override
  public double GetPerimeter() { // 대문자 G
    return 2 * (width + height);
  }
}
