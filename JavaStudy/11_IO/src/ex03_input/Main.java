package ex03_input;

import java.io.BufferedInputStream;
// import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
// import java.io.FileOutputStream;

public class Main {
  public static void main(String[] args) {

    // File 객체(우리가 읽으려는 대상)
    File dir = new File("storage"); // JavaStudy 최상위 경로 생성
    File file = new File(dir, "test.txt"); // 없으면 예외 발생

    // 파일의 읽는 데이터를 읽는 스트림:통로 (입력 스트림)
    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {

      // 어떤 단위로 읽을 것인지 결정 (int, byte[]) -> 읽는 것은 byte 권장
      byte[] b = new byte[4];

      // 실제로 읽은 바이트 수
      int readByte = 0;

      // 파일이 끝날때까지 읽기 (파일이 끝나면 -1 반환)
      while ((readByte = bis.read(b)) != -1) {
        // 실제로 읽은 바이트 수 만큼만 처리
        System.out.print(new String(b, 0, readByte));
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
