import type React from "react"
import { useNavigate, useParams } from "react-router-dom";

const Post: React.FC = () => {
  const { id } = useParams<{id: string}>();
  const navigate = useNavigate();

  const handleClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    // 코드 진행 후 이동이 필요할 때 (ex: 로그인 후 이동)
    navigate("/");
  }
  return(
    <>
      <h3>Post ID: {id}</h3>
      <button onClick={handleClick}>HOME으로 이동</button>
    </>
    
  );
}

export default Post;