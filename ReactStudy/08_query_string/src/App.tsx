import type React from "react"
import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import SearchForm from "./pages/SearchForm";
import SearchResult from "./pages/SearchResult";

const App: React.FC = () => {
  return(
    <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/search" element={<SearchForm />} />
        <Route path="/searchresult" element={<SearchResult />} />
      </Routes>
    </>
  );
}

export default App;