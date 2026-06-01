package ex05_interface;

public class Main {
  public static void main(String[] args) {
    Shape[] shapes = { 
      new Circle(1), 
      new Rectangle(1, 2)
    }; 

    for (Shape s : shapes) {
      System.out.println("둘레: " + s.GetPerimeter());
      System.out.println("넓이: " + s.GetArea());
    }
  }
} 