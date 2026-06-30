// import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from './assets/vite.svg'
// import heroImg from './assets/hero.png'
import './App.css'
import React from 'react';

//  ========= default export 처리하기 ============ <하나만 내보냄>
// 중괄호 없이 import
// import 시 이름 변경 가능함

import Post from "./components/Post";
import U from "./components/User"; //User을 U로 변경

//  ========= named export 처리하기 ============ <여러개 내보냄>
// 중괄호 안에 import
// 내보낸 이름과 동일한 이름으로 import
// 이름을 바꾸고 싶으면 as 키워드 활용
import { PI } from "./api/calculate";
import { getArea } from './api/calculate';
import { feature as f } from './api/calculate';

const App: React.FC = () => {
  return (
    <>
    <Post/>
    <U/>
    <div>{PI}</div>
    <div>{getArea(10)}</div>
    <div>{f.add(1,2)}</div>
    <div>{f.sub(3,1)}</div>
    </>
  )
}

export default App
