import React from "react";

const Post: React.FC = () => { // ts 문법 (컴포넌트= Post, 타입을 : 찍고 바로 작성)
  return (
    <>
      <h1>Post</h1>
    </>
  )
}

// 기본 내보내기 (default export): 이걸 기본으로 내보낸다.
export default Post; // 보통 컴포넌트와 동일하게 작성