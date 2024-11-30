import React from 'react';
import { useNavigate } from "react-router-dom";
import LoginForm from "../../components/requestForm/login"
import RegisterForm from "../../components/requestForm/register"

interface IProps {}

function PantryLandingPage(props: IProps) {

  return (
    <div className="h-screen bg-[#f8f9fa] flex flex-col">
      {/* Main Section */}
      <main className="flex flex-col justify-center items-center text-center bg-gradient-to-r from-[#FFFFFF] to-[#FFFFFF] text-black px-6 flex-grow">
        <h2 className="text-3xl md:text-4xl font-bold mb-4">
          Welcome to BSU Pantry
        </h2>
        <h3 className="text-base md:text-lg mb-6 max-w-xl">
          Track your pantry items, reduce food waste, and stay organized. Sign up to get started!
        </h3>
        <div className="flex space-x-16">
            <LoginForm/>

            <RegisterForm/>
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-[#343a40] text-white py-3">
        <div className="container mx-auto text-center text-xs">
          &copy; {new Date().getFullYear()} PantryApp. All rights reserved.
        </div>
      </footer>
    </div>
  );
}

export default PantryLandingPage;
