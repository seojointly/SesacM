package ex03_input;

import java.io.BufferedInputStream;
// import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
// import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

// 한글 가져오기 가능하게 변경. -> UTF-8 설정

public class Main2 {
  public static void main(String[] args) {

    File dir = new File("storage"); 
    File file = new File(dir, "test.txt"); 

    // BufferedInputStream 뒤에 InputStreamReader를 붙여 바이트를 문자로 변환
    try (InputStreamReader isr = new InputStreamReader(
            new BufferedInputStream(new FileInputStream(file)), StandardCharsets.UTF_8)) {

      // 문자를 담을 배열 생성 (char[])
      char[] c = new char[4];
      int readChar = 0;

      // 파일 끝(-1)까지 문자 단위로 읽기
      while ((readChar = isr.read(c)) != -1) {
        // 읽은 문자 수만큼만 텍스트로 변환하여 출력
        System.out.print(new String(c, 0, readChar));
      }
      
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}