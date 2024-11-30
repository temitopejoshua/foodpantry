import React from "react";
import { Link } from "react-router-dom";
import images from "../../asssets/images";
import Button from "../button";
import Logout from "./logout";

interface IProps {}

function Header(props: IProps) {
  return (
    <header className="bg-[#FFCE00] text-white">
      <nav className="w-full max-w-[2240px] mx-auto px-5 flex items-center justify-between">
        {/* Logo Section */}
        <div className="flex items-center">
          <Link to="/" className="flex items-center h-full p-5">
            <img
              src={images.bowieStateLogo}
              alt="Logo"
              className="h-10 w-auto bg-[#000000]"
            />
          </Link>
        </div>
        {
          <Logout/>
        }
      </nav>
    </header>
  );
}

export default Header;
