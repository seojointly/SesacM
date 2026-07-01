import type React from "react"
import { useNavigate } from "react-router-dom";

const Home: React.FC = () => {

  // useNavigate: 프로그래밍적으로 이동할 때 사용함. 함수 반환
  const navigate = useNavigate();
  
  // 이벤트 핸들러 (이벤트 객체 타입 명시 필수)
  const handleClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    // data-post-id 값 꺼내기
    const postId = e.currentTarget?.dataset.postId;
    // postId를 이용한 요청
    navigate(`/posts/${postId}`);
  }

  return(
    <>
      <h3>Home</h3>
      {/* handleClick = 이벤트 핸들러 이름 */}
      <button onClick={handleClick} data-post-id="1">
        <span>POST-1로 이동</span>
      </button>
      <button onClick={handleClick} data-post-id="2">
        <span>POST-2로 이동</span>
      </button>
    </>
  );
}

export default Home;