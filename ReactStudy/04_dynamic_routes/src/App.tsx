import type React from "react";
import { Link, Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import User from "./pages/User";
import Post from "./pages/Post";

const App: React.FC = () => {
  return(
    // 이후에는 컴포넌트로 조립 (태그 나열 X)
    <> 
      <header>
        <h1>Welcome</h1>
        <nav>
          {/* 경로 자체에 변수 추가 => 경로변수 */}
          <Link to={"/"}>Home</Link>
          <Link to={"/users/1"}>USER</Link> 
          <Link to={"/posts/2/comments/3"}>POST</Link>
        </nav>

      </header>
      <main>
        <Routes>
          {/* 경로 변수는 ":변수" 형태로 사용 */}
          <Route path="/" element={<Home/>}/>
          <Route path="/users/:id" element={<User/>}/>
          <Route path="/posts/:pid/comments/:cid" element={<Post/>}/>
        </Routes>
      </main>
    </>
  );
}

export default App;