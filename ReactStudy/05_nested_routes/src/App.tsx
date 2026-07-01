import type React from "react";
import { Route, Routes } from "react-router-dom";
import Layout from "./pages/layouts/Layout";
import UserList from "./pages/users/UserList";
import PostList from "./pages/posts/PostList";
import Home from "./pages/Home";
import Dashboard from "./pages/dashboard/Dashboard";
import Info from "./pages/dashboard/Info";
import Setting from "./pages/dashboard/Setting";

const App: React.FC = () => {
  return(
    <Routes>
      {/* 상위라우트 */}
      <Route path="/" element={<Layout />} >
      {/* // Route를 셀프클로징 하면 중첩 라우팅이 안됨. 그래서 열고닫아야함 */}
        {/* 하위 라우트: <Oytlet /> 에 표시된다. + "/"로 구분하면 안됨 -> 상위 + 하위이기 때문 */}
        <Route index element={<Home />} />
        <Route path="users" element={<UserList />} />
          {/* 실제로는 UserList만 보여줄 수 없으니까 */}
          {/* <Route index 사용함.*/}
        <Route path="posts" element={<PostList />} />
        <Route path="dashboard" element={<Dashboard />}>
          <Route index element={<Info />} />
          <Route path="info" element={<Info />} />
          <Route path="setting" element={<Setting />} />
        </Route>
      </Route>
    </Routes>
  );
}

export default App;