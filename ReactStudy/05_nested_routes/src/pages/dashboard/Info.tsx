import type React from "react";
import { useOutletContext } from "react-router-dom";
import type { DashboardData } from "./Dashboard";

const Info: React.FC = () => {
  // <Outlet context={data} /> 데이터 받아 처리
  const { data1, data2 } = useOutletContext<DashboardData>();

  return (
    <>
      <h1>Info 페이지</h1>
      <div>{data1}</div>
      <div>{data2}</div>
    </>
  );
}

export default Info;