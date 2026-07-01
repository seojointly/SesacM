import type React from "react";
import { Link, Route, Routes } from "react-router-dom"; // BrowserRouter 제거
import Home from "./pages/Home";
import User from "./pages/User";
import Post from "./pages/Post";

const App: React.FC = () => {
  return (
    <>
      <header>
        <h1>Welcome</h1>
        <nav>
            <Link to ={"/"}>HOME</Link>
            <Link to ={"/users"}>USER</Link>
            <Link to ={"/posts"}>POST</Link>
          </nav>
      </header>
      <main>
        {/* BrowserRouter를 지우고 Routes만 남깁니다 */}
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/users" element={<User />} />
          <Route path="/posts" element={<Post />} />
        </Routes>
      </main>
      <footer>
        <p>&copy; My Vite App. All rights reserved.</p>
      </footer>
    </>
  );
};

export default App;