package ex01_File;

import java.io.File;
import java.util.Arrays;
// import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
    
    // 자바 홈을 File 객체로 생성하기
    // window 기준으로 복사붙여넣기 -> \\ 생김. / 로 변경 가능
    // "C:/dev/Java/jdk-21.0.10";
    File javaHome = new File("C:\\dev\\Java\\jdk-21.0.10");
    
    // 기본정보 확인
    System.out.println("이름: " + javaHome.getName());
    System.out.println("절대경로: " + javaHome.getAbsolutePath());
    System.out.println("상대경로: "+ javaHome.getPath());
    System.out.println(javaHome.isDirectory() ? "디렉터리" : "파일");
    System.out.println("크기: " + javaHome.length() + "Byte");
    System.out.println("최종수정일: " + javaHome.lastModified());

    // 하위 디렉터리 / 파일 객체 가져오기
    File[] files = javaHome.listFiles();
    // Stream<File> file1 = Arrays.stream(files);
    Arrays.stream(files)
    .filter(file -> file.isFile())  // 하위 디렉터리 -> 폴더 빼고 파일만 출력
    .forEach(file -> {
        System.out.println(file.getName()); // 파일명
        System.out.println(file.length()); // 파일크기 
      });

    // 디렉터리 조작 (생성, 삭제)
    File dir = new File("C:\\Users\\user\\Desktop\\001.IT\\005.MegaZone\\JavaStudy\\io_test");
    if (dir.exists()) {
      System.out.println(dir.getAbsolutePath() + "존재합니다.");
    } else {
      dir.mkdirs(); // 폴더 만들기 (s를 붙여서 하위 폴더까지 만듬)
      System.out.println(dir.getAbsolutePath() + "생성했습니다.");
    } // 최초 실행 시 생성했습니다, 재시도 시 존재합니다.
  
    dir.delete(); // 곧바로 폴더 삭제 = delete (비어있는 폴더만 지울 수 있음)
  }
}
