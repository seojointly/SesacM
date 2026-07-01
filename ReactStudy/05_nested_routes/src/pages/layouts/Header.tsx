import type React from "react";
import { Link } from "react-router-dom";

const Header: React.FC = () => {
  return (
    <header>
      <h1>Welcome</h1>
      <nav>
        <Link to={"/"}>Home</Link>  |
        <Link to={"/users/"}>USER</Link>  | 
        <Link to={"/posts/"}>POST</Link>  |
        <Link to={"/dashboard/"}>DASHBOARD</Link>
      </nav>
    </header>
  );
};

export default Header;