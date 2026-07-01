import type React from "react";
import { Outlet } from "react-router-dom";
import Footer from "./Footer";
import Header from "./Header";

const Layout: React.FC = () => {
  return(
    <>
      {/* 상단 헤더 (공통화면) */}
      <Header />

      {/* 메인 화면 (하위 라우터에 의해 변경) */}
      <main>
        {/* Outlet: 여기에 컴포넌트가 왔다갔다 한다는 뜻 */}
        <Outlet />
      </main>

      {/* 하단 푸터 (공통 화면) */}
      <Footer />
    </>
  );
}

export default Layout;