import type React from "react";
import { Link, Outlet } from "react-router-dom";

// 연습) 자식 컴포넌트와 함께 공유할 데이터 타입
export interface DashboardData {
  data1: string;
  data2: string;
}

const Dashboard: React.FC = () => {

  // 자식 컴포넌트와 함께 공유할 데이터
  const data: DashboardData = {
    data1: "hello",
    data2: "world"
  }

  return (
    <>
      <h1>Dashboard 페이지</h1>
      <nav>
        <Link to={"/dashboard/info"}>INFO</Link> | <Link to={"/dashboard/setting"}>setting</Link>
      </nav>
      {/* Outlet context에 데이터 주입: useOutletContext로 받아서 처리함 */}
      <Outlet context={data} />
    </>

  );
}

export default Dashboard;