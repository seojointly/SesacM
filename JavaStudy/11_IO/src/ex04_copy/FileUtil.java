package ex04_copy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

  // 파일 복사
  public static void copy(String source, String copy){

  //  읽을 파일의 객체 생성
  File src = new File(source);

  // 복사할 파일
  File dest = new File(copy);

  // src 읽기 -> byte[] 저장 -> byte[] 내용을 dest로 보내기
  // 서버에 메모리를 쓰는 방법임. 
  try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest)); // 내보내는 것 만들기
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src))) {

        byte[] b = new byte[4];
        int readByte = 0;

        while ((readByte = bis.read(b)) != -1) {
          bos.write(b,0, readByte);
        }

      } catch (IOException e) {
        e.printStackTrace();
    }
    System.out.println(dest.getAbsolutePath() + "파일 복사 완료되었습니다.");
  }
}
