import type React from "react"
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const SearchForm: React.FC = () => {

  // 입력 상자에 입력한 값을 상태로 관리
  const [ keyword, setKeyword ] = useState<string>('');
  const navigate = useNavigate();

  // 폼 제출(서브밋) 이벤트 핸들러
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if(keyword) {
      navigate(`/searchresult?keyword=${keyword}`);
    }
  }

  // 입력 상자 이벤트 핸들러
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setKeyword(e.target.value);
  }
  return(
    <>
      <h3>SearchForm</h3>
      <form onSubmit={handleSubmit}>
        <input type={"text"} value={keyword} onChange={handleChange} />
        <button type={"submit"}>검색</button>
      </form>

    </>
  );
}

export default SearchForm;