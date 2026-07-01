import type React from "react";
import { useParams } from "react-router-dom";

const User: React.FC = () => {
  // 경로 변수 처리하는 useParams
  // 경로 변수 모두 모아서 객체 형태({ })로 반환
  // useParams는 문자열 / undefinded 반환
  /* 
  * const obj = useParams(); // 비추
  * obj.id // 경로변수 빼줘야해서. 그래서 객체 구조분해 할당 하는 걸로 변경
  */
  const { id } = useParams<{id: string}>(); // 경로변수는 전부 string (int 로 적어도)
  const userId = id ? Number(id) : NaN; // 실제라면 스프링부트로 반환 (Hooks 사용)
  <h3>User 페이지</h3>
  return(
    <h3>User 페이지 (UserId: {userId})</h3>
  );
}

export default User;