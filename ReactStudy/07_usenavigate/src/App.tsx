import type React from "react"
import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Post from "./pages/Post";

const App: React.FC = () => {
  return(
    <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/posts/:id" element={<Post />} />
      </Routes>
    </>
  );
}

export default App;