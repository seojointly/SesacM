package ex02_output;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Main1 {
  public static void main(String[] args) {
    
    // File 객체(우리가 만드려는 대상)
    File dir = new File("storage"); // JavaStudy 최상위 경로 생성
    if( !dir.exists()) {
      dir.mkdirs();
    }

    File file = new File(dir,"test.txt");

    // 이렇게 하면 속도가 더 빠름.
    try (BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(file, true))) {
      // 실제 데이터 내보내기 (int, byte[])
      int c = 'A';
      byte[] b = "pple".getBytes();
      fos.write(c);
      fos.write(b);

    } catch (Exception e) {
      System.err.println(e.getMessage());
    } 
  }
}
