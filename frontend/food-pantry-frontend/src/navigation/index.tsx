import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import HomePage from "../pages/homepage";
import Layout from "../components/layout";
import PantryLandingPage from '../pages/landingPage/home';
import Home from '../pages/admin';
import FrontDeskHomePage from '../pages/front-desk';

interface IProps {
}

function RootNavigation(props: IProps) {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout/>}>
          <Route path="/customer/home" element={<HomePage/>}/>
          <Route path='' element={<PantryLandingPage/>}/>
          <Route path='/admin/home' element = {<Home/>}/>
          <Route path='/front-desk/home' element = {<FrontDeskHomePage/>}/>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default RootNavigation;
