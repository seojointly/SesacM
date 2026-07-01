import type React from "react"
import { useNavigate } from "react-router-dom";

const Home: React.FC = () => {

  const navigate = useNavigate();

  return(
    <>
      <h3>Home</h3>
      <button onClick={() => navigate("/search")}>검색하러가기</button>
    </>
  );
}

export default Home;