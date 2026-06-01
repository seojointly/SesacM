package ex02_output;

import java.io.File;
import java.io.FileOutputStream;

public class Main {
  public static void main(String[] args) {
    
    // File 객체(우리가 만드려는 대상)
    File dir = new File("storage"); // JavaStudy 최상위 경로 생성
    if( !dir.exists()) {
      dir.mkdirs();
    }

    File file = new File(dir,"test.txt");

    FileOutputStream fos = null; // 선언은 밖에서, 실행은 try안에서
    try {
      // 파일로 데이터를 보내는 스트림: 통로 (출력 스트림)
      fos = new FileOutputStream(file);

      // 실제 데이터 내보내기 (int, byte[])
      int c = 'A';
      byte[] b = "pple".getBytes();
      fos.write(c);
      fos.write(b);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    } finally {
      try {
        if(fos != null) {
          fos.close(); // 자동으로 시킬 것. -> Main 참고
        }
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
  }

}
